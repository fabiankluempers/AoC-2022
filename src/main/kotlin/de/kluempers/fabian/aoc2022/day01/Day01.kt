package de.kluempers.fabian.aoc2022.day01

import de.kluempers.fabian.aoc2022.HasInput
import de.kluempers.fabian.aoc2022.Input
import de.kluempers.fabian.aoc2022.Puzzle
import de.kluempers.fabian.aoc2022.inputReaderFor
import kotlin.math.max

object Day01 : Puzzle, HasInput by inputReaderFor(1) {

  override fun part1(): Any = input.fold(Pair(0, 0)) { (max, current), line ->
    if (line.isBlank()) Pair(max(current, max), 0)
    else Pair(max, current + line.toInt())
  }.first

  override fun part2(): Any = input.toCaloriesPerElf().sortedDescending().take(3).sum()

}

private tailrec fun Input.toCaloriesPerElf(calories: List<Int> = listOf()): List<Int> =
  if (isEmpty())
    calories
  else {
    val elf = takeWhile { it.isNotBlank() }.map { it.toInt() }
    drop(elf.size).dropWhile { it.isBlank() }.toCaloriesPerElf(calories + elf.sum())
  }