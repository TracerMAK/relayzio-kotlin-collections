package com.relayzio.kotlin.collections

import java.io.Serializable
import com.relayzio.kotlin.common.SequentialSearchTable

/**
 * A well-ordered readonly Set that guarantees the existence of a least element and
 * follows the rules of antisymmetry, transitivity, and connexity.
 */
sealed class WellOrderedSet<out E> : OrderedSet<E>, Serializable {

    private val table: SequentialSearchTable<E> by lazy {
	    SequentialSearchTable<E>(997)	// TODO: Adjust for start size of set
	}
	
    override abstract val size: Int
	override abstract fun contains(element: @UnsafeVariance E): Boolean
	override abstract fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean
	override abstract fun isEmpty(): Boolean
	override abstract fun iterator(): Iterator<@UnsafeVariance E>

    internal class Empty : WellOrderedSet<Nothing>() {
	    override val size: Int = 0
		override fun contains(element: Nothing): Boolean = false
		override fun containsAll(elements: Collection<Nothing>): Boolean = false		
		override fun isEmpty() = true
		
		override fun iterator(): Iterator<Nothing> = object : AbstractIterator<Nothing>() {
		    override fun computeNext() = done()
		}
	}
	
	internal class Cons<E>(internal val head: E,
	                       internal val tail: WellOrderedSet<E>) : WellOrderedSet<E>() {
			   
		override val size: Int = tail.size + 1
		
        override fun contains(element: @UnsafeVariance E): Boolean = TODO("")
        override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean = TODO("")
		
        override fun isEmpty() = false

        override fun iterator(): Iterator<@UnsafeVariance E> = TODO("")
				   
	}
	
	companion object {
		operator fun <E> invoke(vararg elems: E): WellOrderedSet<E> {
		    val set = setOf(*elems)
			return set.toList().foldRight(Empty()) {
			    head: E, tail: WellOrderedSet<E> -> Cons(head, tail)
		    }
		}
	}
}