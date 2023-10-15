package br.com.market.service.service

import br.com.market.service.dto.CategoryDTO
import br.com.market.service.dto.CategoryReadDTO
import br.com.market.service.models.Category
import br.com.market.service.repository.category.ICategoryRepository
import br.com.market.service.repository.category.ICustomCategoryRepository
import br.com.market.service.repository.market.IMarketRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: ICategoryRepository,
    private val customCategoryRepository: ICustomCategoryRepository,
    private val marketRepository: IMarketRepository
) {

    fun save(dto: CategoryDTO) {
        val category = customCategoryRepository.findCategoryByLocalId(dto.localId)?.copy(
            name = dto.name,
            localId = dto.localId,
            active = dto.active
        ) ?: Category(
            name = dto.name,
            localId = dto.localId,
            active = dto.active,
            market = marketRepository.findById(dto.marketId!!).get()
        )

        categoryRepository.save(category)
    }

    fun toggleActive(categoryDTO: CategoryDTO) {
        customCategoryRepository.findCategoryByLocalId(categoryDTO.localId)?.let {
            categoryRepository.save(it.copy(active = !it.active))
        }
    }

    fun sync(categoriesDTOs: List<CategoryDTO>) {
        categoriesDTOs.forEach(::save)
    }

    fun getListLovCategoryReadDTO(simpleFilter: String?, marketId: Long, limit: Int, offset: Int): List<CategoryReadDTO> {
        return customCategoryRepository.getListLovCategoryReadDTO(simpleFilter, marketId, limit, offset)
    }
}