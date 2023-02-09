package br.com.market.service.dto.product

import br.com.market.service.dto.brand.UpdateBrandDTO
import jakarta.validation.constraints.NotEmpty

data class UpdateProductDTO(
    var id: Long,
    @field:NotEmpty(message = "O atributo name não pode ser vazio")
    var name: String,
    @field:NotEmpty(message = "O atributo brands não pode ser vazio")
    var brands: List<UpdateBrandDTO>
)