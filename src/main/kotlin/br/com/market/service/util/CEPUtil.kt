package br.com.market.service.util

object CEPUtil {

    fun isValid(cep: String): Boolean {
        val cepNumbers = cep.replace("[^0-9]".toRegex(), "")
        return cepNumbers.length == 8
    }

}