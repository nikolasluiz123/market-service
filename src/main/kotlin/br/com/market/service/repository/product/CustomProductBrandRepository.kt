package br.com.market.service.repository.product

import br.com.market.service.models.ProductBrand2
import java.util.*

interface CustomProductBrandRepository {

    fun findByLocalProductId(localProductId: UUID): List<ProductBrand2>

    fun findByLocalBrandId(localBrandId: UUID): Optional<ProductBrand2>

//    fun sumStorageCount(storageDTO: UpdateStorageDTO)
//
//    fun subtractStorageCount(storageDTO: UpdateStorageDTO)
}