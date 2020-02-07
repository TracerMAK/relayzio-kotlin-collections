package com.relayzio.kotlin.common

class SequentialSearchTable<V>(val size: Int) {

    private val lists = arrayOfNulls<Node<V>>(size)
	
	private class Node<V>(val next: Node<V>, val value: V) {
	    
	}	

    private fun hash(value: V): Int = (value.hashCode() and 0x7fffffff) % size    

    fun put(value: V): Unit = TODO("")
	
	fun contains(value: V): Unit = TODO("")
	    
}