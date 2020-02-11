package com.relayzio.kotlin.tests.tables

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.Test

import com.relayzio.kotlin.tables.SequentialSearchTable

class Widget(val id: Int, val value: Int) {
	override fun equals(other: Any?): Boolean {
	    if (other == null || other !is Widget || value != other.value)
	        return false
		return true
	}
	override fun hashCode() = 5
	override fun toString(): String = id.toString()
}

class SequentialSearchTableTest {
    
    @Test
	fun intTableWithCollision() {
	    val table = SequentialSearchTable<Int>(10)
		table.put(11)	// hash index = 1
		table.put(101)  // hash index = 1
		assertTrue(table.contains(11))
		assertTrue(table.contains(101))
		assertFalse(table.contains(22))
	}
	
	@Test
	fun objTableWithCollisionAndEqual() {
	    // Each object has same hash code and value. The first object put
		// into the table has 'id' = 1 and the second has 'id' = 2. The
		// object with 'id' = 2 should be rejected.
	    val table = SequentialSearchTable<Widget>(10)
		val w1 = Widget(1, 6)
		val w2 = Widget(2, 6)
		
		table.put(w1)
		table.put(w2)
		assertTrue(table.contains(w1))
	}
	
	@Test
	fun emptyTable() {
	    val table = SequentialSearchTable<Int>(10)
		assertFalse(table.contains(88))
	}
}