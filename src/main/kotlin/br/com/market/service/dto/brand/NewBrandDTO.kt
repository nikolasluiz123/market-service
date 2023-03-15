package br.com.market.service.dto.brand

import jakarta.validation.constraints.NotEmpty
import java.util.*

data class NewBrandDTO(
    var localProductId: UUID? = null,
    var localBrandId: UUID? = null,
    @field:NotEmpty(message = "O atributo name não pode ser vazio")
    var name: String,
    var count: Int = 0
)
