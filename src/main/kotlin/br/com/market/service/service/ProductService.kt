package br.com.market.service.service

import br.com.market.service.dto.ProductBodyDTO
import br.com.market.service.dto.ProductClientDTO
import br.com.market.service.dto.ProductDTO
import br.com.market.service.dto.ProductImageDTO
import br.com.market.service.models.Product
import br.com.market.service.models.ProductImage
import br.com.market.service.repository.brand.ICustomCategoryBrandRepository
import br.com.market.service.repository.market.IMarketRepository
import br.com.market.service.repository.product.ICustomProductImageRepository
import br.com.market.service.repository.product.ICustomProductRepository
import br.com.market.service.repository.product.IProductImageRepository
import br.com.market.service.repository.product.IProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: IProductRepository,
    private val customProductRepository: ICustomProductRepository,
    private val productImageRepository: IProductImageRepository,
    private val customProductImageRepository: ICustomProductImageRepository,
    private val customCategoryBrandRepository: ICustomCategoryBrandRepository,
    private val marketRepository: IMarketRepository
) {

    fun saveProduct(productBodyDTO: ProductBodyDTO) {
        with(productBodyDTO.product) {
            val product = customProductRepository.findProductByLocalId(localId)?.copy(
                name = name,
                price = price!!,
                quantity = quantity!!,
                quantityUnit = quantityUnit,
                categoryBrand = customCategoryBrandRepository.findCategoryBrandByLocalId(categoryBrandLocalId!!),
                active = active,
                localId = localId
            ) ?: Product(
                name = name,
                price = price!!,
                quantity = quantity!!,
                quantityUnit = quantityUnit,
                categoryBrand = customCategoryBrandRepository.findCategoryBrandByLocalId(categoryBrandLocalId!!),
                active = active,
                localId = localId,
                market = marketRepository.findById(marketId!!).get()
            )

            productRepository.save(product)

            val productImages = productBodyDTO.productImages.map { productImageDTO ->
                with(productImageDTO) {
                    customProductImageRepository.findProductImageByLocalId(localId)?.copy(
                        localId = localId,
                        active = active,
                        bytes = bytes,
                        imageUrl = imageUrl,
                        product = product,
                        principal = principal
                    ) ?: ProductImage(
                        localId = localId,
                        active = active,
                        bytes = bytes,
                        imageUrl = imageUrl,
                        product = product,
                        principal = principal,
                        market = marketRepository.findById(marketId!!).get()
                    )
                }
            }

            productImageRepository.saveAll(productImages.filter { it.id == null })
        }
    }

    fun updateProductImage(productImageDTO: ProductImageDTO) {
        val productImage = with(productImageDTO) {
            customProductImageRepository.findProductImageByLocalId(localId)?.copy(
                localId = localId,
                active = active,
                bytes = bytes,
                imageUrl = imageUrl,
                product = customProductRepository.findProductByLocalId(productLocalId!!),
                principal = principal
            )
        }

        customProductImageRepository.updateProductImagePrincipal(productId = productImage?.product?.id!!, id = productImage.id!!)
        productImageRepository.save(productImage)
    }

    fun toggleActiveProduct(productLocalId: String) {
        customProductRepository.findProductByLocalId(productLocalId)?.let { product ->
            productRepository.save(product.copy(active = !product.active))

            val images = customProductImageRepository.findProductImagesByProductId(product.id!!).map { it.copy(active = !it.active) }
            productImageRepository.saveAll(images)
        }

    }

    fun toggleActiveProductImage(productImageLocalId: String) {
        customProductImageRepository.findProductImageByLocalId(productImageLocalId)?.let {
            productImageRepository.save(it.copy(active = !it.active))
        }
    }

    fun sync(productBodyDTOs: List<ProductBodyDTO>) {
        productBodyDTOs.forEach(::saveProduct)
    }

    fun findAllProductDTOs(marketId: Long, limit: Int? = null, offset: Int? = null): List<ProductDTO> {
        return customProductRepository.findAll(marketId, limit, offset).map {
            ProductDTO(
                id = it.id!!,
                active = it.active,
                localId = it.localId!!,
                name = it.name,
                price = it.price,
                quantityUnit = it.quantityUnit,
                quantity = it.quantity,
                categoryBrandLocalId = it.categoryBrand?.localId,
                marketId = it.market?.id
            )
        }
    }

    fun findProductImageDTOs(marketId: Long, limit: Int? = null, offset: Int? = null): List<ProductImageDTO> {
        return customProductImageRepository.findAll(marketId, limit, offset).map {
            ProductImageDTO(
                id = it.id,
                localId = it.localId!!,
                active = it.active,
                bytes = it.bytes,
                imageUrl = it.imageUrl,
                productLocalId = it.product?.localId,
                principal = it.principal!!,
                marketId = it.market?.id!!
            )
        }
    }

    fun findProducts(simpleFilter: String?, limit: Int, offset: Int): List<ProductClientDTO> {
        return customProductRepository.findProducts(simpleFilter, limit, offset)
    }
}