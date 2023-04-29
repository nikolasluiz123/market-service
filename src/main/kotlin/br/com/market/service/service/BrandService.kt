package br.com.market.service.service

import br.com.market.service.dto.brand.BrandBodyDTO
import br.com.market.service.dto.brand.BrandDTO
import br.com.market.service.dto.brand.CategoryBrandDTO
import br.com.market.service.models.Brand
import br.com.market.service.models.CategoryBrand
import br.com.market.service.repository.brand.IBrandRepository
import br.com.market.service.repository.brand.ICategoryBrandRepository
import br.com.market.service.repository.category.ICategoryRepository
import org.springframework.stereotype.Service

@Service
class BrandService(
    private val brandRepository: IBrandRepository,
    private val categoryRepository: ICategoryRepository,
    private val categoryBrandRepository: ICategoryBrandRepository
    ) {
    
    fun save(brandBodyDTO: BrandBodyDTO) {
        with(brandBodyDTO.brand) {
            val brand = brandRepository.findBrandByLocalId(localId)?.copy(
                name = name,
                localId = localId,
                active = active
            ) ?: Brand(
                name = name,
                localId = localId,
                active = active
            )

            brandRepository.save(brand)

            with(brandBodyDTO.categoryBrand) {
                val categoryBrand = CategoryBrand(
                    localId = localId,
                    category = categoryRepository.findCategoryByLocalId(localCategoryId),
                    brand = brand,
                    active = active
                )

                categoryBrandRepository.save(categoryBrand)
            }
        }
    }

    fun toggleActive(categoryDTO: BrandDTO) {
        brandRepository.findBrandByLocalId(categoryDTO.localId)?.let {
            brandRepository.save(it.copy(active = !it.active))
        }
    }

    fun sync(brandBodyDTOs: List<BrandBodyDTO>) {
        brandBodyDTOs.forEach(::save)
    }

    fun findAllBrandDTOs(): List<BrandDTO> {
        return brandRepository.findAll().map {
            BrandDTO(
                localId = it.localId!!,
                name = it.name,
                companyId = it.company?.id,
                active = it.active
            )
        }
    }

    fun findAllCategoryBrandDTOs(): List<CategoryBrandDTO> {
        return categoryBrandRepository.findAll().map {
            CategoryBrandDTO(
                localId = it.localId!!,
                companyId = it.company?.id,
                active = it.active,
                localCategoryId = it.category?.localId!!,
                localBrandId = it.brand?.localId!!
            )
        }
    }
}