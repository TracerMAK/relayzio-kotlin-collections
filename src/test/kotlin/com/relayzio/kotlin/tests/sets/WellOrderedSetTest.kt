package com.relayzio.kotlin.tests.sets

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.Test

import com.relayzio.kotlin.collections.WellOrderedSet

class WellOrderedSetTest {

    @Test fun createEmptySet(): Unit {
	    val set = WellOrderedSet<Int>()
		assertEquals(0, set.size)
		assertFalse(set.contains(5))
		assertFalse(set.containsAll(listOf(1,2,3)))
		assertTrue(set.isEmpty())
		assertFalse(set.iterator().hasNext())
	}
	
	@Test fun createLargeSet(): Unit {
	    val coll = Array<Int>(100000){index -> index * 2}
	    val set = WellOrderedSet<Int>(*coll)
	    assertEquals(100000, set.size)
		assertFalse(set.isEmpty())
	}
	
	@Test fun createWithDupes(): Unit {
	    val set = WellOrderedSet<Int>(1,2,2,3,4,4,4,5,6)
		assertEquals(6, set.size)
	}
}