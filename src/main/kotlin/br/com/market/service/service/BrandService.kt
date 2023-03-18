package br.com.market.service.service

import br.com.market.service.dto.brand.*
import br.com.market.service.models.Brand
import br.com.market.service.models.ProductBrand
import br.com.market.service.repository.brand.BrandRepository
import br.com.market.service.repository.product.ProductBrandRepository
import br.com.market.service.repository.product.ProductRepository
import org.springframework.stereotype.Service

@Service
class BrandService(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
    private val productBrandRepository: ProductBrandRepository
) {

    fun findAllBrands(): List<SyncBrandDTO> {
        return brandRepository.findAll().map {
            SyncBrandDTO(it.idLocal!!, it.name)
        }
    }

    fun findAllProductBrands(): List<SyncProductBrandDTO> {
        return productBrandRepository.findAll().map {
            SyncProductBrandDTO(it.idLocal!!, it.product.idLocal!!, it.brand.idLocal!!, it.count)
        }
    }

    fun saveBrand(newBrandDTO: NewBrandDTO): Brand? {
        val product = productRepository.findProductByLocalId(newBrandDTO.localProductId)

        if (product.isPresent) {
            var brand = Brand(name = newBrandDTO.name, idLocal = newBrandDTO.localBrandId)
            brand = brandRepository.save(brand)

            val productBrand = ProductBrand(
                idLocal = newBrandDTO.localProductBrandId,
                product = product.get(),
                brand = brand,
                count = newBrandDTO.count
            )

            productBrandRepository.save(productBrand)

            return brand
        }

        return null
    }

    fun updateBrand(updateBrandDTO: UpdateBrandDTO): Brand? {
        val brandOptional = brandRepository.findBrandByLocalId(updateBrandDTO.localBrandId)
        val productBrandOptional = productBrandRepository.findByLocalBrandId(updateBrandDTO.localBrandId)

        if (brandOptional.isPresent && productBrandOptional.isPresent) {
            val brand = brandOptional.get()
            brand.name = updateBrandDTO.name

            val productBrand = productBrandOptional.get()
            productBrand.count = updateBrandDTO.count

            return brand
        }

        return null
    }

    fun deleteBrand(deleteBrandDTO: DeleteBrandDTO) {
        val productBrandOptional = productBrandRepository.findByLocalBrandId(deleteBrandDTO.localBrandId)

        if (productBrandOptional.isPresent) {
            val productBrand = productBrandOptional.get()

            productBrandRepository.deleteById(productBrand.id!!)
            brandRepository.deleteById(productBrand.brand.id!!)
        }
    }

    fun deleteBrands(brandDTOs: List<DeleteBrandDTO>) {
        brandDTOs.forEach(::deleteBrand)
    }

    fun syncBrands(brandsDTO: List<NewBrandDTO>) {
        brandsDTO.forEach { brandDTO ->
            val brandOptional = brandRepository.findBrandByLocalId(brandDTO.localBrandId)

            if (brandOptional.isPresent) {
                val brand = brandOptional.get()

                updateBrand(
                    UpdateBrandDTO(
                        localBrandId = brand.idLocal!!,
                        name = brandDTO.name,
                        count = brandDTO.count
                    )
                )
            } else {
                saveBrand(brandDTO)
            }
        }
    }
}