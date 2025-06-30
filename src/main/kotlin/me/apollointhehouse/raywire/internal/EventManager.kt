package me.apollointhehouse.raywire.internal

import me.apollointhehouse.raywire.api.*
import me.apollointhehouse.raywire.Raywire.LOGGER
import java.lang.reflect.Method

typealias Callable = Pair<Any, Method>

internal class EventManager : Bus {
	private val methodCache: MutableMap<Class<out Any>, MutableList<Callable>> = mutableMapOf()
	private val objectEventMap: MutableMap<Any, MutableList<Class<out Any>>>   = mutableMapOf()

	/**
	 * Subscribes given object to scan for event handlers to be invoked
	 * @param obj the object to be subscribed
	 */
	override fun subscribe(obj: Any) {
		val klass = obj::class.java

		// Get all valid EventHandler methods
		val methods = klass.methods
			.asSequence()
			.filter { it.isValid() }
			.prioritize()
			.toList()

		for (method in methods) {
			// Cache the methods for each event
			val eventClass = method.parameterTypes[0] ?: continue
			if (!methodCache.containsKey(eventClass)) {
				methodCache[eventClass] = mutableListOf()
			}
			methodCache[eventClass]?.add(obj to method)

			// Cache the events for each object
			if (!objectEventMap.containsKey(obj)) {
				objectEventMap[obj] = mutableListOf()
			}
			objectEventMap[obj]?.add(eventClass)
		}
	}

	/**
	 * Unsubscribes given object from being scanned for event handlers to be invoked
	 * @param obj the object to be unsubscribed
	 */
	override fun unsubscribe(obj: Any) {
		objectEventMap[obj]?.forEach { eventClass ->
			methodCache[eventClass]?.removeIf { it.first == obj }
		}
		objectEventMap.remove(obj)
	}

	/**
	 * Invoke all event handlers for given event
	 * @param event Event that called post
	 */
	override fun post(event: Event) = try {
		val callables = methodCache[event::class.java]?.toList() ?: return

		for ((obj, method) in callables) {
			method.isAccessible = true
			method.invoke(obj, event)
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

		return parameterTypes[0]::class.java.isInstance(Event::class.java)
	}

	/**
	 * Used for setting up Event invocation priority
	 * @return Sorted list by annotation priority
	 */
	private fun Sequence<Method>.prioritize(): Sequence<Method> =
		sortedByDescending { it.getAnnotation(EventHandler::class.java).priority }
}
