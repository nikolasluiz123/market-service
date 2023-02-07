package br.com.market.service.dto

data class ProductDTO(
    var id: Long? = null,
    var name: String,
    var brands: List<String>
)