package br.com.market.service.dto.category

import java.util.*

data class CategoryDTO(
    var localCategoryId: UUID,
    var name: String? = null,
    var companyId: Long? = null,
    var active: Boolean = true
)