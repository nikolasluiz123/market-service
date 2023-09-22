package br.com.market.service.util

object CPFUtil {

    fun isValid(cpf: String): Boolean {
        val cpfNumbers = cpf.replace("[^0-9]".toRegex(), "")

        if (cpfNumbers.length != 11) {
            return false
        }

        val digit1 = calculateDigit(cpfNumbers.substring(0, 9))
        val digit2 = calculateDigit(cpfNumbers.substring(0, 9) + digit1)

        return cpfNumbers.substring(9, 10).toInt() == digit1 && cpfNumbers.substring(10, 11).toInt() == digit2
    }

    private fun calculateDigit(base: String): Int {
        var sum = 0
        var weight = base.length + 1

        for (char in base) {
            sum += char.toString().toInt() * weight
            weight--
        }

        val result = sum % 11
        return if (result < 2) 0 else 11 - result
    }
}