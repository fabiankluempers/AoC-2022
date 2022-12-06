package de.kluempers.fabian.aoc2022

import arrow.core.compose
import de.kluempers.fabian.aoc2022.fp.*


object Day06 : Puzzle, HasInput by inputReaderFor(6) {
    override fun part1(): Any = input.solution(4)

    override fun part2(): Any = input.solution(14)

}

private fun Input.solution(windowSize : Int) = first().asSequence()
    .zip(generateSequence(1, add(1)))
    .windowed(windowSize)
    .first(eq(windowSize) compose ::length compose map(Pair<Char,Int>::first))
    .last().second