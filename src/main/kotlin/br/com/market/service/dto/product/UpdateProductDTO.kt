package br.com.market.service.dto.product

import jakarta.validation.constraints.NotEmpty

data class UpdateProductDTO(
    var id: Long,
    var idLocal: Long,
    @field:NotEmpty(message = "O atributo name não pode ser vazio")
    var name: String,
    var imageUrl: String = ""
)