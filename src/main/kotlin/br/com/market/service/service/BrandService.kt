package br.com.market.service.service

import br.com.market.service.dto.brand.BrandBodyDTO
import br.com.market.service.dto.brand.BrandDTO
import br.com.market.service.dto.brand.CategoryBrandDTO
import br.com.market.service.models.Brand
import br.com.market.service.models.CategoryBrand
import br.com.market.service.repository.brand.IBrandRepository
import br.com.market.service.repository.brand.ICategoryBrandRepository
import br.com.market.service.repository.brand.ICustomBrandRepository
import br.com.market.service.repository.brand.ICustomCategoryBrandRepository
import br.com.market.service.repository.category.ICustomCategoryRepository
import org.springframework.stereotype.Service

@Service
class BrandService(
    private val brandRepository: IBrandRepository,
    private val customBrandRepository: ICustomBrandRepository,
    private val customCategoryRepository: ICustomCategoryRepository,
    private val categoryBrandRepository: ICategoryBrandRepository,
    private val customCategoryBrandRepository: ICustomCategoryBrandRepository
    ) {
    
    fun save(brandBodyDTO: BrandBodyDTO) {
        with(brandBodyDTO.brand) {
            val brand = customBrandRepository.findBrandByLocalId(localId)?.copy(
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
                    category = customCategoryRepository.findCategoryByLocalId(localCategoryId),
                    brand = brand,
                    active = active
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