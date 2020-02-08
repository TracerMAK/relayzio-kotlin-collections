package com.relayzio.kotlin.tables

class SequentialSearchTable<V>(val size: Int) {

    private val lists = arrayOfNulls<Node<V>>(size)
	
	private data class Node<V>(val next: Node<V>?, val value: V) {
	    
	}

    private fun hash(value: V): Int = (value.hashCode() and 0x7fffffff) % size

    fun put(value: V): Unit {
	    if (!contains(value)) {
	        val h = hash(value)
	        lists[h] = Node<V>(lists[h], value)
		}
	}
	
	fun contains(value: V): Boolean {
	    val h = hash(value)
		var l = lists[h]
		if (l?.value == value) return true
		
		while (l?.next != null) {
		    l = l.next
			if (l?.value == value) return true
		}
		return false
	}    
			    
	    
}