package com.relayzio.kotlin.collections

import java.io.Serializable
import com.relayzio.kotlin.tables.SequentialSearchTable

/**
 * A well-ordered readonly Set that guarantees the existence of a least element and
 * follows the rules of antisymmetry, transitivity, and connexity.
 */
sealed class WellOrderedSet<out E> : OrderedSet<E>, Serializable {

    override abstract val size: Int
    override abstract fun contains(element: @UnsafeVariance E): Boolean
    override abstract fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean
    override abstract fun isEmpty(): Boolean
    abstract fun min(): E
    internal abstract fun back(): WellOrderedSet<E>

    override fun iterator(): Iterator<E> =
        when(this) {
            is Empty -> object : AbstractIterator<Nothing>() {
                override fun computeNext() = done()
            }
            is Cons -> object : Iterator<E> {
                    
                private val computeNext = compute()
                private var done = false
                    
                private fun compute(): () -> E {
                    var firstElem: E = min()
                    var nextSet: WellOrderedSet<E> = back()
                    
                    val computeNext = fun(): E {
                        if (done) throw NoSuchElementException()						
                        val elem = firstElem
                        
                        when (nextSet) {
                            is Empty -> done = true
                            is Cons -> {
                                firstElem = nextSet.min()
                                nextSet = nextSet.back()
                            }
                        }
                        return elem
                    }
                    return computeNext
                }	

                override fun next(): E = computeNext()
                
                override fun hasNext(): Boolean = !done
            }
        }
    
    abstract fun listSize(index: Int): Int?  // TODO: Remove after testing

    internal class Empty : WellOrderedSet<Nothing>() {
	
	    override fun min(): Nothing { throw NoSuchElementException() }
        override fun back(): WellOrderedSet<Nothing> = this
        override val size: Int = 0
        override fun contains(element: Nothing): Boolean = false
        override fun containsAll(elements: Collection<Nothing>): Boolean = false		
        override fun isEmpty() = true
        override fun toString() = "{}"
        
        override fun listSize(index: Int): Int? = 0  // TODO: Remove after testing
    }
    
    internal class Cons<E>(internal val head: E,
                           internal val tail: WellOrderedSet<E>,
                           internal val table: SequentialSearchTable<Any?>) : WellOrderedSet<E>() {
                           
        init {
            table.put(head)
        }
        
        override fun min(): E = head
        override fun back(): WellOrderedSet<E> = tail  
        override val size: Int = tail.size + 1    
        override fun contains(element: @UnsafeVariance E): Boolean = table.contains(element)
		override fun isEmpty() = false
        
        override fun containsAll(elements: Collection<@UnsafeVariance E>): Boolean {
            for(e in elements) {
                if (contains(e) == false) return false
            }
            return true
        }
        
		override fun toString(): String = TODO("")
		
        override fun listSize(index: Int): Int? = table.listSize(index)  // TODO: Remove after testing     
    }
    
    companion object {
        // TODO("Add a comparator function as a parameter")
        operator fun <E> invoke(vararg elems: E): WellOrderedSet<E> {
            val set = setOf(*elems)
            val table = SequentialSearchTable<Any?>(997)
            return set.toList().foldRight(Empty()) {
                head: E, tail: WellOrderedSet<E> -> Cons(head, tail, table)
            }
        }
    }
}