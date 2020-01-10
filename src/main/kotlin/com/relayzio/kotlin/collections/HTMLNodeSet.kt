package com.relayzio.kotlin.collections

import java.io.Serializable

sealed class HTMLNodeSet<out E> : AbstractSet<E>(), OrderedSet<E>, Serializable {
    private val serialVersionUID: Long = 3406603774387021979

    companion object {
        operator fun <E> invoke(vararg elements: E): Set<E> =
            if (elements.size > 0) elements.toSet() else emptySet()
    }
}