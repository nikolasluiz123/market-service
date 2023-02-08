package br.com.market.service.repository.product

import br.com.market.service.dto.brand.UpdateStorageDTO
import br.com.market.service.models.ProductBrand

interface CustomProductBrandRepository {

    fun findByProductId(id: Long): List<ProductBrand>

    fun sumStorageCount(storageDTO: UpdateStorageDTO)

    fun subtractStorageCount(storageDTO: UpdateStorageDTO)
}