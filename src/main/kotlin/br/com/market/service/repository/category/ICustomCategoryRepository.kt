package br.com.market.service.repository.category

import br.com.market.service.dto.filter.CategoryFiltersDTO
import br.com.market.service.models.Category

interface ICustomCategoryRepository {

    fun findCategoryByLocalId(localId: String): Category?

    fun findCategories(categoryFiltersDTO: CategoryFiltersDTO): List<Category>
}