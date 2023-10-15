package br.com.market.service.repository.category

import br.com.market.service.dto.CategoryReadDTO
import br.com.market.service.models.Category

interface ICustomCategoryRepository {

    fun findCategoryByLocalId(localId: String): Category?

    fun getListLovCategoryReadDTO(simpleFilter: String?, marketId: Long, limit: Int, offset: Int): List<CategoryReadDTO>
}