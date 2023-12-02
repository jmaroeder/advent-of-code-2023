fun main() {
  val SPELLED_DIGIT = Regex("(one|two|three|four|five|six|seven|eight|nine|zero)")
  val SPELLED_DIGIT_LOOKAHEAD = Regex("(?=(one|two|three|four|five|six|seven|eight|nine|zero))")
  val DIGIT_MAP = mapOf(
      "one" to 1,
      "two" to 2,
      "three" to 3,
      "four" to 4,
      "five" to 5,
      "six" to 6,
      "seven" to 7,
      "eight" to 8,
      "nine" to 9,
      "zero" to 0,
  )

  fun findFirstDigit(input: String): Int {
    return input.first { c -> c.isDigit() }.digitToInt()
  }

  fun findLastDigit(input: String): Int {
    return input.last { c -> c.isDigit() }.digitToInt()
  }

  fun findCalibrationValue(input: String): Int {
    return (findFirstDigit(input).toString() + findLastDigit(input).toString()).toInt()
  }

  fun part1(input: List<String>): Int {
    return input.fold(0) { sum: Int, element: String ->
      sum + findCalibrationValue(element)
    }
  }

  fun findFirstSpelledOrNumericDigit(input: String): Int {
    val spelledDigitMatch = SPELLED_DIGIT_LOOKAHEAD.find(input)
    val numericDigitIndex = input.indexOfFirst { c -> c.isDigit() }
    if (numericDigitIndex != -1 && (spelledDigitMatch == null || numericDigitIndex < spelledDigitMatch.range.first)) {
      return input[numericDigitIndex].digitToInt()
    }
    return DIGIT_MAP.getOrElse(SPELLED_DIGIT.matchAt(input, spelledDigitMatch!!.range.first)!!.value) {
      throw IllegalStateException()
    }
  }

  fun findLastSpelledOrNumericDigit(input: String): Int {
    val spelledDigitMatches = SPELLED_DIGIT_LOOKAHEAD.findAll(input)
    val numericDigitIndex = input.indexOfLast { c -> c.isDigit() }
    if (numericDigitIndex != -1 && (spelledDigitMatches.count() == 0 || numericDigitIndex > spelledDigitMatches.last().range.first)) {
      return input[numericDigitIndex].digitToInt()
    }
    return DIGIT_MAP.getOrElse(SPELLED_DIGIT.matchAt(input, spelledDigitMatches.last().range.first)!!.value) {
      throw IllegalStateException()
    }
  }

  fun findPart2CalibrationValue(input: String): Int {
    val firstDigit = findFirstSpelledOrNumericDigit(input)
    val lastDigit = findLastSpelledOrNumericDigit(input)
    return ("$firstDigit$lastDigit").toInt()
  }

  fun part2(input: List<String>): Int {
    return input.fold(0) { sum: Int, element: String ->
      sum + findPart2CalibrationValue(element)
    }

  }

  // test if implementation meets criteria from the description, like:
  val testInput = readInput("Day01_test")
  check(part1(testInput) == 142)
  val testInput2 = readInput("Day01_test2")
  check(part2(testInput2) == 281)

  val input = readInput("Day01")
  part1(input).println()
  part2(input).println()
}
