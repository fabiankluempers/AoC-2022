package de.kluempers.fabian.aoc2022

typealias Input = List<String>
interface HasInput {
  val input : Input
  val day: Int
}

fun inputReaderFor(day: Int) = object : HasInput {
  override val input: Input by lazy { readInputFor(day) }
  override val day: Int = day
}

