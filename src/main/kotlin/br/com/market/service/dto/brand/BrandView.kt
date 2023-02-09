package br.com.market.service.dto.brand

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty

data class BrandView(
    var id: Long,
    @field:NotEmpty(message = "O atributo name não pode ser vazio")
    var name: String,
    @field:Max(Int.MAX_VALUE.toLong())
    @field:Min(0)
    var count: Int
)