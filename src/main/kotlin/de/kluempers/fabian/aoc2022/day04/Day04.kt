package de.kluempers.fabian.aoc2022.day04

import arrow.core.*
import de.kluempers.fabian.aoc2022.*

private typealias RangePair = Pair<IntRange, IntRange>

object Day04 : Puzzle, HasInput by inputReaderFor(4) {
    override fun part1(): Any = input.countPairsWhere(RangePair::rangesContained)

    override fun part2(): Any = input.countPairsWhere(RangePair::rangesOverlap)
}

private fun Input.countPairsWhere(predicate: (RangePair) -> Boolean) =
    count(predicate compose ::toRangePair)

private fun toRangePair(input: String) : RangePair = input.split(",")
    .map(::toRange)
    .let { (first, second) -> first to second }

private fun toRange(input : String) = input.split("-").map(String::toInt)
    .let { (lower, upper) -> lower..upper }

private fun RangePair.rangesContained() = first in second || second in first

private fun RangePair.rangesOverlap() =
    if (first.first < second.first) first.last >= second.first
    else second.last >= first.first
