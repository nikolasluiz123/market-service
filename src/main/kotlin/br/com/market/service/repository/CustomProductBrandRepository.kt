package br.com.market.service.repository

import br.com.market.service.models.ProductBrand

interface CustomProductBrandRepository {

    fun findByProductId(id: Long): List<ProductBrand>
}