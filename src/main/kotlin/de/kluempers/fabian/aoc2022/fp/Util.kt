package de.kluempers.fabian.aoc2022.fp

import arrow.core.compose
import arrow.core.curried
import arrow.core.uncurried

// functions

fun <A, B, C> flip(f: (A) -> (B) -> C) = { b: B -> { a: A -> f(a)(b) } }

// ordering

fun <T> eq(a: T) = { b: T -> a == b }

fun <T : Comparable<T>> gt(a: T) = { b: T -> a > b }

fun <T : Comparable<T>> lt(a: T) = { b: T -> a < b }

fun <T : Comparable<T>> gte(a: T) = { b: T -> a >= b }

fun <T : Comparable<T>> lte(a: T) = { b: T -> a <= b }

// string

fun split(separator: String) = { s: String -> s.split(separator) }

fun toInt(radix: Int = 10) = { s: String -> s.toInt(radix) }

// ints

fun add(a: Int) = { b: Int -> a + b }

fun mul(a: Int) = { b: Int -> a * b }

fun sub(a: Int) = { b: Int -> a - b }

fun div(a: Int) = { b: Int -> a / b }

// bools

fun not(b: Boolean) = !b

fun and(b: Boolean) = Boolean::and.curried()(b)

fun or(b: Boolean) = Boolean::or.curried()(b)

// Pair

fun <T> first(pair: Pair<T, *>) = pair.first

fun <T> second(pair: Pair<*, T>) = pair.second

// region List/Iterable

// basic

fun length(collection: Collection<*>) = collection.size
fun <T> head(iterable: Iterable<T>) = iterable.first()

fun <T> tail(iterable: Iterable<T>) = iterable.drop(1)

fun <T> cons(elem: T) = { list: List<T> -> listOf(elem) + list }

fun <T> uncons(iterable: Iterable<T>) = head(iterable) to tail(iterable)

fun <T> take(amount: Int) = { iteratee: Iterable<T> -> iteratee.take(amount) }

fun <T> takeWhile(f: (T) -> Boolean) = { iteratee: Iterable<T> -> iteratee.takeWhile(f) }

fun <T> takeLastWhile(f: (T) -> Boolean) = { iteratee: List<T> -> iteratee.takeLastWhile(f) }

fun <T> drop(amount: Int) = { iteratee: Iterable<T> -> iteratee.drop(amount) }

fun <T> dropWhile(f: (T) -> Boolean) = { iteratee: Iterable<T> -> iteratee.dropWhile(f) }

fun <T> dropLastWhile(f: (T) -> Boolean) = { iteratee: List<T> -> iteratee.dropLastWhile(f) }

// transformations
fun <T, R> map(f: (T) -> R) = { iteratee: Iterable<T> -> iteratee.map(f) }

fun <T> filter(f: (T) -> Boolean) = { iteratee: Iterable<T> -> iteratee.filter(f) }

fun <T, R> flatMap(f: (T) -> Iterable<R>) = { iteratee: Iterable<T> -> iteratee.flatMap(f) }

fun <T> reverse(iteratee: Iterable<T>) = iteratee.reversed()

fun <T> window(size: Int, step: Int = 1, partial: Boolean = false) =
    { iteratee: Iterable<T> -> iteratee.windowed(size, step, partial) }

fun <T> distinct(iterable: Iterable<T>) = iterable.toSet()

fun <T> transpose(x: List<List<T>>): List<List<T>> = when {
    x.any(List<T>::isEmpty) -> listOf()
    else -> listOf(x.map(::head)) + transpose(x.map(::tail))
}

// folds

fun <T, R> foldl(initial: R, f: (R) -> (T) -> (R)) = { iteratee: Iterable<T> -> iteratee.fold(initial, f.uncurried()) }

fun <T, R> foldr(initial: R, f: (T) -> (R) -> (R)) = { iteratee: List<T> -> iteratee.foldRight(initial, f.uncurried()) }

fun <T> reduce(f: (T) -> (T) -> (T)) = { iteratee: Iterable<T> -> iteratee.reduce(f.uncurried()) }

fun sum(iteratee: Iterable<Int>) = reduce(::add)(iteratee)

fun product(iteratee: Iterable<Int>) = reduce(::mul)(iteratee)

fun <T> all(f: (T) -> Boolean) = { iteratee: Iterable<T> -> iteratee.all(f) }

fun <T> any(f: (T) -> Boolean) = all(::not compose f)

fun <T> find(f: (T) -> Boolean) = { iteratee: Iterable<T> -> iteratee.find(f) }

// zips

fun <A, B> zip(left: Iterable<A>) = { right: Iterable<B> -> left.zip(right) }

fun <A, B, C> zipWith(f: (A) -> (B) -> C) = { a: Iterable<A> -> { b: Iterable<B> -> a.zip(b, f.uncurried()) } }

// set

fun <T> intersect(a : Set<T>) = { b : Set<T> -> a intersect b }

// endregion


