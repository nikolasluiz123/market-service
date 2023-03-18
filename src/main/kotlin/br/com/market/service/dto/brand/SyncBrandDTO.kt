package br.com.market.service.dto.brand

import java.util.*

data class SyncBrandDTO(
    var localBrandId: UUID,
    var name: String,
)