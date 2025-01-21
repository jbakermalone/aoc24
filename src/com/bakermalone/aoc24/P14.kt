package com.bakermalone.aoc24

import com.bakermalone.aoc23.readLines

fun main() {
    val width = 101
    val height = 103
    val regex = Regex("p=([-0-9]*),([-0-9]*) v=([-0-9]*),([-0-9]*)")
    val input = readLines("p14").map { regex.find(it)!!.groups.drop(1).map { it!!.value.toInt() } }
    p14b(input, width, height)
}

private fun p14b(
    input: List<List<Int>>,
    width: Int,
    height: Int
) {
    var list = input
    var i = 0
    var max = 0
    while (true) {
        i++
        val points = mutableSetOf<Pair<Int, Int>>()
        list = list.map { (px, py, vx, vy) ->
            var newX = (px + vx) % width
            if (newX < 0) newX += width
            var newY = (py + vy) % height
            if (newY < 0) newY += height
            points += newX to newY
            listOf(newX, newY, vx, vy)
        }
        val score = points.count {
            (x, y) ->
            points.contains(x + 1 to y) ||
                    points.contains(x + 1 to y + 1) ||
                    points.contains(x + 1 to y - 1) ||
                    points.contains(x to y + 1) ||
                    points.contains(x to y - 1) ||
                    points.contains(x - 1 to y) ||
                    points.contains(x - 1 to y + 1)
                    points.contains(x - 1 to y - 1)
        }
        if (score > max) {
            max = score
            println("$max $i")
            for (y in 0..<height) {
                for (x in 0..<width) {
                    print(if (x to y in points) "X" else " ")
                }
                println()
            }
        }
    }
}

private fun p14a(
    input: List<List<Int>>,
    width: Int,
    height: Int
) {
    val iters = 100
    var tr = 0
    var tl = 0
    var bl = 0
    var br = 0
    for ((px, py, vx, vy) in input) {
        var ex = (px + iters * vx) % width
        var ey = (py + iters * vy) % height
        if (ex < 0) ex += width
        if (ey < 0) ey += height
        println("$ex $ey")
        when {
            ex < width / 2 && ey < height / 2 -> tl++
            ex > width / 2 && ey < height / 2 -> tr++
            ex < width / 2 && ey > height / 2 -> bl++
            ex > width / 2 && ey > height / 2 -> br++
            else -> println("mid: $ex $ey")
        }
    }
    println(tr * tl * bl * br)
}