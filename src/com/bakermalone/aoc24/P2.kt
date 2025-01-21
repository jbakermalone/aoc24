import com.bakermalone.aoc23.readLines
import java.util.regex.Pattern
import kotlin.math.sign

fun main() {
    val reports = readLines("p2").map {
        it.trim().split(Pattern.compile(" +")).map { it.toInt() }
    }
    val orig = p2b(reports)
    val new = p2b2(reports)
    println(new.minus(orig))
}

fun p2a(reports: List<List<Int>>) {
    println(reports.count {
        check(it)
    })
}

private fun check(it: List<Int>): Boolean {
    val sign = if (it[1] - it[0] > 0) 1 else -1
    return it.windowed(2).all { (f, s) ->
        (s - f) * sign in 1..3
    }
}
fun p2b2(reports: List<List<Int>>): List<List<Int>> {
   val result = reports.filter { l ->
       if (check(l)) return@filter true
       for (i in l.indices) {
           val removed = l.filterIndexed { index, _ -> index != i }
           if (check(removed)) return@filter true
       }
       false
   }
    println(result.size)
    return result
}

fun p2b(reports: List<List<Int>>): List<List<Int>> {
    val result = reports.filter { l ->
        val sign = l.windowed(2).groupBy { (f, s) -> sign(s.toFloat() - f).toInt() }.maxBy { it.value.size }.key
        if (l.windowed(2).all { (f, s) ->
            (s - f) * sign in 1..3
        }) return@filter true

        var found = false
        var skip = false
        for (i in 0 until l.size - 1) {
            if (skip) {
                skip = false
                continue
            }
            if ((l[i + 1] - l[i]) * sign !in 1..3) {
                if (i > 0 && ((l[i + 1] - l[i-1]) * sign in 1..3)) {
                    if (found) {
                        return@filter false//.also { println("a: $l") }
                    } else found = true
                }
                else if (i == l.size - 2 || ((l[i + 2] - l[i]) * sign in 1..3)) {
                    if (found) {
                        return@filter false//.also { println("c: $l") }
                    } else found = true.also { skip = true }
                } else return@filter false//.also { println("b: $l") }
            }
        }
        true
    }
    println(result.size)
    return result
}