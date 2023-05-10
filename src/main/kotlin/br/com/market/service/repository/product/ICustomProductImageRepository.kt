package br.com.market.service.repository.product

import br.com.market.service.models.ProductImage
import java.util.*

interface ICustomProductImageRepository {

    fun findProductImageByLocalId(localId: UUID): ProductImage?

    fun deleteProductImagesByProductLocalId(localProductId: UUID)
}
