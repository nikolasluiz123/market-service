package br.com.market.service.dto.product

import java.util.*

data class UpdateProductDTO(
    var id: Long,
    var localProductId: UUID,
    var name: String,
    var imageUrl: String = ""
)