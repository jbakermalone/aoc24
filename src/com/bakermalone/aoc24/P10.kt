package com.bakermalone.aoc24

import com.bakermalone.aoc23.readLines

fun main() {
    val input = readLines("p10").map {
        it.map { it.digitToInt() }
    }
   // p10a(input)
    p10b(input)
}

fun p10b(input: List<List<Int>>) {
    var scores = input.map { it.map { it to if (it == 9) 1 else 0 }}
    for (i in 8 downTo 0) {
        val new = scores.mapIndexed { idx1, r ->
            r.mapIndexed { idx2, (v, s) ->
                if (v != i) v to s
                else {
                    v to
                            (if (idx1 > 0 && scores[idx1 - 1][idx2].first == v + 1) scores[idx1 - 1][idx2].second else 0) +
                            (if (idx1 < scores.size - 1 && scores[idx1 + 1][idx2].first == v + 1) scores[idx1 + 1][idx2].second else 0) +
                            (if (idx2 > 0 && scores[idx1][idx2 - 1].first == v + 1) scores[idx1][idx2 - 1].second else 0) +
                            (if (idx2 < scores.size - 1 && scores[idx1][idx2 + 1].first == v + 1) scores[idx1][idx2 + 1].second else 0)
                }
            }
        }
        scores = new
    }
    println(scores.sumOf { it.filter { it.first == 0}.sumOf { it.second } })
}

fun p10a(input: List<List<Int>>) {
    println(input.mapIndexed { x, line ->
        line.mapIndexed { y, c ->
            if (c != 0) 0
            else {
                val trails = mutableSetOf(x to y)
                var total = 0
                while (trails.isNotEmpty()) {
                    val n = trails.first()
                    trails.remove(n)
                    val v = input[n.first][n.second]
                    if (v == 9) {
                        total++
                    }
                    if (n.first > 0 && input[n.first - 1][n.second] == v + 1) {
                        trails.add(n.first - 1 to n.second)
                    }
                    if (n.second > 0 && input[n.first][n.second - 1] == v + 1) {
                        trails.add(n.first to n.second - 1)
                    }
                    if (n.first < input.size - 1 && input[n.first + 1][n.second] == v + 1) {
                        trails.add(n.first + 1 to n.second)
                    }
                    if (n.second < input.size - 1 && input[n.first][n.second + 1] == v + 1) {
                        trails.add(n.first to n.second + 1)
                    }
                }
                total
            }
        }.sum()
    }.sum())
}
