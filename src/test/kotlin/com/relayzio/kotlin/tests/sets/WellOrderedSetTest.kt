package com.relayzio.kotlin.tests.sets

import java.util.NoSuchElementException
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
        
        var failed = false
        try { emptySet.iterator().next() }
        catch (e: NoSuchElementException) { failed = true }
        assertTrue(failed)
    }
    
    @Test fun createLargeSet(): Unit {
        val coll = Array<Int>(100000){index -> index * 2}
        val set = WellOrderedSet<Int>(*coll)
        assertEquals(100000, set.size)
        assertFalse(set.isEmpty())
        assertTrue(set.contains(50000))
        assertFalse(set.contains(38799))
        assertFalse(set.contains(-5))
        assertTrue(set.containsAll(listOf(18,20,1008,98642,0,199998)))
        assertFalse(set.containsAll(listOf(2,5000,199999)))
    }
    
    @Test fun createWithDupes(): Unit {
        val set = WellOrderedSet<Int>(1,2,2,3,4,4,4,5,6)
        assertEquals(6, set.size)
        assertTrue(set.containsAll(listOf(2,2,3,4,4)))
        assertFalse(set.containsAll(listOf(4,7,2)))
    }
    
    @Test fun setContains(): Unit {
        assertTrue(stringSet.contains("String"))
        assertFalse(stringSet.contains("isn't"))
        assertTrue(stringSet.containsAll(listOf("is", "String")))
        assertFalse(stringSet.containsAll(listOf("This", "Is", "a")))
        
        assertTrue(intSet.contains(13))
        assertFalse(intSet.contains(12))
        assertTrue(intSet.containsAll(listOf(1,3,5,7,11,13,17,19)))
        assertFalse(intSet.containsAll(listOf(1,3,5,7,11,13,17,19,23)))
    }
    
    @Test fun measureHugeSetLookupTime() {
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
    
    @Test fun iteratorTests() {
        val iter = stringSet.iterator()
        
        assertTrue(iter.hasNext())
        assertEquals("This", iter.next())
        assertTrue(iter.hasNext())
        assertEquals("is", iter.next())
        assertTrue(iter.hasNext())
        assertEquals("a", iter.next())
        assertTrue(iter.hasNext())
        assertEquals("String", iter.next())
        assertTrue(iter.hasNext())
        assertEquals("set.", iter.next())
		assertFalse(iter.hasNext())
        
        var failed = false
        try { iter.next() }
        catch (e: NoSuchElementException) { failed = true }
        assertTrue(failed)
    }
}