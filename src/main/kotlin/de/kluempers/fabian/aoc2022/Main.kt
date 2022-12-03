import de.kluempers.fabian.aoc2022.Day02
import de.kluempers.fabian.aoc2022.Day03
import kotlin.time.ExperimentalTime
import kotlin.time.TimedValue
import kotlin.time.measureTimedValue

val puzzles = listOf<Puzzle>(
  Day01,
  Day02,
  Day03,
)

@OptIn(ExperimentalTime::class)
fun main() {
  val puzzle = puzzles.last()
  runPart(puzzle::part1).print(puzzle.day, "one")
  runPart(puzzle::part2).print(puzzle.day, "two")
}

@OptIn(ExperimentalTime::class)
fun TimedValue<Any>.print(day: Int, part: String) = println(
  """
  Result for Day%02d part $part: 
    - result   : $value
    - duration : $duration
  """.trimIndent().format(day)
)

@OptIn(ExperimentalTime::class)
fun runPart(f: () -> Any) = measureTimedValue {
  try {
    f()
  } catch (e: NotImplementedError) {
    "not yet implemented"
  }
}