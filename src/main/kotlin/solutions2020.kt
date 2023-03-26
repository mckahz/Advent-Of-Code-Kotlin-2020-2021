import java.io.File
import java.math.BigInteger
import kotlin.math.*

fun <T> cartesianProduct(vararg lists: Collection<T>): List<Collection<T>> =
    lists.fold(listOf(listOf())) { acc, list -> list.flatMap { e -> acc.map { pe -> pe + e } } }

fun <T> List<T>.splitBy(p: (T) -> Boolean): List<List<T>> {
    val grouped = mutableListOf<List<T>>()
    var group = mutableListOf<T>()
    for (i in 0 .. this.size) {
        if (i < this.size && !p(this[i])) {
            group.add(this[i])
            continue
        }
        val copy = mutableListOf<T>()
        copy.addAll(group)
        grouped.add(copy.toList())
        group = mutableListOf()
    }
    return grouped.toList()
}


fun reportRepair(entries: List<Int>): Int =
    cartesianProduct(entries, entries)
        .find { pairs -> pairs.sum() == 2020 }!!
        .reduce(Int::times)

fun reportRepair2(entries: List<Int>): Int =
    cartesianProduct(entries, entries, entries)
        .find { triplets -> triplets.sum() == 2020 }!!
        .reduce(Int::times)

data class PasswordPolicyAndPassword(val lowerBound: Int, val upperBound: Int, val char: Char, val password: String)

fun passwordPhilosophy(passwordPolicyAndPasswords: List<PasswordPolicyAndPassword>) =
    passwordPolicyAndPasswords.count { (lowerBound, upperBound, char, password) ->
        password.count { it == char } in lowerBound..upperBound
    }

fun passwordPhilosophy2(passwordPolicyAndPasswords: List<PasswordPolicyAndPassword>) =
    passwordPolicyAndPasswords.count { (p1, p2, char, password) ->
        listOf(p1, p2).map { password [it - 1] }.count { it == char } == 1
    }

fun tobogganTrajectory(trees: List<List<Char>>) =
    (1 until trees.size).count {
        trees[it][it * 3 % trees.first().size] == '#'
    }

fun tobogganTrajectory2(trees: List<List<Char>>) =
    listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2)).map { (right, down) ->
        (1 until trees.size / down).count { trees[it * down][it * right % trees.first().size] == '#' }
    }.map { it.toBigInteger() }
        .reduce(BigInteger::times)

fun passportProcessing(passports: List<Map<String, String>>): Int =
    passports.count { passport: Map<String, String> ->
        passport.size == 8 || (passport.size == 7 && !passport.any { it.key == "cid" })
    }



fun passportProcessing2(passports: List<Map<String, String>>): Int =
    passports.count { passport: Map<String, String> ->
        val hgt by lazy { passport["hgt"]!!.takeWhile { it.isDigit() }.toInt() }
        val hgtUnits by lazy { passport["hgt"]!!.dropWhile { it.isDigit() } }

        (passport.size == 8 || (passport.size == 7 && !passport.any { it.key == "cid"}))
        && (passport["byr"]!!.toInt() in 1920..2002)
        && (passport["iyr"]!!.toInt() in 2010..2020)
        && (passport["eyr"]!!.toInt() in 2020..2030)
        && when (hgtUnits) {
            "cm" -> hgt in 150..193
            "in" -> hgt in 59..76
            else -> false
        }
        && Regex("#[\\d[a-f][A-F]]{6}").matches(passport["hcl"]!!)
        && Regex("amb|blu|brn|gry|grn|hzl|oth").matches(passport["ecl"]!!)
        && Regex("\\d{9}").matches(passport["pid"]!!)
    }

fun List<Int>.binaryPartition(max: Int): Int =
    this.fold(0..max) { range, bit ->
        if (bit == 0)
            range.first..(range.first+range.last)/2
        else
            (range.first+range.last)/2 + 1..range.last
    }.first

fun binaryBoarding(seats: List<String>): Int =
    seats.map { seatCode ->
        val row = seatCode.take(7).map { when (it) { 'F' -> 0; else -> 1 } }.binaryPartition(127)
        val col = seatCode.drop(7).map { when (it) { 'L' -> 0; else -> 1 } }.binaryPartition(7)
        /* seat id */ row * 8 + col
    }.max()

fun binaryBoarding2(seats: List<String>): Int {
    val seatIds = seats.map { seatCode ->
        val row = seatCode.take(7).map { when (it) { 'F' -> 0; else -> 1 } }.binaryPartition(127)
        val col = seatCode.drop(7).map { when (it) { 'L' -> 0; else -> 1 } }.binaryPartition(7)
        row * 8 + col
    }
    return (0..seatIds.max()).find { seatId ->
        seatIds.contains(seatId - 1) && !seatIds.contains(seatId) && seatIds.contains(seatId + 1)
    }!!
}

fun customCustoms(groups: List<List<String>>): Int =
    groups.map { group -> group.joinToString(separator = "").toCharArray().distinct().size }.sum()

fun customCustoms2(groups: List<List<String>>): Int =
    groups.map { group -> group.first().count { answer -> group.drop(1).all { otherPeople -> otherPeople.contains (answer) }} }.sum()

val myColor = "shiny gold"

fun handyHaversacks(rules: Map<String, Map<String, Int>>): Int {
    fun Map<String, Int>.canContainMyColor(): Boolean =
        if (this.keys.contains(myColor)) true
        else this.keys.any { rules[it]!!.canContainMyColor() }

    return rules.keys.count { rules[it]!!.canContainMyColor() }
}

fun handyHaversacks2(rules: Map<String, Map<String, Int>>): Int {
    fun String.containsNBags(): Int {
        val children = rules[this]!!
        return children.map {
            val color = it.key
            val count = it.value
            (color.containsNBags() + 1) * count
        }.sum()
    }
    return myColor.containsNBags()
}

fun handleHalting(instructions: List<Pair<String, Int>>): Int {
    var acc = 0
    var line = 0
    val readLines = mutableListOf<Int>()
    while (true) {
        if (line in readLines || line == instructions.size) break
        readLines.add(line)
        val (opCode, n) = instructions[line]
        when (opCode) {
            "acc" -> {
                acc += n
                line += 1
            }
            "jmp" -> line += n
            else -> line += 1
        }
    }
    return acc
}

fun handleHalting2(instructions: List<Pair<String, Int>>): Int {
    fun List<Pair<String, Int>>.halts(): Boolean {
        if (instructions[instructions.size - 2].first == "nop") {
            return true
        }
        var acc = 0
        var line = 0
        val readLines = mutableListOf<Int>()
        while (true) {
            if (line == this.size) return true
            if (line in readLines) return false
            readLines.add(line)
            val (opCode, n) = this[line]
            when (opCode) {
                "acc" -> {
                    acc += n
                    line += 1
                }
                "jmp" -> line += n
                else -> line += 1
            }
        }
    }

    fun List<Pair<String, Int>>.replace(n: Int): List<Pair<String, Int>> {
        val list = this.toMutableList()
        list[n] = Pair (
            when (list[n].first) {
                "jmp" -> "nop"
                "nop" -> "jmp"
                else -> list[n].first
            }, list[n].second
        )
        return list.toList()
    }

    return handleHalting(instructions.indices.map { i -> instructions.replace(i) }.first { it.halts() })
}

fun encodingError(preambleLength: Int, nums: List<BigInteger>): BigInteger {
    fun isValid(i: Int): Boolean {
        if (i < preambleLength) return true
        val prevNums = nums.slice(i - preambleLength until i).toSet()
        val compliments = prevNums.map { nums[i] - it }.toSet()
        return prevNums.intersect(compliments).isNotEmpty()
    }

    return nums.filterIndexed { i, _ -> !isValid(i) }.first()
}

fun encodingError2(preambleLength: Int, nums: List<BigInteger>): BigInteger {
    val firstInvalid = encodingError(preambleLength, nums)
    val sumRange : IntRange = nums.indices.asSequence().map { lower ->
        (lower until nums.size).asSequence().map { upper ->
            lower..upper
        }.firstOrNull { nums.slice(it).reduce(BigInteger::plus) == firstInvalid }
    }.first { it != null }!!
    val sumNums = nums.slice(sumRange)
    return sumNums.min() + sumNums.max()
}

