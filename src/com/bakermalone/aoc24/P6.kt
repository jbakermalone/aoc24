package com.bakermalone.aoc24

import com.bakermalone.aoc23.readLines

enum class Dir { U, R, D, L }
val Pair<Int, Int>.row
    get() = first
val Pair<Int, Int>.col
    get() = second

fun main() {
    lateinit var start: Pair<Int, Int>
    val rows = readLines("p6").mapIndexed { idx, l ->
        l.mapIndexedNotNull { idx2, c ->
            if (c == '^') start = idx to idx2
            if (c == '#') idx2 else null
        }
    }
    val cols = List(rows.size) { mutableListOf<Int>() }
    rows.forEachIndexed { idx, r ->
        r.forEach {
            cols[it].add(idx)
        }
    }
    println(rows)
    println(cols)
    val visited = p6a(rows, cols, start)
    p6b(rows, cols, start, visited!!)
}

fun p6b(
    rows: List<List<Int>>,
    cols: List<List<Int>>,
    start: Pair<Int, Int>,
    visited: Set<Pair<Int, Int>>
) {
    // 7 6
    println(visited)
    println(visited.count { (r, c) ->
        val newRows = ArrayList(rows)
        newRows[r] = rows[r].plus(c).sorted()
        val newCols = ArrayList(cols)
        newCols[c] = cols[c].plus(r).sorted()
        val p6a = p6a(newRows, newCols, start)
        if (p6a == null) println("found $r $c")
        p6a == null
    })

}

fun p6a(rows: List<List<Int>>, cols: List<List<Int>>, start: Pair<Int, Int>): Set<Pair<Int, Int>>? {
    val us = mutableSetOf<Pair<Int, Int>>()
    val ds = mutableSetOf<Pair<Int, Int>>()
    val ls = mutableSetOf<Pair<Int, Int>>()
    val rs = mutableSetOf<Pair<Int, Int>>()
    var dir = Dir.U
    var pos = start
    while (true) {
//        println(dir)
//        println(pos)
        when (dir) {
            Dir.U -> {
                val block = cols[pos.col].findLast { it < pos.row }
                (((block ?: -1) + 1)..pos.row).mapTo(us) {
                    it to pos.col
                }
                if (block == null) break
                pos = block + 1 to pos.col
                if (rs.contains(pos)) return null
                dir = Dir.R
            }
            Dir.R -> {
                val block = rows[pos.row].find { it > pos.col }
                (pos.col..<(block ?: cols.size)).mapTo(rs) {
                    pos.row to it
                }
                if (block == null) break
                pos = pos.row to block - 1
                if (ds.contains(pos)) return null
                dir = Dir.D
            }
            Dir.D -> {
                val block = cols[pos.col].find { it > pos.row }
                (pos.row..<(block ?: rows.size)).mapTo(ds) {
                    it to pos.col
                }
                if (block == null) break
                pos = block - 1 to pos.col
                if (ls.contains(pos)) return null
                dir = Dir.L
            }
            Dir.L -> {
                val block = rows[pos.row].findLast { it < pos.col }
                (((block ?: - 1) + 1)..pos.col).mapTo(ls) {
                    pos.row to it
                }
                if (block == null) break
                pos = pos.row to block + 1
                if (us.contains(pos)) return null
                dir = Dir.U
            }
        }
    }
    val result = us + ds + ls + rs
//    println(result.size)
    return result
}
