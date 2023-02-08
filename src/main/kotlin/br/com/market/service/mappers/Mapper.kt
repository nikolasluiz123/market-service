package br.com.market.service.mappers

interface Mapper<T, U> {
    fun toDTO(value: T): U

    fun toModel(value: U): T
}
