package br.com.market.service.dto.brand

import java.util.*

data class SyncProductBrandDTO(
    var localId: UUID,
    var localProductId: UUID,
    var localBrandId: UUID,
    var count: Int
)