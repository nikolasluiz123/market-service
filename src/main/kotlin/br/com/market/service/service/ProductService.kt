package br.com.market.service.service

import br.com.market.service.dto.product.ProductBodyDTO
import br.com.market.service.models.Product
import br.com.market.service.models.ProductImage
import br.com.market.service.repository.brand.ICategoryBrandRepository
import br.com.market.service.repository.product.IProductImageRepository
import br.com.market.service.repository.product.IProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: IProductRepository,
    private val productImageRepository: IProductImageRepository,
    private val categoryBrandRepository: ICategoryBrandRepository
) {

    fun save(productBodyDTO: ProductBodyDTO) {
        with(productBodyDTO.product) {
            val product = productRepository.findProductByLocalId(localId)?.copy(
                id = id,
                name = name,
                price = price!!,
                quantity = quantity!!,
                quantityUnit = quantityUnit,
                categoryBrand = categoryBrandRepository.findCategoryBrandByLocalId(categoryBrandLocalId!!),
                active = active,
                localId = localId
            ) ?: Product(
                name = name,
                price = price!!,
                quantity = quantity!!,
                quantityUnit = quantityUnit,
                categoryBrand = categoryBrandRepository.findCategoryBrandByLocalId(categoryBrandLocalId!!),
                active = active,
                localId = localId
            )

            productRepository.save(product)
        }

        val productImages = productBodyDTO.productImages.map { productImageDTO ->
            with(productImageDTO) {
                productImageRepository.findProductImageByLocalId(localId)?.copy(
                    localId = localId,
                    active = active,
                    id = id,
                    bytes = bytes,
                    imageUrl = imageUrl,
                    product = productRepository.findProductByLocalId(productLocalId!!)
                ) ?: ProductImage(
                    localId = localId,
                    active = active,
                    bytes = bytes,
                    imageUrl = imageUrl,
                    product = productRepository.findProductByLocalId(productLocalId!!)
                )
            }
        }

        productImageRepository.deleteProductImagesByProductLocalId(productBodyDTO.product.localId)
        productImageRepository.saveAll(productImages)
    }
}