package br.com.market.service.dto.category

import java.util.*

data class CategoryDTO(
    var localCategoryId: UUID,
    var name: String,
    var companyId: Long
)