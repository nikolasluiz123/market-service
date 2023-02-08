package br.com.market.service.extensions

import br.com.market.service.query.Parameter
import jakarta.persistence.TypedQuery

fun <T> TypedQuery<T>.setParameters(params: List<Parameter>) {
    params.forEach { param -> setParameter(param.name, param.value) }
}