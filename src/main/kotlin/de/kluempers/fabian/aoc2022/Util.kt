package de.kluempers.fabian.aoc2022

import arrow.core.curried
import java.io.File

fun readInputFor(day : Int) = File("input/Day%02d.txt".format(day)).readLines()

fun <A,B,C> flip(f : (A, B) -> C) : (B, A) -> C = { b, a -> f(a, b) }

fun <A,B,C> flipCurried(f : (A, B) -> C) : (B) -> (A) -> C = flip(f).curried()

fun <T> head(iterable: Iterable<T>) = iterable.first()

fun <T> tail(iterable: Iterable<T>) = iterable.drop(1)

operator fun <T : Comparable<T>> ClosedRange<T>.contains(other: ClosedRange<T>) =
    (start in other && endInclusive in other) || (other.start in this && other.endInclusive in this)

fun <T,R> fmap(f: (T) -> R) = { iterable: Iterable<T> -> iterable.map(f) }