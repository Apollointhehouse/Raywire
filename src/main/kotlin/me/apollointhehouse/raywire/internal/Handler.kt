package me.apollointhehouse.raywire.internal

import java.lang.ref.WeakReference
import java.lang.reflect.Method

internal data class Handler(val target: WeakReference<Any>, val method: Method, val priority: Int)
