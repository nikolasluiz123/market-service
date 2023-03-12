package br.com.market.service.dto.brand

import jakarta.validation.constraints.NotEmpty

data class NewBrandDTO(
    var localProductId: Long? = null,
    var localBrandId: Long? = null,
    @field:NotEmpty(message = "O atributo name não pode ser vazio")
    var name: String,
    var count: Int = 0
)
