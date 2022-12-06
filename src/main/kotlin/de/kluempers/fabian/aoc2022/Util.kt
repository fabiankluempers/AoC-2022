package de.kluempers.fabian.aoc2022

import arrow.core.curried
import java.io.File

fun readInputFor(day : Int) = File("input/Day%02d.txt".format(day)).readLines()

operator fun <T : Comparable<T>> ClosedRange<T>.contains(other: ClosedRange<T>) =
    (start in other && endInclusive in other) || (other.start in this && other.endInclusive in this)

