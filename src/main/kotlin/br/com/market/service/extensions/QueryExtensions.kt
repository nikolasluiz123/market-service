package br.com.market.service.extensions

import br.com.market.service.query.Parameter
import jakarta.persistence.Query

fun Query.setParameters(params: List<Parameter>) {
    params.forEach { param -> setParameter(param.name, param.value) }
}

@Suppress("UNCHECKED_CAST")
fun <T> Query.getResultList(resultType: Class<T>): List<T> {
    return resultList as List<T>
}