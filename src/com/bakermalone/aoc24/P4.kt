package com.bakermalone.aoc24

import com.bakermalone.aoc23.readLines

fun main() {
    val lines = readLines("p4")
    p4b(lines)
}
fun p4b(lines: List<String>) {
    println(
        (1 ..< lines.size - 1).sumOf { x ->
            (1 ..< lines.size - 1).count { y ->
                lines[x][y] == 'A' && (
                        ((lines[x-1][y-1] == 'M' && lines[x+1][y+1] == 'S') ||
                                (lines[x-1][y-1] == 'S' && lines[x+1][y+1] == 'M')) &&
                                ((lines[x+1][y-1] == 'M' && lines[x-1][y+1] == 'S') ||
                                        (lines[x+1][y-1] == 'S' && lines[x-1][y+1] == 'M'))
                        )
            }
        }
    )
}

fun p4a(lines: List<String>) {
    val lines2 = lines.indices.map { idx -> lines.map { it[idx] }.joinToString("") }
    val lines3 = ((-lines.size + 1)..(lines.size - 1)).map { idx2 ->
        lines.mapIndexedNotNull { index, s ->
            s.getOrNull(index + idx2)
        }.joinToString("")
    }
    val lines4 = ((2 * lines.size - 2) downTo 0).map { idx2 ->
        lines.mapIndexedNotNull { index, s ->
            s.getOrNull(idx2 - index)
        }.joinToString("")
    }
    val p1 = Regex("XMAS")
    val p2 = Regex("SAMX")
    println(
        lines.sumOf { p1.findAll(it).count() } +
                lines.sumOf { p2.findAll(it).count() } +
                lines2.sumOf { p1.findAll(it).count() } +
                lines2.sumOf { p2.findAll(it).count() } +
                lines3.sumOf { p1.findAll(it).count() } +
                lines3.sumOf { p2.findAll(it).count() } +
                lines4.sumOf { p1.findAll(it).count() } +
                lines4.sumOf { p2.findAll(it).count() }
    )
}