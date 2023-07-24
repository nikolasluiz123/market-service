package br.com.market.service.repository.product

import br.com.market.service.models.Product

interface ICustomProductRepository {

    fun findProductByLocalId(localId: String): Product?
    fun findAll(marketId: Long): List<Product>
}