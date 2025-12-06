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

package me.apollointhehouse.raywire.api

import me.apollointhehouse.raywire.internal.EventManager

interface Bus {
    companion object {
        operator fun invoke(): Bus = EventManager()
    }
    /**
     * Subscribes given object to scan for event handlers to be invoked
     * @param obj the object to be subscribed
     */
    fun subscribe(obj: Any)

    /**
     * Unsubscribes given object from being scanned for event handlers to be invoked
     * @param obj the object to be unsubscribed
     */
    fun unsubscribe(obj: Any)

    /**
     * Invokes all event handlers for given event
     * @param event Event being posted
     */
    @Suppress("unused")
    fun post(event: Event) = post(event, false)
    /**
     * Invokes all event handlers for given event
     * @param event Event being posted
     * @param respectCancels If true, and the event implements [Cancellable], once a handler cancels the event, no further handlers are invoked.
     */
    fun post(event: Event, respectCancels: Boolean)
}
