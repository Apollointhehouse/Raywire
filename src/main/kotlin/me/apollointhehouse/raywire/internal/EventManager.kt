package me.apollointhehouse.raywire.internal

import me.apollointhehouse.raywire.api.*
import me.apollointhehouse.raywire.Raywire.LOGGER
import java.lang.ref.WeakReference
import java.lang.reflect.Method
import java.util.WeakHashMap

internal class EventManager : Bus {
    private val lock = Any()
    private val methodCache: MutableMap<Class<*>, MutableList<Handler>> = mutableMapOf()
    private val objectEventMap: MutableMap<Any, MutableList<Class<out Any>>> = WeakHashMap()

    /**
     * Subscribes given object to scan for event handlers to be invoked
     * @param obj the object to be subscribed
     */
    override fun subscribe(obj: Any) {
        synchronized(lock) {
            if (objectEventMap.containsKey(obj)) return

            val klass = obj::class.java

            val methods = allDeclaredMethods(klass)
                .filter { it.isValid() }
                .onEach { it.isAccessible = true }
                .toList()

            for (method in methods) {
                val eventClass = method.parameterTypes[0]
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
        }
    }


    /**
     * Unsubscribes given object from being scanned for event handlers to be invoked
     * @param obj the object to be unsubscribed
     */
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
        }
    }

    /**
     * Invoke all event handlers for given event
     * @param event Event that called post
     */
    @Suppress("kotlin:S6518")
    override fun post(event: Event) = try {
        val handlers = synchronized(lock) {
            methodCache
                .filterKeys { it.isAssignableFrom(event::class.java) }
                .values
                .onEach { list ->
                    list.removeIf { it.target.get() == null }
                }
                .flatten()
                .sortedByDescending { it.priority }
        }

        for (handler in handlers) {
            val target = handler.target.get() ?: continue
            handler.method.invoke(target, event)
        }
    } catch (e: Exception) {
        LOGGER.error("Failed to invoke event: ${event::class.simpleName}")
        e.printStackTrace()
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
}
