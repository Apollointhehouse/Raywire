/*
 * Copyright 2025 Apollointhehouse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.apollointhehouse.raywire.internal

import me.apollointhehouse.raywire.Raywire.LOGGER
import me.apollointhehouse.raywire.api.Bus
import me.apollointhehouse.raywire.api.Cancellable
import me.apollointhehouse.raywire.api.Event
import me.apollointhehouse.raywire.api.EventHandler
import java.lang.ref.WeakReference
import java.lang.reflect.Method
import java.util.WeakHashMap
import kotlin.collections.ArrayDeque

internal class EventManager : Bus {
    private val lock = Any()
    private val methodCache: MutableMap<Class<*>, MutableList<Handler>> = mutableMapOf()
    private val objectEventMap: MutableMap<Any, MutableList<Class<out Event>>> = WeakHashMap()
    private val resolvedCache: MutableMap<Class<out Event>, List<Handler>> = mutableMapOf()

    override fun subscribe(obj: Any) {
        synchronized(lock) {
            if (objectEventMap.containsKey(obj)) return

            val klass = obj::class.java

            val methods = allDeclaredMethods(klass)
                .filter { it.isValid() }
                .onEach { it.isAccessible = true }
                .toList()

            for (method in methods) {
                @Suppress("UNCHECKED_CAST")
                val eventClass = method.parameterTypes[0] as Class<out Event>
                val priority = method.getAnnotation(EventHandler::class.java).priority
                val handler = Handler(WeakReference(obj), method, priority)

                val list = methodCache.getOrPut(eventClass) { mutableListOf() }

                // insert sorted by priority (highest first)
                val index = list.indexOfFirst { it.priority < priority }
                if (index == -1) {
                    list.add(handler)
                } else {
                    list.add(index, handler)
                }

                objectEventMap.getOrPut(obj) { mutableListOf() }.add(eventClass)
            }
            resolvedCache.clear()
        }
    }

    @Suppress("kotlin:S6518")
    override fun unsubscribe(obj: Any) {
        synchronized(lock) {
            objectEventMap[obj]?.forEach { eventClass ->
                methodCache[eventClass]?.removeIf {
                    val target = it.target.get()
                    target == null || target === obj
                }
            }
            objectEventMap.remove(obj)
            resolvedCache.clear()
        }
    }

    @Suppress("kotlin:S6518")
    override fun post(event: Event, respectCancels: Boolean) {
        val cancellable = if (respectCancels) event as? Cancellable else null

        if (cancellable?.cancelled == true) return

        val handlers = synchronized(lock) {
            val eventClass = event::class.java
            resolvedCache[eventClass]
                ?: collectHandlersFor(eventClass)
                    .sortedByDescending { it.priority }
                    .also { resolvedCache[eventClass] = it }
        }

        for (handler in handlers) {
            if (cancellable?.cancelled == true) break
            try {
                val target = handler.target.get() ?: continue
                handler.method.invoke(target, event)
            } catch (e: Exception) {
                LOGGER.error("Failed to invoke handler for event: ${event::class.simpleName}", e)
            }
        }
    }

    /**
     * Used to check if Method is valid for Event handling
     * @return true if Method is valid
     */
    private fun Method.isValid(): Boolean {
        if (!isAnnotationPresent(EventHandler::class.java)) return false
        if (returnType != Void.TYPE) return false
        if (parameterCount != 1) return false

        return Event::class.java.isAssignableFrom(parameterTypes[0])
    }

    private fun allDeclaredMethods(klass: Class<*>): Sequence<Method> = sequence {
        val seen = mutableSetOf<Class<*>>()
        val queue = ArrayDeque<Class<*>>()

        queue.add(klass)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()

            if (!seen.add(current)) continue
            if (current == Any::class.java) continue

            current.declaredMethods
                .asSequence()
                .filter { !it.isSynthetic && !it.isBridge }
                .forEach { yield(it) }

            current.superclass?.let { queue.add(it) }

            current.interfaces.forEach { queue.add(it) }
        }
    }

    private fun collectHandlersFor(eventClass: Class<out Event>): List<Handler> {
        val result = mutableListOf<Handler>()
        val seen = mutableSetOf<Class<*>>()
        val queue = ArrayDeque<Class<*>>()

        queue.add(eventClass)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            if (!seen.add(current)) continue

            @Suppress("UNCHECKED_CAST")
            val handlers = methodCache[current as Class<out Event>]
            if (handlers != null) {
                handlers.removeIf { it.target.get() == null }
                result.addAll(handlers)
            }

            current.superclass
                ?.takeIf { Event::class.java.isAssignableFrom(it) }
                ?.let { queue.add(it) }

            current.interfaces
                .filter { Event::class.java.isAssignableFrom(it) }
                .forEach { queue.add(it) }
        }

        return result
    }
}
