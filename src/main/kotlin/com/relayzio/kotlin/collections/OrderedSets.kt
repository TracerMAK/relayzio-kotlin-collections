package com.relayzio.kotlin.collections

/**
 * Creates a sorted set from the provided array and weight generation function.
 */
public fun <E> orderedSetByWeightOf(vararg elements: E, weight: (E) -> Int) : Set<E> =
    if (elements.size > 0) elements.sortedBy(weight).toSet() else emptySet()

public interface OrderedSet<out E> : Set<E> {
    // Additional operations for an ordered set.
}