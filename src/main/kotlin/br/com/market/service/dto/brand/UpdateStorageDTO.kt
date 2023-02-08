package br.com.market.service.dto.brand

data class UpdateStorageDTO(
    var productId: Long,
    var brandId: Long,
    var count: Int
)