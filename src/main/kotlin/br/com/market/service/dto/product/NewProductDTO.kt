package br.com.market.service.dto.product

import java.util.*

data class NewProductDTO(
    var localProductId: UUID? = null,
    var name: String,
    var imageUrl: String = ""
)