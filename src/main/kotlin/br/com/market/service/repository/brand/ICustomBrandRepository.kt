package br.com.market.service.repository.brand

import br.com.market.service.dto.BrandAndReferencesDTO
import br.com.market.service.models.Brand

interface ICustomBrandRepository {

    fun findBrandByLocalId(localId: String): Brand?
    fun findBrands(marketId: Long, limit: Int? = null, offset: Int? = null): List<Brand>

    fun getListBrand(simpleFilter: String?, categoryLocalId: String?, marketId: Long, limit: Int, offset: Int): List<BrandAndReferencesDTO>

    fun findBrandAndReferenceBy(categoryLocalId: String, brandLocalId: String): BrandAndReferencesDTO

    fun toggleActive(categoryId: String, brandId: String)
}