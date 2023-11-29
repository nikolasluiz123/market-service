package br.com.market.service.service

import br.com.market.service.dto.BrandAndReferencesDTO
import br.com.market.service.dto.CategoryBrandDTO
import br.com.market.service.models.Brand
import br.com.market.service.models.CategoryBrand
import br.com.market.service.repository.brand.IBrandRepository
import br.com.market.service.repository.brand.ICategoryBrandRepository
import br.com.market.service.repository.brand.ICustomBrandRepository
import br.com.market.service.repository.brand.ICustomCategoryBrandRepository
import br.com.market.service.repository.category.ICustomCategoryRepository
import br.com.market.service.repository.market.IMarketRepository
import org.springframework.stereotype.Service

@Service
class BrandService(
    private val brandRepository: IBrandRepository,
    private val customBrandRepository: ICustomBrandRepository,
    private val customCategoryRepository: ICustomCategoryRepository,
    private val categoryBrandRepository: ICategoryBrandRepository,
    private val customCategoryBrandRepository: ICustomCategoryBrandRepository,
    private val marketRepository: IMarketRepository
    ) {
    
    fun save(brandBodyDTO: BrandAndReferencesDTO) {
        with(brandBodyDTO.brand) {
            val brand = customBrandRepository.findBrandByLocalId(localId)?.copy(
                name = name,
                localId = localId,
                active = active
            ) ?: Brand(
                name = name,
                localId = localId,
                active = active,
                market = marketRepository.findById(marketId!!).get()
            )

            brandRepository.save(brand)

            with(brandBodyDTO.categoryBrand) {
                val categoryBrand = customCategoryBrandRepository.findCategoryBrandByLocalId(localId) ?: CategoryBrand(
                    localId = localId,
                    category = customCategoryRepository.findCategoryByLocalId(localCategoryId),
                    brand = brand,
                    active = active,
                    market = marketRepository.findById(marketId!!).get()
                )

                categoryBrandRepository.save(categoryBrand)
            }
        }
    }

    fun toggleActive(categoryBrandDTO: CategoryBrandDTO) {
        customCategoryBrandRepository.findCategoryBrandByLocalId(categoryBrandDTO.localId)?.let {
            categoryBrandRepository.save(it.copy(active = !it.active))
        }
    }

    fun getListBrand(simpleFilter: String?, categoryLocalId: String?, marketId: Long, limit: Int, offset: Int): List<BrandAndReferencesDTO> {
        return customBrandRepository.getListBrand(simpleFilter, categoryLocalId, marketId, limit, offset)
    }
}