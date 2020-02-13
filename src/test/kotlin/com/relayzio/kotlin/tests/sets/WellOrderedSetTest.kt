package com.relayzio.kotlin.tests.sets

import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis
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
        assertTrue(set.contains(50000))
        assertFalse(set.contains(38799))
        assertFalse(set.contains(-5))
    }
    
    @Test fun createWithDupes(): Unit {
        val set = WellOrderedSet<Int>(1,2,2,3,4,4,4,5,6)
        assertEquals(6, set.size)
    }
    
    @Test fun setContains(): Unit {
        assertTrue(stringSet.contains("String"))
        assertFalse(stringSet.contains("isn't"))
        
        assertTrue(intSet.contains(13))
        assertFalse(intSet.contains(12))
    }
    
    @Test fun measureLargeSetLookupTime() {
        val huge = Array<Int>(1000000){index -> index}
        lateinit var set: WellOrderedSet<Int>
        
        var lookupTime = measureTimeMillis {
            set = WellOrderedSet<Int>(*huge)
        }
        println("Time to create million element set = $lookupTime ms")
                
        assertEquals(1000000, set.size)
        
        lookupTime = measureNanoTime {
            set.contains(1)
        }
        lookupTime /= 1000
		var listIndex = (1.hashCode() and 0x7fffffff) % 997
		var listSize = set.listSize(listIndex)
		println("Size of search list $listIndex = $listSize")
        println("Lookup time for first element of million element set = $lookupTime us")
        
		listIndex = (500000.hashCode() and 0x7fffffff) % 997
		listSize = set.listSize(listIndex)
		println("Size of search list $listIndex = $listSize")
        lookupTime = measureNanoTime {
            assertTrue(set.contains(500000))
        }
        lookupTime /= 1000
        println("Lookup time for middle element of million element set = $lookupTime us")
        
		listIndex = (999999.hashCode() and 0x7fffffff) % 997
		listSize = set.listSize(listIndex)	
		println("Size of search list $listIndex = $listSize")	
        lookupTime = measureNanoTime {
            assertTrue(set.contains(999999))
        }
        lookupTime /= 1000
        println("Lookup time for last element of million element set = $lookupTime us")
    }
}