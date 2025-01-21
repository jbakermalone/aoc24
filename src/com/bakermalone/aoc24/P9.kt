package com.bakermalone.aoc24

import com.bakermalone.aoc23.readLines

fun main() {
    val input = readLines("p9")[0].map { it.digitToInt() }
    p9b(input)
}

private val DEBUG = false

private data class Item(
    var size: Int,
    var number: Int,
    var moved: Boolean,
    var next: Item?,
    var prev: Item?,
    var startPos: Long,
) {
    fun remove() {
        if (number >= 0) {
            val prev = prev!!
            val next = next
            if (prev.number < 0) {
                prev.size += size
                if (next != null && next.number < 0) {
                    prev.size += next.size
                    next.remove()
                }
                prev.next = this.next
                this.next?.prev = prev
            }
            else if (next != null && next.number < 0) {
                next.startPos = startPos
                next.size += size
                next.prev = prev
                prev.next = next
            }
            else {
                val newItem = Item(size, -number, false, next, prev, startPos)
                prev.next = newItem
                next?.prev = newItem
            }
        }
        else {
            prev?.next = next
            next?.prev = prev
        }
    }

    fun replace(item: Item) {
        item.remove()
        item.startPos = startPos
        prev?.next = item
        next?.prev = item
        item.next = next
        item.prev = prev
        item.moved = true
        prev = null
        next = null
    }

    fun insertAfter(item: Item) {
        item.startPos = startPos + size
        next?.prev = item
        item.next = next
        item.prev = this
        next = item
    }

    fun nextBlank() = generateSequence(next) { it.next }.find { it.number < 0 }
    override fun toString() = "n: $number s: $size"
}

fun p9b(input: List<Int>) {

    var c2 = 0
    var total2 = 0L
    println("initial:")
    for ((idx, i) in input.withIndex()) {
        repeat(i) {
            if (i > 0) {
                val inc = idx / 2.toLong() * c2.toLong()
                total2 += inc
                assert(inc > 0)
            }
            c2++
        }
    }
    println(total2)
    if (DEBUG) println(input.sum())
    println()
    var last_: Item? = null
    var first_: Item? = null
    var pos = 0L
    input.forEachIndexed { idx, v ->
        if (v == 0) return@forEachIndexed
        val n = Item(v, if (idx % 2 == 0) idx / 2 else (idx + 1) / -2, false, null, last_, pos)
        pos += v
        last_?.next = n
        last_ = n
        if (first_ == null) {
            first_ = n
        }
    }
    val first = first_!!
    var firstBlank = first.nextBlank()!!
    var toRemove = last_!!
    if (DEBUG) {
        for (i in generateSequence(first) { it.next }) {
            repeat(i.size) {
                print(if (i.number >= 0) i.number else '.')
            }
        }
        println()
    }
    a@while (true) {
        if (toRemove.number < 0 || toRemove.moved) {
            toRemove = toRemove.prev ?: break
            continue
        }
        var b = firstBlank
        while (true) {
            if (toRemove.startPos < b.startPos) break
            if (b.size >= toRemove.size) {
                val nextToRemove = toRemove.prev
                b.replace(toRemove)
                if (b.size > toRemove.size) {
                    toRemove.insertAfter(Item(b.size - toRemove.size, b.number, false, null, null, 0))
                }
                if (b == firstBlank) {
                    firstBlank = toRemove.nextBlank() ?: break@a
                }
                if (DEBUG) {
                    var x = 0
                    var y = 'a'
                    for (i in generateSequence(first) { if (x < 80) it.next else null }) {
                        repeat(i.size) {
                            x++
                            print(
                                when {
                                    i.number < 0 -> '.'
                                    i.number < 10 -> i.number.toString()[0]
                                    else -> y
                                }
                            )
                        }
                        if (i.number >= 10) y++
                    }
                    println()
                }
                toRemove = nextToRemove ?: break@a
                continue@a
            }
            b = b.nextBlank() ?: break
        }
        toRemove = toRemove.prev ?: break
    }

    var c = 0
    var total = 0L
    for (i in generateSequence(first) { it.next }) {
        repeat(i.size) {
            if (DEBUG) print(if (i.number >= 0) i.number else '.')
            if (i.number > 0) {
               total += i.number.toLong() * c.toLong()
                assert(total > 0)
            }
            c++
        }
    }
    if (DEBUG) {
        println()
        println(c)
    }
    println(total)
}

/*
fun p9b(input_: List<Int>) {
    val result = LinkedList(input_.mapIndexedNotNull { idx, v ->
        if (v == 0) null
        else Item(v, if (idx % 2 == 0) idx / 2 else -1)
    })
    val start = result.listIterator()
    a@while(true) {
        val v = result.removeLast()
        if (v.number < 0 || v.moved) continue

        var toReset = 0
        while(true) {
            if (!start.hasNext()) {
                break
            }
            val (p, n) = start.next()
            toReset++
            if (n >= 0) continue
            if (p >= v.size) {
                start.remove()
                start.add(v.apply { moved = true })
                if (n > v.size) {
                    start.add(Item(n - v.size, -1))
                    toReset++
                }
                else if (toReset == 1) {
                    while (start.next().number >= 0) {}
                    start.previous()
                }
                else {
                    repeat(toReset) {
                        start.previous()
                    }
                }
            }
        }
    }
}
*/
fun p9a(input: List<Int>) {
    var endPtr = input.size - 1
    if (endPtr % 2 == 1) endPtr--
    var consumed = 0
    var startPos = 0
    println(input.mapIndexed { index, v ->
        if (endPtr < index) 0 //.also { print(0) }
        else {
            (0..<v).sumOf { i ->
                if (endPtr < index || (endPtr == index && consumed >= v - i)) 0L //.also { print(0) }
                else {
                    if (index % 2 == 0) {
                        //print(index / 2)
                        (startPos++ * (index / 2)).toLong()
                    } else {
                        if (consumed == input[endPtr]) {
                            endPtr -= 2
                            while (input[endPtr] == 0) endPtr -= 2
                            consumed = 0
                        }
                        val result = startPos++ * (endPtr / 2)
                        consumed++
                        result.toLong()
                    }
                }
            }
        }
    }.sum())
}
