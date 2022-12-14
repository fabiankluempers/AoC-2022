package de.kluempers.fabian.aoc2022.day02

import de.kluempers.fabian.aoc2022.HasInput
import de.kluempers.fabian.aoc2022.Input
import de.kluempers.fabian.aoc2022.Puzzle
import de.kluempers.fabian.aoc2022.day02.GameResult.*
import de.kluempers.fabian.aoc2022.day02.RockPaperScissors.*
import de.kluempers.fabian.aoc2022.inputReaderFor

private enum class RockPaperScissors(val value: Int) {
  ROCK(1),
  PAPER(2),
  SCISSORS(3);

  fun play(enemy: RockPaperScissors) = when (this) {
    ROCK     -> when (enemy) {
      ROCK     -> DRAW
      PAPER    -> LOSS
      SCISSORS -> WIN
    }
    PAPER    -> when (enemy) {
      ROCK     -> WIN
      PAPER    -> DRAW
      SCISSORS -> LOSS
    }
    SCISSORS -> when (enemy) {
      ROCK     -> LOSS
      PAPER    -> WIN
      SCISSORS -> DRAW
    }
  }

  fun pickForResult(result: GameResult) = when (this) {
    ROCK     -> when (result) {
      LOSS -> SCISSORS
      DRAW -> ROCK
      WIN  -> PAPER
    }
    PAPER    -> when (result) {
      LOSS -> ROCK
      DRAW -> PAPER
      WIN  -> SCISSORS
    }
    SCISSORS -> when (result) {
      LOSS -> PAPER
      DRAW -> SCISSORS
      WIN  -> ROCK
    }
  }
}

private enum class GameResult(val value: Int) {
  LOSS(0),
  DRAW(3),
  WIN(6),
}

private fun String.toRockPaperScissors() = when {
  this == "A" || this == "X" -> ROCK
  this == "B" || this == "Y" -> PAPER
  this == "C" || this == "Z" -> SCISSORS
  else                       -> error("Can't map '$this' to ${RockPaperScissors::class.simpleName}")
}

private fun String.toGameResult() = when {
  this == "X" -> LOSS
  this == "Y" -> DRAW
  this == "Z" -> WIN
  else        -> error("Can't map '$this' to ${GameResult::class.simpleName}")
}

private val inputPattern = Regex("([ABC])\\s([XYZ])\\s*")

private data class Game(val enemy: RockPaperScissors, val me: RockPaperScissors) {
  val result = me.play(enemy).value + me.value
}

private fun missingMatchGroup(): Nothing = error("Missing match group")

private fun Input.playGames(myChoice: (String, RockPaperScissors) -> RockPaperScissors) = map {
  with(inputPattern.matchEntire(it)?.groups) {
    requireNotNull(this) { "$it doesn't match input pattern" }
    val enemyChoice = get(1)?.value?.toRockPaperScissors() ?: missingMatchGroup()
    val myChoiceString = get(2)?.value ?: missingMatchGroup()
    Game(enemyChoice, myChoice(myChoiceString, enemyChoice))
  }
}.map(Game::result).sum()

object Day02 : Puzzle, HasInput by inputReaderFor(2) {

  override fun part1(): Any = input.playGames { rawInput, _ ->
    rawInput.toRockPaperScissors()
  }

  override fun part2(): Any = input.playGames { rawInput, enemyChoice ->
    enemyChoice.pickForResult(rawInput.toGameResult())
  }
}