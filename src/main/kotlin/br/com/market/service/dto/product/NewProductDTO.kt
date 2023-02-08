package br.com.market.service.dto.product

import br.com.market.service.dto.brand.NewBrandDTO

data class NewProductDTO(
    var id: Long? = null,
    var name: String,
    var brands: List<NewBrandDTO>
)