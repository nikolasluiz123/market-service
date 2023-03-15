package br.com.market.service.repository.product

import br.com.market.service.models.ProductBrand
import java.util.*

interface CustomProductBrandRepository {

    fun findByLocalProductId(localProductId: UUID): List<ProductBrand>

    fun findByLocalBrandId(localBrandId: UUID): Optional<ProductBrand>

//    fun sumStorageCount(storageDTO: UpdateStorageDTO)
//
//    fun subtractStorageCount(storageDTO: UpdateStorageDTO)
}