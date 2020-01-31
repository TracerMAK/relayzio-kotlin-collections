package com.relayzio.kotlin.collections

import java.io.Serializable

/**
 * A well-ordered readonly Set that guarantees the existence of a least element and
 * follows the rules of antisymmetry, transitivity, and connexity.
 */
sealed class WellOrderedSet<out E> : OrderedSet<E>, Serializable {

    override abstract val size: Int
	override abstract fun contains(element: @UnsafeVariance E): Boolean
	override abstract fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean
	override abstract fun isEmpty(): Boolean
	override abstract fun iterator(): Iterator<@UnsafeVariance E>

    internal object Empty : WellOrderedSet<Nothing>() {
	    override val size: Int = 0
		override fun contains(element: Nothing): Boolean = false
		override fun containsAll(elements: Collection<Nothing>): Boolean = false
		
		override fun isEmpty() = true
		
		override fun iterator(): Iterator<Nothing> = TODO("")
	}
	
	
	/*
	internal class Cons<E>(internal val head: E,
	                       internal val tail: WellOrderedSet<E>) : WellOrderedSet<E> {
						   
	}*/
	

}