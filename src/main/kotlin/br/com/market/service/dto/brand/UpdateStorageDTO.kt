package br.com.market.service.dto.brand

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.util.*

data class UpdateStorageDTO(
    var productId: UUID,
    var brandId: UUID,
    @field:Max(Int.MAX_VALUE.toLong())
    @field:Min(0)
    var count: Int
)