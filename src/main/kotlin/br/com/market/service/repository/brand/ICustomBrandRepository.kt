package br.com.market.service.repository.brand

import br.com.market.service.models.Brand

interface ICustomBrandRepository {

    fun findBrandByLocalId(localId: String): Brand?
    fun findBrands(marketId: Long): List<Brand>
}