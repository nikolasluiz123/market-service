package br.com.market.service.dto.product

import jakarta.validation.constraints.NotEmpty

data class NewProductDTO(
    var id: Long? = null,
    var idLocal: Long? = null,
    @field:NotEmpty(message = "O atributo name não pode ser vazio")
    var name: String,
    @field:NotEmpty(message = "O atributo imageUrl não pode ser vazio")
    var imageUrl: String = ""
)