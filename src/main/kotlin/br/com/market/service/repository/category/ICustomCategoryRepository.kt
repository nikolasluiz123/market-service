package br.com.market.service.repository.category

import br.com.market.service.models.Category
import java.util.*

interface ICustomCategoryRepository {

    fun findCategoryByLocalId(localId: String): Category?

}