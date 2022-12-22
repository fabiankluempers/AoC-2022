package de.kluempers.fabian.aoc2022.day09

import de.kluempers.fabian.aoc2022.HasInput
import de.kluempers.fabian.aoc2022.Puzzle
import de.kluempers.fabian.aoc2022.Vec2d
import de.kluempers.fabian.aoc2022.fp.uncons
import de.kluempers.fabian.aoc2022.inputReaderFor
import kotlin.math.abs

// Yes this solution has very poor performance and is hard to read, but I won't fix it.
object Day09 : Puzzle, HasInput by inputReaderFor(9) {
    override fun part1(): Any = input.map {
        val (direction, value) = it.split(" ")
        Instruction(Direction.valueOf(direction.first()), value.toInt())
    }.fold(PositionState.initial to setOf(Vec2d(0,0))) { (state, visited), instruction: Instruction ->
        state.moveHead(instruction).followHead().let { it to visited + it.tailPosition }
    }.second.size

    override fun part2(): Any = input.map {
        val (direction, value) = it.split(" ")
        Instruction(Direction.valueOf(direction.first()), value.toInt())
    }.fold(part2InitialPositions() to setOf(Vec2d(0,0)), FoldState::executeInstruction).second.size
}

private typealias FoldState = Pair<List<Vec2d>, Set<Vec2d>>
private tailrec fun FoldState.executeInstruction(instruction: Instruction): FoldState {
    if (instruction.value == 0) return this
    val (head, tail) = uncons(first)
    val newHeadPos = head.move(1, instruction.direction)
    return tail.fold(newHeadPos to listOf(newHeadPos)) { (headPos, positions), tailPos ->
        val newTailPos = PositionState(headPos,tailPos).followHead().tailPosition
        newTailPos to positions + newTailPos
    }.let { it.second to second + it.first }.executeInstruction(instruction.copy(value = instruction.value - 1))
}
private fun part2InitialPositions() = generateSequence { Vec2d(0,0) }.take(10).toList()

private enum class Direction {
    LEFT, RIGHT, UP, DOWN;

    companion object {
        fun valueOf(c: Char) = when (c) {
            'L' -> LEFT
            'R' -> RIGHT
            'U' -> UP
            'D' -> DOWN
            else -> error("can't parse '$c' as ${Direction::class.simpleName}")
        }
    }
}

private fun PositionState.moveHead(instruction: Instruction) =
    copy(headPosition = headPosition.move(instruction.value, instruction.direction))

private data class Instruction(val direction: Direction, val value: Int)

private fun Vec2d.move(value: Int, direction: Direction) = when (direction) {
    Direction.LEFT -> moveOnX(-value)
    Direction.RIGHT -> moveOnX(value)
    Direction.UP -> moveOnY(value)
    Direction.DOWN -> moveOnY(-value)
}

private fun Vec2d.moveOnX(value: Int) = Vec2d(x + value, y)

private fun Vec2d.moveOnY(value: Int) = Vec2d(x, y + value)

private infix fun Vec2d.touches(other: Vec2d) = abs(x - other.x) <= 1 && abs(y - other.y) <= 1

private data class PositionState(val headPosition: Vec2d, val tailPosition: Vec2d) {
    companion object {
        val initial = PositionState(Vec2d(0, 0), Vec2d(0, 0))
    }
}

private tailrec fun PositionState.followHead(): PositionState =
    if (tailPosition touches headPosition) this
    else when {
        headPosition.x == tailPosition.x -> tailPosition.moveOnY(valueForYMove())
        headPosition.y == tailPosition.y -> tailPosition.moveOnX(valueForXMove())
        else -> tailPosition.moveOnX(valueForXMove()).moveOnY(valueForYMove())
    }.let { PositionState(headPosition, it) }.followHead()

private fun PositionState.valueForXMove() = if (headPosition.x > tailPosition.x) 1 else -1
private fun PositionState.valueForYMove() = if (headPosition.y > tailPosition.y) 1 else -1
