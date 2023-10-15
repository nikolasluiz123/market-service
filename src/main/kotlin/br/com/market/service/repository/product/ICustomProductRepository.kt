package br.com.market.service.repository.product

import br.com.market.service.dto.ProductClientDTO
import br.com.market.service.models.Product

interface ICustomProductRepository {

    fun findProductByLocalId(localId: String): Product?
    fun findAll(marketId: Long, limit: Int? = null, offset: Int? = null): List<Product>

    fun findProducts(simpleFilter: String?, limit: Int, offset: Int): List<ProductClientDTO>

}