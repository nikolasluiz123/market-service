package br.com.market.service.dto.brand

data class UpdateBrandDTO(
    var id: Long,
    var name: String,
    var sumCount: Int = 0
)
