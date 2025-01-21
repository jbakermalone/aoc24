package com.bakermalone.aoc24

import com.bakermalone.aoc23.readLines

fun main() {
    val input = readLines("p12")
    p12b(input)
}

fun p12a(input: List<String>) {
    val remaining = input.flatMapIndexed { x, line -> line.indices.map { x to it } }.toMutableSet()
    var total = 0
    while (remaining.isNotEmpty()) {
        val seen = mutableSetOf<Pair<Int, Int>>()
        val current = mutableSetOf(remaining.first().also { remaining.remove(it) })
        var perimeter = 0
        while (current.isNotEmpty()) {
          //  println(current)
            val (x, y) = current.first().also { seen.add(it); current.remove(it) }
           // println("$x $y")
            others(x, y, input).filter { it !in seen }.forEach { (x2, y2) ->
                if (x2 == -1 || input[x][y] != input[x2][y2]) {
                   // println("$x $y")
                    perimeter++
                }
                else current.add(x2 to y2)
            }
        }
        //println(input[seen.first().first][seen.first().second])
        //println("${seen.size} $perimeter")
        total += perimeter * seen.size
        remaining.removeAll(seen)
    }
    println(total)
}

fun p12b(input: List<String>) {
    val remaining = input.flatMapIndexed { x, line -> line.indices.map { x to it } }.toMutableSet()
    var total = 0
    while (remaining.isNotEmpty()) {
        val seen = mutableSetOf<Pair<Int, Int>>()
        val current = mutableSetOf(remaining.first().also { remaining.remove(it) })
        val perimeter = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        while (current.isNotEmpty()) {
            //  println(current)
            val (x, y) = current.first().also { seen.add(it); current.remove(it) }
            // println("$x $y")
            others2(x, y).filter { it !in seen }.forEach { (x2, y2) ->
                if (x2 == -1 || y2 == -1 || x2 == input.size || y2 == input[0].length || input[x][y] != input[x2][y2]) {
                    // println("$x $y")
                    perimeter.add((x to y) to (x2 to y2))
                  //  perimeter.add((x2 to y2) to (x to y))
                }
                else current.add(x2 to y2)
            }
        }
        //println(input[seen.first().first][seen.first().second])
        //println("p: ${perimeter.size}")
        var sideCount = 0
        while (perimeter.isNotEmpty()) {
            sideCount++
            val (s1, s2) = perimeter.first().also { perimeter.remove(it) }
            val diff = (s1.second - s2.second) to (s1.first - s2.first)
            var curr = ((s1.first + diff.first) to (s1.second + diff.second)) to
                    ((s2.first + diff.first) to (s2.second + diff.second))
           // println(curr)
           // println(perimeter)
            while (curr in perimeter) {
                perimeter.remove(curr)
                curr = ((curr.first.first + diff.first) to (curr.first.second + diff.second)) to
                        ((curr.second.first + diff.first) to (curr.second.second + diff.second))
            }
            curr = ((s1.first - diff.first) to (s1.second - diff.second)) to
                    ((s2.first - diff.first) to (s2.second - diff.second))
            while (curr in perimeter) {
                perimeter.remove(curr)
                curr = ((curr.first.first - diff.first) to (curr.first.second - diff.second)) to
                        ((curr.second.first - diff.first) to (curr.second.second - diff.second))
            }
        }
        //println("${sideCount}  ${seen.size}")
        total += sideCount * seen.size
        remaining.removeAll(seen)
    }
    println(total)
}

fun others(x: Int, y: Int, input: List<String>): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    if (x > 0) result.add(x - 1 to y) else result.add(-1 to -1)
    if (y > 0) result.add(x to y - 1) else result.add(-1 to -1)
    if (x < input.size - 1) result.add(x + 1 to y) else result.add(-1 to -1)
    if (y < input.size - 1) result.add(x to y + 1) else result.add(-1 to -1)
    return result
}


fun others2(x: Int, y: Int): List<Pair<Int, Int>> {
    val result = mutableListOf<Pair<Int, Int>>()
    result.add(x - 1 to y)
    result.add(x to y - 1)
    result.add(x + 1 to y)
    result.add(x to y + 1)
    return result
}