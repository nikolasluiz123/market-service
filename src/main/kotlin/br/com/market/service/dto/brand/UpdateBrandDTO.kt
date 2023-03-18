package br.com.market.service.dto.brand

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import java.util.*

data class UpdateBrandDTO(
    var localBrandId: UUID,
    var name: String,
    var count: Int = 0
)
