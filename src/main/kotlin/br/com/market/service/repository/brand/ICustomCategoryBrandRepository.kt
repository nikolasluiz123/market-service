package br.com.market.service.repository.brand

import br.com.market.service.models.CategoryBrand
import java.util.*

interface ICustomCategoryBrandRepository {

    fun findCategoryBrandByLocalId(localId: UUID): CategoryBrand?
}
