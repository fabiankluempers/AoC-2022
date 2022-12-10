package de.kluempers.fabian.aoc2022

import de.kluempers.fabian.aoc2022.fp.uncons

object Day07 : Puzzle, HasInput by inputReaderFor(7) {
    override fun part1(): Any = parseToFileSystem(input.drop(1), "/").second.toList()
        .filterIsInstance<Dir>()
        .filter { it.size <= 100_000 }
        .sumOf(Dir::size)

    override fun part2(): Any {
        val fs = parseToFileSystem(input.drop(1), "/").second

        val maxSize = 70_000_000

        val remainingSize = maxSize - fs.size

        val neededSize = 30_000_000

        return fs.toList()
            .filterIsInstance<Dir>()
            .sortedBy(Dir::size)
            .first { it.size >= (neededSize - remainingSize) }
            .size
    }

}

private sealed interface FileSystem {
    val size: Long
}

private data class File(val name: String, override val size: Long) : FileSystem {
    constructor(instruction: String) : this(
        instruction.dropWhile { it != ' ' }.drop(1),
        instruction.takeWhile { it != ' ' }.toLong()
    )
}

private data class Dir(val name: String, override val size: Long, val contents: List<FileSystem>) : FileSystem {
    constructor(name: String, contents: List<FileSystem>) : this(name, contents.sumOf(FileSystem::size), contents)
}

private fun parseToFileSystem(
    instructions: Input,
    name: String,
    currentContents: List<FileSystem> = listOf()
): Pair<Input, FileSystem> {
    if (instructions.isEmpty()) return instructions to Dir(name, currentContents)
    val (instruction, remainingInstruction) = uncons(instructions)
    return when {
        isCdDown(instruction) -> {
            val (newRemainingInstruction, fs) = parseToFileSystem(remainingInstruction, dirName(instruction))
            parseToFileSystem(newRemainingInstruction, name, currentContents + fs)
        }

        isCdUp(instruction) -> {
            return remainingInstruction to Dir(name, currentContents)
        }

        isFile(instruction) -> {
            parseToFileSystem(remainingInstruction, name, currentContents + File(instruction))
        }

        else -> parseToFileSystem(remainingInstruction, name, currentContents)
    }
}

private fun isCdDown(instruction: String): Boolean = !isCdUp(instruction) && instruction.startsWith("\$ cd")

private fun dirName(instruction: String): String = instruction.drop(5)

private fun isFile(instruction: String): Boolean = instruction.first().isDigit()

private fun isCdUp(instruction: String): Boolean = instruction.startsWith("\$ cd ..")

private fun FileSystem.toList(): List<FileSystem> = when (this) {
    is Dir -> listOf(this) + this.contents.flatMap(FileSystem::toList)
    is File -> listOf(this)
}