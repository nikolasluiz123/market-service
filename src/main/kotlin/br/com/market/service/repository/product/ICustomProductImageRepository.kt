package br.com.market.service.repository.product

import br.com.market.service.models.ProductImage

interface ICustomProductImageRepository {

    fun findProductImageByLocalId(localId: String): ProductImage?

    fun findProductImagesByProductId(productId: Long): List<ProductImage>

    fun toggleActiveProductImagesByProductLocalId(localProductId: String, active: Boolean)

    fun updateProductImagePrincipal(productId: Long, id: Long)
    fun findAll(marketId: Long, limit: Int? = null, offset: Int? = null): List<ProductImage>

    fun toggleActiveProductImage(productId: String, imageId: String)
}
