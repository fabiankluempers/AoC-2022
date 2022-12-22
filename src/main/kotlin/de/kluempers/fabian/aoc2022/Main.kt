package de.kluempers.fabian.aoc2022

import de.kluempers.fabian.aoc2022.day01.Day01
import de.kluempers.fabian.aoc2022.day02.Day02
import de.kluempers.fabian.aoc2022.day03.Day03
import de.kluempers.fabian.aoc2022.day04.Day04
import de.kluempers.fabian.aoc2022.day05.Day05
import de.kluempers.fabian.aoc2022.day06.Day06
import de.kluempers.fabian.aoc2022.day07.Day07
import de.kluempers.fabian.aoc2022.day08.Day08
import de.kluempers.fabian.aoc2022.day09.Day09
import de.kluempers.fabian.aoc2022.day10.Day10
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

val puzzles = listOf<Puzzle>(
  Day01,
  Day02,
  Day03,
  Day04,
  Day05,
  Day06,
  Day07,
  Day08,
  Day09,
  Day10,
)

@OptIn(ExperimentalTime::class)
fun main() {
  val puzzle = puzzles.last()
  runPart(puzzle::part1).print(puzzle.day, "one")
  runPart(puzzle::part2).print(puzzle.day, "two")
}

@OptIn(ExperimentalTime::class)
fun TimedValue<Any>.print(day: Int, part: String) = println(
  """
  Result for Day%02d part $part: 
    - result   : $value
    - duration : $duration
  """.trimIndent().format(day)
)

@OptIn(ExperimentalTime::class)
fun runPart(f: () -> Any) = measureTimedValue {
  try {
    f()
  } catch (e: NotImplementedError) {
    "not yet implemented"
  }
}