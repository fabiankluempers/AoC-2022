package de.kluempers.fabian.aoc2022.day10

import de.kluempers.fabian.aoc2022.HasInput
import de.kluempers.fabian.aoc2022.Input
import de.kluempers.fabian.aoc2022.Puzzle
import de.kluempers.fabian.aoc2022.inputReaderFor

object Day10 : Puzzle, HasInput by inputReaderFor(10) {
    override fun part1(): Any = input
        .executeProgram()
        .filter { it.cycle in measurePoints }
        .sumOf { it.cycle * it.register }

    override fun part2(): Any = input
        .executeProgram()
        .chunked(40) { it.zip(0..39, ExecutionState::toPixel) }
        .joinToString("\n", "\n", "\n") { it.joinToString("") }
}

private fun ExecutionState.toPixel(crtColIndex : Int) =
    if (crtColIndex in register - 1..register + 1) '#' else '.'
private fun Input.executeProgram() = fold(listOf(ExecutionState(1, 1)), List<ExecutionState>::executeNext)

private val measurePoints = listOf(20, 60, 100, 140, 180, 220)

private data class ExecutionState(val cycle: Int, val register: Int)

private fun List<ExecutionState>.executeNext(input: String) = when {
    input.startsWith("noop") -> this + last().noop()
    input.startsWith("addx") -> this + last().addx(input.split(" ")[1].toInt())
    else -> error("$input is not a valid instruction")
}

private fun ExecutionState.addx(value: Int): List<ExecutionState> =
    noop() + ExecutionState(cycle + 2, register + value)

private fun ExecutionState.noop() = listOf(copy(cycle = cycle + 1))