package br.com.market.service.service

import br.com.market.service.dto.brand.BrandView
import br.com.market.service.dto.brand.UpdateStorageDTO
import br.com.market.service.repository.brand.BrandRepository
import br.com.market.service.repository.product.ProductBrandRepository
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
import org.springframework.stereotype.Service

@Service
class BrandService(
    private val brandRepository: BrandRepository,
    private val productBrandRepository: ProductBrandRepository
) {

    fun findProductBrands(productId: Long): List<BrandView> {
        return brandRepository.findProductBrands(productId)
    }

    fun sumStorageCount(storageDTO: UpdateStorageDTO) {
        productBrandRepository.sumStorageCount(storageDTO)
    }

    fun subtractStorageCount(storageDTO: UpdateStorageDTO) {
        productBrandRepository.subtractStorageCount(storageDTO)
    }
}