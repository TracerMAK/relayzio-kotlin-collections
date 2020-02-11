package com.relayzio.kotlin.tables

class SequentialSearchTable<V>(val size: Int) {

    private val lists = arrayOfNulls<Node<V>>(size)
	
	private data class Node<V>(val next: Node<V>?, val value: V) {
	    val size: Int by lazy {
		    if (next == null) 1
			else next.size + 1
		}
		
	    override fun toString() = value.toString()
	}

    private fun hash(value: V): Int = (value.hashCode() and 0x7fffffff) % size

    fun put(value: V): Unit {
	    if (!contains(value)) {
	        val h = hash(value)
	        lists[h] = Node<V>(lists[h], value)
		}
	}
	
	fun contains(value: V): Boolean {
		var l = lists[hash(value)]
		if (l?.value == value) return true
		
		while (l?.next != null) {
		    l = l.next
			if (l?.value == value) return true
		}
		return false
	}    
			    
	    
}