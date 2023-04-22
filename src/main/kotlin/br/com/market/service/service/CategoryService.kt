package br.com.market.service.service

import br.com.market.service.dto.category.CategoryDTO
import br.com.market.service.models.Category
import br.com.market.service.repository.category.ICategoryRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(private val repository: ICategoryRepository) {

    fun save(dto: CategoryDTO) {
        val category = repository.findCategoryByLocalId(dto.localCategoryId)?.copy(
            name = dto.name,
            localId = dto.localCategoryId,
            active = dto.active
        ) ?: Category(
            name = dto.name,
            localId = dto.localCategoryId,
            active = dto.active
        )

        repository.save(category)
    }

    fun toggleActive(categoryDTO: CategoryDTO) {
        repository.findCategoryByLocalId(categoryDTO.localCategoryId)?.let {
            repository.save(it.copy(active = !it.active))
        }
    }

    fun sync(categoriesDTOs: List<CategoryDTO>) {
        categoriesDTOs.forEach(::save)
    }

    fun findAll(): List<CategoryDTO> {
        return repository.findAll().map {
            CategoryDTO(
                localCategoryId = it.localId!!,
                name = it.name,
                companyId = it.company?.id,
                active = it.active
            )
        }
    }
}