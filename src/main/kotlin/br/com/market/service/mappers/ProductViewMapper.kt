package br.com.market.service.mappers

import br.com.market.service.models.Product
import br.com.market.service.dto.product.ProductView

object ProductViewMapper : Mapper<Product, ProductView> {

    override fun toDTO(value: Product) = ProductView(id = value.id, name = value.name)

    override fun toModel(value: ProductView) = Product(id = value.id, name = value.name)

}