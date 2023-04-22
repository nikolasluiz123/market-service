package br.com.market.service.service

import br.com.market.service.dto.category.CategoryDTO
import br.com.market.service.models.Category
import br.com.market.service.repository.category.ICategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val repository: ICategoryRepository) {

    fun save(dto: CategoryDTO) {
        val category = repository.findCategoryByLocalId(dto.localCategoryId)?.copy(name = dto.name)
            ?: Category(name = dto.name, localId = dto.localCategoryId)

        repository.save(category)
    }

    fun toggleActive(categoryDTO: CategoryDTO) {
        repository.findCategoryByLocalId(categoryDTO.localCategoryId)?.let {
            repository.save(it.copy(active = !it.active))
        }
    }
}