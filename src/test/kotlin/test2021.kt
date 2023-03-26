import com.sun.nio.sctp.InvalidStreamException
import kotlin.test.*
import java.io.File

class Tests2021 {
    @Test
    fun day1() {
        fun parse(path: String) = File(path).readLines().map { it.toInt() }
        assertEquals(7, sonarSweep(parse("inputs/2021/day1-test.txt")))
        assertEquals(1374, sonarSweep(parse("inputs/2021/day1.txt")))
        assertEquals(5, sonarSweep2(parse("inputs/2021/day1-test.txt")))
        assertEquals(1418, sonarSweep2(parse("inputs/2021/day1.txt")))
    }

    @Test
    fun day2() {
        fun parse(path: String): List<Pair<String, Int>> =
            File(path).readLines().map {
                val split = it.split(" ")
                Pair(split[0], split[1].toInt())
            }
        assertEquals(150        , dive(parse("inputs/2021/day2-test.txt")))
        assertEquals(2117664    , dive(parse("inputs/2021/day2.txt")))
        assertEquals(900        , dive2(parse("inputs/2021/day2-test.txt")))
        assertEquals(2073416724 , dive2(parse("inputs/2021/day2.txt")))
    }

    @Test
    fun day3() {
        fun parse(path: String): List<List<Int>> = File(path).readLines().map { it.map { it.digitToInt() } }
        assertEquals(198     , binaryDiagnostic(parse("inputs/2021/day3-test.txt")))
        assertEquals(3959450 , binaryDiagnostic(parse("inputs/2021/day3.txt")))
        assertEquals(230     , binaryDiagnostic2(parse("inputs/2021/day3-test.txt")))
        assertEquals(7440311 , binaryDiagnostic2(parse("inputs/2021/day3.txt")))
    }

    @Test
    fun day4() {
        fun parse(path: String): Pair<List<Int>, List<List<List<Int>>>> {
            val lines = File(path).readLines()
            val draws = lines.first().split(",").map { it.toInt() }
            val boards = lines.slice(2 until lines.size)
                .windowed(5, 6)
                .map { board ->
                    board.map { row ->
                        row.split(" ").map { it.trim() }.filter { it != "" }.map { it.toInt() }
                    }
                }
            return Pair(draws, boards)
        }
        assertEquals(4512  , giantSquid(parse("inputs/2021/day4-test.txt")))
        assertEquals(11536 , giantSquid(parse("inputs/2021/day4.txt")))
        assertEquals(1924  , giantSquid2(parse("inputs/2021/day4-test.txt")))
        assertEquals(1284  , giantSquid2(parse("inputs/2021/day4.txt")))
    }
}