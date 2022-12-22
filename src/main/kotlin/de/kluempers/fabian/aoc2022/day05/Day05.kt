package de.kluempers.fabian.aoc2022.day05

import arrow.core.*
import de.kluempers.fabian.aoc2022.HasInput
import de.kluempers.fabian.aoc2022.Input
import de.kluempers.fabian.aoc2022.Puzzle
import de.kluempers.fabian.aoc2022.fp.map
import de.kluempers.fabian.aoc2022.fp.tail
import de.kluempers.fabian.aoc2022.inputReaderFor

object Day05 : Puzzle, HasInput by inputReaderFor(5) {
    override fun part1(): Any = input.solution(reverse = true)

    override fun part2(): Any = input.solution(reverse = false)
}

private fun Input.solution(reverse: Boolean) = getInstructions(this)
    .fold(getInitialCrateStacks()) { stacks, instruction -> instruction(stacks, reverse) }
    .map(List<Char>::last)
    .joinToString("")

private data class Instruction(val amount: Int, val from: Int, val to: Int) {
    operator fun invoke(stacks: List<List<Char>>, reverse: Boolean) = stacks.mapIndexed { index, stack ->
        when (index) {
            from -> stack.dropLast(amount)
            to -> stack + stacks[from].takeLast(amount).let { if (reverse) it.reversed() else it }
            else -> stack
        }
    }
}

private val inputPattern = Regex("move (\\d+) from (\\d+) to (\\d+)")

private fun getInstructions(input : Input) = input.drop(10)
    .mapNotNull(inputPattern::matchEntire)
    .map(map(String::toInt) compose ::tail compose MatchResult::groupValues)
    .map { Instruction(it[0], it[1] - 1, it[2] - 1) }

private fun Input.getInitialCrateStacks() = (0..8).map(this::getCrateStack)

private fun Input.getCrateStack(index: Int) = take(8).foldRight(listOf<Char>()) { line, list ->
    val crate = line.getOrNull(4 * index + 1) ?: ' '
    if (crate == ' ') list else list + crate
}