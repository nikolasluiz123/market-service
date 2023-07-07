package br.com.market.service.repository.brand

import br.com.market.service.dto.filter.CategoryBrandFiltersDTO
import br.com.market.service.models.CategoryBrand

interface ICustomCategoryBrandRepository {

    fun findCategoryBrandByLocalId(localId: String): CategoryBrand?
    fun findCategoryBrands(categoryBrandFiltersDTO: CategoryBrandFiltersDTO): List<CategoryBrand>
}
