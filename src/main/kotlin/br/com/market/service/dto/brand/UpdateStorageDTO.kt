package br.com.market.service.dto.brand

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class UpdateStorageDTO(
    var productId: Long,
    var brandId: Long,
    @field:Max(Int.MAX_VALUE.toLong())
    @field:Min(0)
    var count: Int
)