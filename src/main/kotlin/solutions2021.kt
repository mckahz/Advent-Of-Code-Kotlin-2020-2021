import com.sun.nio.sctp.InvalidStreamException
import java.io.File
import kotlin.math.*

fun sonarSweep(report: List<Int>): Int =
    report.zipWithNext().count { (first, next) -> first - next < 0 }

fun sonarSweep2(report: List<Int>): Int =
    report
        .windowed(3, 1)
        .map { it.sum() }
        .zipWithNext()
        .count { (first, next) -> first - next < 0 }


fun dive(course: List<Pair<String, Int>>): Int {
    var h = 0
    var depth = 0
    for (step in course) {
        val X = step.second
        when (step.first) {
            "down" -> depth += X
            "up" -> depth -= X
            "forward" -> h += X
        }
    }
    return depth * h
}

fun dive2(course: List<Pair<String, Int>>): Int {
    var h = 0
    var depth = 0
    var aim = 0
    for (step in course) {
        val X = step.second
        when (step.first) {
            "down" -> aim += X
            "up" -> aim -= X
            "forward" -> {
                h += X
                depth += aim * X
            }
        }
    }
    return depth * h
}

fun binaryDiagnostic(report: List<List<Int>>): Int {
    val columns = List(report.first().size) { i -> List(report.size) { j -> report [j][i] } }

    val (gammaRate, epsilonRate) = columns.map { column: List<Int> ->
        val (zeroes, ones) = column.partition { it == 0 }
        val (gammaBit, epsilonBit) = if (zeroes.size > ones.size) Pair(0, 1) else Pair(1, 0)
        Pair(gammaBit, epsilonBit)
    }.unzip()

    fun List<Int>.toDecimal(): Int = this.reversed().mapIndexed { i, bit -> 2.0.pow(i).roundToInt() * bit }.sum()
    return gammaRate.toDecimal() * epsilonRate.toDecimal()
}

fun binaryDiagnostic2(report: List<List<Int>>): Int {
    val columns = List(report.first().size) { i -> List(report.size) { j -> report [j][i] } }

    val oxygenRatings = report.toMutableList()
    for (i in columns.indices) {
        val column = oxygenRatings.map { it[i] }
        val (zeroes, ones) = column.partition { it == 0 }

        val oxygenBit = if (zeroes.size > ones.size) 0 else 1
        if (oxygenRatings.size > 1)
            oxygenRatings.removeIf { row -> row[i] != oxygenBit }
    }

    val co2Ratings = report.toMutableList()
    for (i in columns.indices) {
        val column = co2Ratings.map { it[i] }
        val (zeroes, ones) = column.partition { it == 0 }

        val co2Bit = if (zeroes.size <= ones.size) 0 else 1
        if (co2Ratings.size > 1)
            co2Ratings.removeIf { row -> row[i] != co2Bit }
    }


    fun List<Int>.toDecimal(): Int = this.reversed().mapIndexed { i, bit -> 2.0.pow(i).roundToInt() * bit }.sumOf { it }
    return oxygenRatings.first().toDecimal() * co2Ratings.first().toDecimal()
}

fun giantSquid(args: Pair<List<Int>, List<List<List<Int>>>>): Int {
    val (drawList, boards) = args

    fun List<Int>.isFinished(draws: List<Int>): Boolean =
        (this - draws.toSet()).isEmpty()

    fun List<List<Int>>.hasWon(draws: List<Int>): Boolean {
        val rows  = this
        val cols  = (0 until 5).map { i -> this.map { it[i] }}
        return (rows + cols).any { it.isFinished(draws) }
    }

    val winningDraws = drawList.indices.asSequence().map { i -> drawList.subList(0, i) }
        .first { draws -> boards.any { board -> board.hasWon(draws) } }

    val winningBoard = boards.find { it.hasWon(winningDraws) }!!

    return (winningBoard.flatten() - winningDraws.toSet()).sum() * winningDraws.last()
}

fun giantSquid2(args: Pair<List<Int>, List<List<List<Int>>>>): Int {
    val (drawList, boards) = args

    fun List<Int>.isFinished(draws: List<Int>): Boolean =
        (this - draws.toSet()).isEmpty()

    fun List<List<Int>>.hasWon(draws: List<Int>): Boolean {
        val rows  = this
        val cols  = (0 until 5).map { i -> this.map { it[i] }}
        return (rows + cols).any { it.isFinished(draws) }
    }

    val lastWinningDraws = drawList.indices.asSequence().map { i -> drawList.subList(0, i) }
        .first { draws -> boards.all { board -> board.hasWon(draws) } }

    val lastWinningBoard = boards.find { !it.hasWon(lastWinningDraws.slice(0 until lastWinningDraws.size - 1)) }!!

    return (lastWinningBoard.flatten() - lastWinningDraws.toSet()).sum() * lastWinningDraws.last()
}