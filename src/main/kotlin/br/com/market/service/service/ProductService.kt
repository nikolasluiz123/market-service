package br.com.market.service.service

import br.com.market.service.dto.product.DeleteProductDTO
import br.com.market.service.dto.product.NewProductDTO
import br.com.market.service.dto.product.SyncProductDTO
import br.com.market.service.dto.product.UpdateProductDTO
import br.com.market.service.models.Product2
import br.com.market.service.repository.brand.BrandRepository
import br.com.market.service.repository.product.ProductBrandRepository
import br.com.market.service.repository.product.ProductRepository
import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
    private val productBrandRepository: ProductBrandRepository
) {

    fun findAllProducts(): List<SyncProductDTO> {
        return productRepository.findAll().map {
            SyncProductDTO(it.idLocal!!, it.name, it.imageUrl)
        }
    }

    fun saveProduct(productDTO: NewProductDTO): Product2 {
        val newProduct2 = Product2(idLocal = productDTO.localProductId, name = productDTO.name, imageUrl = productDTO.imageUrl)
        return productRepository.save(newProduct2)
    }

    fun syncProducts(productDTOs: List<NewProductDTO>) {
        productDTOs.forEach { productDTO ->
            val productOptional = productRepository.findProductByLocalId(productDTO.localProductId)

            if (productOptional.isPresent) {
                val product = productOptional.get()

                updateProduct(UpdateProductDTO(
                    id = product.id!!,
                    localProductId = productDTO.localProductId,
                    name = productDTO.name,
                    imageUrl = productDTO.imageUrl
                ))
            } else {
                saveProduct(productDTO)
            }
        }
    }

    fun updateProduct(productDTO: UpdateProductDTO): Product2 {
        val product = productRepository.findProductByLocalId(productDTO.localProductId).orElseThrow {
            EntityNotFoundException("Não foi possível encontrar o produto com o identificador especificado.")
        }

        product.name = productDTO.name
        product.imageUrl = productDTO.imageUrl

        return product
    }

    fun deleteProduct(productDTO: DeleteProductDTO) {
        val productBrandList = productBrandRepository.findByLocalProductId(productDTO.localProductId)

        val brandIds = productBrandList.map { productBrand ->
            val brandId = productBrand.brand2.id
            productBrandRepository.deleteById(productBrand.id!!)
            brandId
        }

        brandIds.forEach { brandRepository.deleteById(it!!) }

        val product = productRepository.findProductByLocalId(productDTO.localProductId)

        if (product.isPresent) {
            productRepository.deleteById(product.get().id!!)
        }
    }

    fun deleteProducts(productDTOs: List<DeleteProductDTO>) {
        productDTOs.forEach(::deleteProduct)
    }
}