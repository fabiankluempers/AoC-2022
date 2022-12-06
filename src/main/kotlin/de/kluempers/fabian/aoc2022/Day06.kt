package de.kluempers.fabian.aoc2022


object Day06 : Puzzle, HasInput by inputReaderFor(6) {
    override fun part1(): Any = input.solution(4)

    override fun part2(): Any = input.solution(14)

}

private fun Input.solution(windowSize : Int) = first().asSequence()
    .zip(generateSequence(1) { it + 1 })
    .windowed(windowSize)
    .first { window -> window.map { it.first }.toSet().size == windowSize }
    .last().second