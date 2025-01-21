package com.bakermalone.aoc24

import com.bakermalone.aoc23.readLines

fun main() {
    val (mapLines, movesLines) = readLines("p15").partition { it.contains("#") }
    val moves = movesLines.joinToString("")
    val map = mapLines.toMutableList()
    var locY = map.indexOfFirst { it.contains("@") }
    var locX = map[locY].indexOf('@')
    map[locY] = map[locY].replace('@', '.')

    p15b(moves, map, locY, locX)
}

private fun p15b(
    moves: String,
    initialMap: MutableList<String>,
    initialY: Int,
    initialX: Int
) {
    var map = initialMap.map {
        it.map { c ->
            when(c) {
                '.' -> ".."
                '#' -> "##"
                'O' -> "[]"
                else -> throw Exception()
            }
        }.joinToString("")
    }
    var locY = initialY
    var locX = initialX * 2

    for ((idx, move) in moves.withIndex()) {
 //       println("$move $idx")
        var tmpX = if (move == '<') map[0].length - locX - 1 else locX
        var tmpY = if (move == '^') map.size - locY - 1 else locY
        val tempMap =
            when (move) {
                '<' -> map.map { it.reversed() }
                '^' -> map.indices.reversed().map { map[it] }
                else -> map
            }.toMutableList()

        var didGo = true
        when (move) {
            '<', '>' -> {
                if (tempMap[tmpY].substring(tmpX + 1).substringBefore('#').contains('.')) {
                    val space = tempMap[tmpY].indexOf('.', tmpX + 1)
                    tempMap[tmpY] = tempMap[tmpY].substring(0, tmpX) + '.' + tempMap[tmpY].substring(tmpX, space) +
                            tempMap[tmpY].substring(space + 1)
                    tmpX++
                    didGo = true
                }
            }
            else -> {
                var toCheck = mapOf(Pair(tmpX, tmpY + 1) to '.')
                a@while (toCheck.isNotEmpty()) {
                    val newToCheck = mutableMapOf<Pair<Int, Int>, Char>()
                    for ((p, c) in toCheck) {
                        val (x, y) = p
                        when (tempMap[y][x]) {
                            '#' -> {
                                didGo = false
                                break@a
                            }

                            '[' -> {
                                newToCheck[Pair(x, y + 1)] = '['
                                newToCheck[Pair(x + 1, y + 1)] = ']'
                                if (toCheck.keys.none { it.first == x + 1 && it.second == y}) {
                                    tempMap[y] = tempMap[y].replaceRange(x + 1, x + 2, ".")
                                }
                            }

                            ']' -> {
                                newToCheck[Pair(x, y + 1)] = ']'
                                newToCheck[Pair(x - 1, y + 1)] = '['
                                if (toCheck.keys.none { it.first == x - 1 && it.second == y}) {
                                    tempMap[y] = tempMap[y].replaceRange(x - 1, x, ".")
                                }
                            }
                        }
                        tempMap[y] = tempMap[y].replaceRange(x, x + 1, c.toString())
                    }
                    toCheck = newToCheck
                }
                tmpY++
            }
        }
        if (didGo) {
            map = when (move) {
                '<' -> tempMap.map { it.reversed() }
                '^' -> tempMap.indices.reversed().map { tempMap[it] }
                else -> tempMap
            }
            locX = if (move == '<') map[0].length - tmpX - 1 else tmpX
            locY = if (move == '^') map.size - tmpY - 1 else tmpY
        }
  //      map.forEachIndexed { idx, s -> println(if (idx == locY) s.replaceRange(locX, locX + 1, "@") else s) }
    }
    println(map.mapIndexed { y, s ->
        s.mapIndexedNotNull { x, c ->
            if (c == '[') x + 100 * y else null
        }.sum()
    }.sum())
}

private fun p15a(
    moves: String,
    map: MutableList<String>,
    initialLocY: Int,
    initialLocX: Int
) {
    var locY = initialLocY
    var locX = initialLocX
    for (move in moves) {
        when (move) {
            '<' -> {
                if (map[locY].substring(0, locX).substringAfterLast('#').contains('.')) {
                    val space = map[locY].substring(0, locX).lastIndexOf('.')
                    map[locY] = map[locY].substring(0, space) + map[locY].substring(space + 1, locX) + '.' +
                            map[locY].substring(locX)
                    locX--
                }
            }

            '>' -> {
                if (map[locY].substring(locX + 1).substringBefore('#').contains('.')) {
                    val space = map[locY].indexOf('.', locX + 1)
                    map[locY] = map[locY].substring(0, locX) + '.' + map[locY].substring(locX, space) +
                            map[locY].substring(space + 1)
                    locX++
                }
            }

            '^' -> {
                val str = map.map { it[locX] }.joinToString("")
                if (str.substring(0, locY).substringAfterLast('#').contains('.')) {
                    for (i in str.lastIndexOf('.', locY - 1)..<locY) {
                        map[i] = map[i].replaceRange(locX, locX + 1, map[i + 1][locX].toString())
                    }
                    map[locY] = map[locY].replaceRange(locX, locX + 1, ".")
                    locY--
                }
            }

            'v' -> {
                val str = map.map { it[locX] }.joinToString("")
                if (str.substring(locY + 1).substringBefore('#').contains('.')) {
                    for (i in str.indexOf('.', locY + 1) downTo locY + 1) {
                        map[i] = map[i].replaceRange(locX, locX + 1, map[i - 1][locX].toString())
                    }
                    map[locY] = map[locY].replaceRange(locX, locX + 1, ".")
                    locY++
                }
            }
        }
    }
    println(map.mapIndexed { y, s ->
        s.mapIndexedNotNull { x, c ->
            if (c == 'O') x + 100 * y else null
        }.sum()
    }.sum())
}