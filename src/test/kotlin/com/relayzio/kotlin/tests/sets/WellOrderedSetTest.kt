package com.relayzio.kotlin.tests.sets

import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.Test
import org.junit.BeforeClass

import com.relayzio.kotlin.collections.WellOrderedSet

class WellOrderedSetTest {

    val emptySet = WellOrderedSet<Int>()
    val stringSet = WellOrderedSet<String>("This", "is", "a", "String", "set.")
	val intSet = WellOrderedSet<Int>(1, 3, 5, 7, 11, 13, 17, 19)

    @Test fun createEmptySet(): Unit {
		assertEquals(0, emptySet.size)
		assertFalse(emptySet.contains(5))
		assertFalse(emptySet.containsAll(listOf(1,2,3)))
		assertTrue(emptySet.isEmpty())
		assertFalse(emptySet.iterator().hasNext())
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
	
	@Test fun setContains(): Unit {
	    assertTrue(stringSet.contains("String"))
		assertFalse(stringSet.contains("isn't"))
		assertTrue(intSet.contains(13))
	}
}