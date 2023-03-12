package br.com.market.service.dto.brand

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min

data class UpdateBrandDTO(
    var localBrandId: Long,
    var name: String,
    @field:Max(Int.MAX_VALUE.toLong())
    @field:Min(0)
    var count: Int = 0
)
