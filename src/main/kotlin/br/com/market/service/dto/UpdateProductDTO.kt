package br.com.market.service.dto

data class UpdateProductDTO(
    var id: Long,
    var name: String,
    var brands: List<UpdateBrandDTO>
)