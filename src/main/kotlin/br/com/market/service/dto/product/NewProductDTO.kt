package br.com.market.service.dto.product

import java.util.*

data class NewProductDTO(
    var localProductId: UUID,
    var name: String,
    var imageUrl: String = ""
)