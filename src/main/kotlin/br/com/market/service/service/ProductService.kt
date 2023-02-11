package br.com.market.service.service

import br.com.market.service.dto.product.*
import br.com.market.service.mappers.BrandDTOMapper
import br.com.market.service.mappers.ProductViewMapper
import br.com.market.service.models.Product
import br.com.market.service.models.ProductBrand
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

    fun findAllProducts(): List<ProductView> {
        return productRepository.findAll().map(ProductViewMapper::toDTO)
    }

    fun saveProduct(productDTO: NewProductDTO) {
        val product = Product(name = productDTO.name)
        val brands = productDTO.brands.map(BrandDTOMapper::toModel)

        productRepository.save(product)
        brandRepository.saveAll(brands)

        val productBrands = brands.map { ProductBrand(product = product, brand = it) }
        productBrandRepository.saveAll(productBrands)
    }

    fun updateProduct(productDTO: UpdateProductDTO) {
        val product = productRepository.findById(productDTO.id).orElseThrow {
            EntityNotFoundException("Não foi possível encontrar o produto com o identificador especificado.")
        }

        product.name = productDTO.name

        productDTO.brands.forEach {
            val brand = brandRepository.findById(it.id).orElseThrow {
                EntityNotFoundException("Não foi possível encontrar a marca com o identificador especificado.")
            }

            brand.name = it.name
        }
    }

    fun deleteProduct(productDTO: DeleteProductDTO) {
        val productBrandList = productBrandRepository.findByProductId(productDTO.id)

        val brandIds = productBrandList.map { productBrand ->
            val brandId = productBrand.brand.id
            productBrandRepository.deleteById(productBrand.id!!)
            brandId
        }

        brandIds.forEach { brandRepository.deleteById(it!!) }

        productRepository.deleteById(productDTO.id)
    }
}