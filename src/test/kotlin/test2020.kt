import kotlin.test.*
import java.io.File
import java.math.BigInteger

class Tests2020 {
    @Test
    fun day1() {
        fun parse(path: String) = File(path).readLines().map { it.toInt() }
        assertEquals(514579    , reportRepair(parse("inputs/2020/day1-test.txt")))
        assertEquals(445536    , reportRepair(parse("inputs/2020/day1.txt")))
        assertEquals(241861950 , reportRepair2(parse("inputs/2020/day1-test.txt")))
        //assertEquals(138688160 , reportRepair2(parse("inputs/2020/day1.txt")))
    }

    @Test
    fun day2() {
        fun parse(path: String) = File(path).readLines().map {
            val words = it.split(" ")
            val nums = words[0].split("-")
            PasswordPolicyAndPassword(
                lowerBound = nums[0].toInt(),
                upperBound = nums[1].toInt(),
                char = words[1][0],
                password = words[2]
            )
        }
        assertEquals(2   , passwordPhilosophy(parse("inputs/2020/day2-test.txt")))
        assertEquals(474 , passwordPhilosophy(parse("inputs/2020/day2.txt")))
        assertEquals(1   , passwordPhilosophy2(parse("inputs/2020/day2-test.txt")))
        assertEquals(745 , passwordPhilosophy2(parse("inputs/2020/day2.txt")))
    }

    @Test
    fun day3() {
        fun parse(path: String) = File(path).readLines().map { it.toCharArray().toList() }
        assertEquals(7                 , tobogganTrajectory(parse("inputs/2020/day3-test.txt")))
        assertEquals(292               , tobogganTrajectory(parse("inputs/2020/day3.txt")))
        assertEquals(336.toBigInteger()        , tobogganTrajectory2(parse("inputs/2020/day3-test.txt")))
        assertEquals(9354744432.toBigInteger() , tobogganTrajectory2(parse("inputs/2020/day3.txt")))
    }

    @Test
    fun day4() {
        fun parse(path: String) = File(path).readLines().splitBy { it.isEmpty() }.map { linesOfPassPort ->
            linesOfPassPort.joinToString(separator = " ").split(" ").map { feild ->
                val split = feild.split(":")
                Pair(split[0], split[1])
            }.toMap()
        }
        assertEquals(2   , passportProcessing(parse("inputs/2020/day4-test.txt")))
        assertEquals(182 , passportProcessing(parse("inputs/2020/day4.txt")))
        assertEquals(2   , passportProcessing2(parse("inputs/2020/day4-test.txt")))
        assertEquals(109 , passportProcessing2(parse("inputs/2020/day4.txt")))
    }

    @Test
    fun day5() {
        fun parse(path: String) = File(path).readLines()
        assertEquals(850, binaryBoarding(parse("inputs/2020/day5.txt")))
        assertEquals(599, binaryBoarding2(parse("inputs/2020/day5.txt")))
    }

    @Test
    fun day6() {
        fun parse(path: String) = File(path).readLines().splitBy { it.isEmpty() }
        assertEquals(11   , customCustoms(parse("inputs/2020/day6-test.txt")))
        assertEquals(7110 , customCustoms(parse("inputs/2020/day6.txt")))
        assertEquals(6    , customCustoms2(parse("inputs/2020/day6-test.txt")))
        assertEquals(3628 , customCustoms2(parse("inputs/2020/day6.txt")))
    }

    @Test
    fun day7() {
        fun parse(path: String): Map<String, Map<String, Int>> {
            val lines = File(path).readLines()
            val allowedColors = lines.map { it.split(" bags").first() }
            return lines.map { line ->
                val colors = Regex(allowedColors.joinToString(separator = "|")).findAll(line).map { it.value }
                val numbers = Regex("\\d").findAll(line).map { it.value.toInt() }
                Pair(colors.first(), colors.drop(1).zip(numbers).toMap())
            }.toMap()
        }
        assertEquals(4    , handyHaversacks(parse("inputs/2020/day7-test.txt")))
        assertEquals(121  , handyHaversacks(parse("inputs/2020/day7.txt")))
        assertEquals(32   , handyHaversacks2(parse("inputs/2020/day7-test.txt")))
        assertEquals(126  , handyHaversacks2(parse("inputs/2020/day7-test2.txt")))
        assertEquals(3805 , handyHaversacks2(parse("inputs/2020/day7.txt")))
    }

    @Test
    fun day8() {
        fun parse(path: String): List<Pair<String, Int>> = File(path).readLines().map {
            val split = it.split(" ")
            Pair(split[0], split[1].toInt())
        }
        assertEquals(5    , handleHalting(parse("inputs/2020/day8-test.txt")))
        assertEquals(1087 , handleHalting(parse("inputs/2020/day8.txt")))
        assertEquals(8    , handleHalting2(parse("inputs/2020/day8-test.txt")))
        assertEquals(780  , handleHalting2(parse("inputs/2020/day8.txt")))
    }

    @Test
    fun day9() {
        fun parse(path: String): List<BigInteger> = File(path).readLines().map { it.toBigInteger() }
        assertEquals(127.toBigInteger()        , encodingError(5, parse("inputs/2020/day9-test.txt")))
        assertEquals(1504371145.toBigInteger() , encodingError(25, parse("inputs/2020/day9.txt")))
        assertEquals(62.toBigInteger()         , encodingError2(5, parse("inputs/2020/day9-test.txt")))
        assertEquals(183278487.toBigInteger()  , encodingError2(25, parse("inputs/2020/day9.txt")))
    }
}