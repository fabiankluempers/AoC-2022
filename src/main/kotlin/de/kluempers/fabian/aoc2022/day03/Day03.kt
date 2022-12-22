package de.kluempers.fabian.aoc2022.day03

import arrow.core.compose
import de.kluempers.fabian.aoc2022.HasInput
import de.kluempers.fabian.aoc2022.Puzzle
import de.kluempers.fabian.aoc2022.fp.head
import de.kluempers.fabian.aoc2022.inputReaderFor

object Day03 : Puzzle, HasInput by inputReaderFor(3) {
    override fun part1(): Any = input
        .map { it.take(it.length / 2).toSet() intersect it.drop(it.length / 2).toSet() }
        .sumOf(Char::priority compose ::head)

    override fun part2(): Any = input
        .chunked(3)
        .map { it.map(String::toSet).reduce(Set<Char>::intersect) }
        .sumOf(Char::priority compose ::head)
}

private fun Char.priority() = when (code) {
    in 'a'.code..'z'.code -> (code - 'a'.code) + 1
    in 'A'.code..'Z'.code -> (code - 'A'.code) + 27
    else -> error("no priority for $this")
}


