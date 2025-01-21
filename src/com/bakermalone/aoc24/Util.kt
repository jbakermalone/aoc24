package com.bakermalone.aoc23

import java.nio.file.Files
import java.nio.file.Paths

fun readLines(file: String): List<String> {
  return Files.readAllLines(Paths.get("data", file))
}

fun transpose(segment: List<String>) = (0 ..< segment[0].length).map { idx ->
  segment.map { it[idx] }.joinToString("")
}

fun transpose2(segment: List<CharArray>) = (0 ..< segment[0].size).map { idx ->
  segment.map { it[idx] }.toCharArray()
}
