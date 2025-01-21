package com.bakermalone.aoc24

import com.bakermalone.aoc23.readLines
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

fun main() {
    val temp = readLines("p8")
    val input = mutableMapOf<Char, MutableList<Pair<Int, Int>>>()
    temp.forEachIndexed { idx, l ->
        l.forEachIndexed { idx2, c ->
            if (c != '.') {
                input.getOrPut(c) { mutableListOf() }.add(idx to idx2)
            }
        }
    }
    p8b(input, temp.size)
}

fun p8b(input: Map<Char, List<Pair<Int, Int>>>, max: Int) {
    val result = mutableSetOf<Pair<Int, Int>>()
    input.values.forEach { locs ->
        for (i in 0 ..< locs.size - 1) {
            for (j in (i + 1) ..< locs.size) {
                val xIncrement = abs(locs[i].first - locs[j].first)
                val xMin = min(locs[i].first, locs[j].first)
                val yIncrement = abs(locs[i].second - locs[j].second)
                val yMin = min(locs[i].second, locs[j].second)
                val xMax = max(locs[i].first, locs[j].first)
                val yMax = max(locs[i].second, locs[j].second)
                if (locs[i].first - locs[j].first >= 0 == locs[i].second - locs[j].second >= 0) {
                    var x = xMin
                    var y = yMin
                    while (x >= 0 && y >= 0) {
                        result.add(x to y)
                        x -= xIncrement
                        y -= yIncrement
                    }
                    x = xMax
                    y = yMax
                    while (x < max && y < max) {
                        result.add(x to y)
                        x += xIncrement
                        y += yIncrement
                    }
                }
                else {
                    var x = xMin
                    var y = yMax
                    while (x >= 0 && y < max) {
                        result.add(x to y)
                        x -= xIncrement
                        y += yIncrement
                    }
                    x = xMax
                    y = yMin
                    while (x < max && y >= 0) {
                        result.add(x to y)
                        x += xIncrement
                        y -= yIncrement
                    }
                }
            }
        }
    }
    println(result.size)
}
