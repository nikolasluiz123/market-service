package br.com.market.service.dto

data class NewProductDTO(
    var id: Long? = null,
    var name: String,
    var brands: List<NewBrandDTO>
)