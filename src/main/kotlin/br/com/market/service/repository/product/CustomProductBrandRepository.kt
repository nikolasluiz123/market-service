package br.com.market.service.repository.product

import br.com.market.service.models.ProductBrand
import java.util.Optional

interface CustomProductBrandRepository {

    fun findByLocalProductId(localProductId: Long): List<ProductBrand>

    fun findByLocalBrandId(localBrandId: Long): Optional<ProductBrand>

//    fun sumStorageCount(storageDTO: UpdateStorageDTO)
//
//    fun subtractStorageCount(storageDTO: UpdateStorageDTO)
}