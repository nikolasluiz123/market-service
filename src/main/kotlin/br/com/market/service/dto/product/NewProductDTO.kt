package br.com.market.service.dto.product

import br.com.market.service.dto.brand.NewBrandDTO
import jakarta.validation.constraints.NotEmpty

data class NewProductDTO(
    var id: Long? = null,
    @field:NotEmpty(message = "O atributo name não pode ser vazio")
    var name: String,
    var imageUrl: String = "",
    @field:NotEmpty(message = "O atributo brands não pode ser vazio")
    var brands: List<NewBrandDTO>
)