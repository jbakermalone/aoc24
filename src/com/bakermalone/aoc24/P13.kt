package com.bakermalone.aoc24

import com.bakermalone.aoc23.readLines
import kotlin.math.roundToInt
import kotlin.math.roundToLong

val EPSILON = 0.001

fun main() {
    val input = readLines("p13")
    val re = Regex(".*[+=]([0-9]+),.*[+=]([0-9]+)")
    var total = 0L
    for (i in input.indices step 4) {
        if (input[i] == "xxx") break
        val (a1, a2) = re.find(input[i])!!.groups.mapNotNull { it!!.value.toDoubleOrNull() }
        val (b1, b2) = re.find(input[i + 1])!!.groups.mapNotNull { it!!.value.toDoubleOrNull() }
        val (c1, c2) = re.find(input[i + 2])!!.groups.mapNotNull { it!!.value.toDoubleOrNull()?.plus(10000000000000.0) }
        val b = ((c1 * a2 / a1) - c2) / ((b1 * a2 / a1) - b2)
        val a = (c1 - b * b1) / a1
        if (Math.abs(a.roundToLong().toDouble() - a) < EPSILON &&
            Math.abs(b.roundToLong().toDouble() - b) < EPSILON) {
            total += 3 * a.roundToLong() + b.roundToLong()
            println(c1 - a1 * a.roundToLong() - b1 * b.roundToLong())
        }
    }
    println(total)
    /*
    val a1 = 26.0
    val b1 = 67.0
    val c1 = 12748.0
    val a2 = 66.0
    val b2 = 21.0
    val c2 = 12176.0
    val b = ((c1 * a2 / a1) - c2) / ((b1 * a2 / a1) - b2)
    val a = (c1 - b * b1) / a1
    println("$a $b")*/
}