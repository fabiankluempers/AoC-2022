package de.kluempers.fabian.aoc2022.day06

import arrow.core.compose
import de.kluempers.fabian.aoc2022.HasInput
import de.kluempers.fabian.aoc2022.Puzzle
import de.kluempers.fabian.aoc2022.fp.*
import de.kluempers.fabian.aoc2022.inputReaderFor


object Day06 : Puzzle, HasInput by inputReaderFor(6) {
    override fun part1(): Any = solution(4)(input.first())

    override fun part2(): Any = solution(14)(input.first())

}

private fun solution(windowSize: Int) = { input: String ->
    input.asSequence()
        .zip(generateSequence(1, add(1)))
        .windowed(windowSize)
        .first(eq(windowSize) compose ::length compose map(::first))
        .last().second
}