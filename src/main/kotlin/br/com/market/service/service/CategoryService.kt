package br.com.market.service.service

import br.com.market.service.dto.category.CategoryDTO
import br.com.market.service.models.Category
import br.com.market.service.repository.category.CategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    fun save(categoryDTO: CategoryDTO) {
        val company = categoryDTO.companyId
        val category = Category()
    }
}