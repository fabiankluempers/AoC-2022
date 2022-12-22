package de.kluempers.fabian.aoc2022

import arrow.core.compose
import de.kluempers.fabian.aoc2022.fp.*

object Day08 : Puzzle, HasInput by inputReaderFor(8) {
    override fun part1(): Any {
        val m = indexedMatrix(input)
        val tM = transpose(m)
        val normalizedEdges = listOf(
            m, // left
            m.map(::reverse), // right
            tM, // top
            tM.map(::reverse), // bottom
        )
        return normalizedEdges.flatMap(map(::visibleFromLeft)).reduce(Set<Vec2d>::plus).size
    }

    override fun part2(): Any {
        val m = matrix(input)
        return transpose(m.map(::scenicScores)).zip(transpose(m).map(::scenicScores)).map { (horizontal, vertical) ->
            zipWith(::mul)(horizontal)(vertical)
        }.maxOf(List<Int>::max)
    }
}

private fun visibleFromLeft(list: List<Pair<Vec2d, Int>>) = list.fold(-1 to setOf(), ::visibleIndices).second

private fun visibleIndices(
    visibleHeightAndIndices: Pair<Int, Set<Vec2d>>,
    indexAndHeight: Pair<Vec2d, Int>
): Pair<Int, Set<Vec2d>> {
    val (visibleHeight, visibleIndices) = visibleHeightAndIndices
    val (index, height) = indexAndHeight
    return if (visibleHeight < height) height to (visibleIndices + index) else visibleHeightAndIndices
}

private fun indexedMatrix(input: Input) = matrix(input).mapIndexed { y, ints ->
    ints.mapIndexed { x, i -> Vec2d(x, y) to i }
}

private fun matrix(input: Input) = input.map(map(Char::digitToInt) compose String::toList)

private fun scenicScores(list: List<Int>) = list.mapIndexed { index, height ->
    val left = list.take(index).reversed()
    val right = list.drop(index + 1)

    fun scenicScoreInDirection(viewInDirection : List<Int>) = viewInDirection
        .takeWhile { it < height }.size
        .let {
            // handle literal edge case
            if (viewInDirection.size - it > 0) it + 1 else it
        }

    scenicScoreInDirection(left) * scenicScoreInDirection(right)
}