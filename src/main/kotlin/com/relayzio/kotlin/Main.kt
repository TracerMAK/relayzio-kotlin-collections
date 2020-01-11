package com.relayzio.kotlin

import kotlin.math.*
import com.relayzio.kotlin.collections.orderedSetByWeightOf

fun main(args: Array<String>) {
    // Odd numbers have higher weight.
    val oddToEven: (Int) -> Int = {x -> x % 2}
    val arr = arrayOf(1,5,2,3,10,7,15)
    val s = orderedSetByWeightOf(*arr, weight = oddToEven)
    s.forEach() {print("${it} ")}
    println()
    
    val meanSquaredError: (Double) -> Double = {x ->  0 - (x - 15.25).pow(2)}
    val arrEmpty = arrayOf<Double>(14.2, 14.8, 16.1, 15.32, 15.25)
    val s2 = orderedSetByWeightOf(*arrEmpty, weight = meanSquaredError)
    s2.forEach() {print("${it} ")}
}
