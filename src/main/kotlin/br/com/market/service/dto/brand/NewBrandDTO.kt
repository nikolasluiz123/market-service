package br.com.market.service.dto.brand

import java.util.*

data class NewBrandDTO(
    var localProductBrandId: UUID,
    var localProductId: UUID,
    var localBrandId: UUID,
    var name: String,
    var count: Int = 0
)
