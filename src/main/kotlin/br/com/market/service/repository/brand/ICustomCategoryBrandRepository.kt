package br.com.market.service.repository.brand

import br.com.market.service.models.CategoryBrand

interface ICustomCategoryBrandRepository {

    fun findCategoryBrandByLocalId(localId: String): CategoryBrand?
    fun findCategoryBrands(marketId: Long, limit: Int? = null, offset: Int? = null): List<CategoryBrand>
}
