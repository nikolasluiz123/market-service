package br.com.market.service.dto.product

import br.com.market.service.dto.brand.UpdateBrandDTO

data class UpdateProductDTO(
    var id: Long,
    var name: String,
    var brands: List<UpdateBrandDTO>
)