package com.relayzio.kotlin.collections

/**
 * Creates a read-only ordered set from the provided arguments and weight
 * generation function.
 */
@JvmName("orderedSetByWeightIntOf")
inline fun <reified E> orderedSetByWeightOf(vararg elements: E, crossinline weight: (E) -> Int) : Set<E> =
    when (elements.size) {
        0 -> emptySet()
        else -> elements.sortedByDescending(weight).toSet()
    }

@JvmName("orderedSetByWeightDoubleOf") 
inline fun <reified E> orderedSetByWeightOf(vararg elements: E, crossinline weight: (E) -> Double) : Set<E> =
    when (elements.size) {
        0 -> emptySet()
        else -> elements.sortedByDescending(weight).toSet()
    }


public interface OrderedSet<out E> : Set<E> {
    // Additional operations for an ordered set.
   
}