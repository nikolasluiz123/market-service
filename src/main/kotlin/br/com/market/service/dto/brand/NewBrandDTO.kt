package br.com.market.service.dto.brand

import jakarta.validation.constraints.NotEmpty

data class NewBrandDTO(
    var id: Long? = null,
    @field:NotEmpty(message = "O atributo name não pode ser vazio")
    var name: String
)
