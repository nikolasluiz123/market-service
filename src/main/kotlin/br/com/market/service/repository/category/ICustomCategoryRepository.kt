package br.com.market.service.repository.category

import br.com.market.service.dto.CategoryDTO
import br.com.market.service.models.Category

interface ICustomCategoryRepository {

    fun findCategoryByLocalId(localId: String): Category?

    fun getListCategory(simpleFilter: String?, marketId: Long, limit: Int?, offset: Int?): List<CategoryDTO>

    fun toggleActive(categoryLocalId: String)
}