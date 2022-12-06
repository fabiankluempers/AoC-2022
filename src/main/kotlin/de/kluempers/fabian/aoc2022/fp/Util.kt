package de.kluempers.fabian.aoc2022.fp

import arrow.core.compose
import arrow.core.curried
import arrow.core.uncurried

// equality

fun <T> eq(a: T) = { b : T -> a == b }

// ints

fun add(a: Int) = { b : Int -> a + b }

fun mul(a: Int) = { b : Int -> a * b }

fun sub(a: Int) = { b : Int -> a - b }

fun div(a: Int) = { b : Int -> a / b }

// bools

fun not(b : Boolean) = !b

fun and(b : Boolean) = Boolean::and.curried()(b)

fun or(b: Boolean) = Boolean::or.curried()(b)

// Pair

fun <T> first(pair : Pair<T,*>) = pair.first

fun <T> second(pair : Pair<*,T>) = pair.second

// region List/Iterable

// basic

fun length(collection: Collection<*>) = collection.size
fun <T> head(iterable: Iterable<T>) = iterable.first()

fun <T> tail(iterable: Iterable<T>) = iterable.drop(1)

fun <T> take(amount : Int) = { x : Iterable<T> -> x.take(amount) }

fun <T> takeWhile(f: (T) -> Boolean) = { x : Iterable<T> -> x.takeWhile(f) }

fun <T> drop(amount : Int) = { x : Iterable<T> -> x.drop(amount) }

fun <T> dropWhile(f: (T) -> Boolean) = { x : Iterable<T> -> x.dropWhile(f) }

// transformations
fun <T,R> map(f: (T) -> R) = { x: Iterable<T> -> x.map(f) }

fun <T> filter(f: (T) -> Boolean) = { x : Iterable<T> -> x.filter(f) }

fun <T,R> flatMap(f: (T) -> Iterable<R>) = { x: Iterable<T> -> x.flatMap(f) }

fun <T> reverse(x : Iterable<T>) = x.reversed()

fun <T> window(size: Int, step: Int = 1, partial : Boolean = false) =
    { x : Iterable<T> -> x.windowed(size, step, partial) }

fun <T> distinct(iterable: Iterable<T>) = iterable.toSet()

// folds

fun <T,R> foldl(f : (R) -> (T) -> (R)) = { initial: R -> { x: Iterable<T> -> x.fold(initial, f.uncurried()) } }

fun <T,R> foldr(f : (T) -> (R) -> (R)) = { initial: R -> { x: List<T> -> x.foldRight(initial, f.uncurried()) } }

fun <T> reduce(f: (T) -> (T) -> (T)) = { x: Iterable<T> -> x.reduce(f.uncurried()) }

fun sum(x : Iterable<Int>) = reduce(::add)

fun product(x : Iterable<Int>) = reduce(::mul)

fun <T> all(f: (T) -> Boolean) = { x: Iterable<T> -> x.all(f) }

fun <T> any(f: (T) -> Boolean) = all(::not compose f)

fun <T> find(f: (T) -> Boolean) = { x: Iterable<T> -> x.find(f) }

// zips

fun <A,B> zip(left : Iterable<A>) = { right : Iterable<B> -> left.zip(right) }

fun <A,B,C> zipWith(f: (A) -> (B) -> C) = { a: Iterable<A> -> { b: Iterable<B> -> a.zip(b, f.uncurried()) } }

// endregion

