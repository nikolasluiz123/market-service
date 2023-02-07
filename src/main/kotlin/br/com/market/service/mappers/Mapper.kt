package br.com.market.service.mappers

interface Mapper<T, U> {
    fun map(value: T): U
}
