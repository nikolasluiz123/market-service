package br.com.market.service.repository.category

import br.com.market.service.models.Category

interface ICustomCategoryRepository {

    fun findCategoryByLocalId(localId: String): Category?

    fun findCategories(marketId: Long): List<Category>
}