package br.com.market.service.mappers

import br.com.market.service.models.Product
import br.com.market.service.view.ProductView

class ProductViewMapper : Mapper<Product, ProductView> {

    override fun map(value: Product): ProductView {
        return ProductView(id = value.id, name = value.name)
    }

}