package br.com.market.service.service

import br.com.market.service.dto.ProductDTO
import br.com.market.service.mappers.ProductViewMapper
import br.com.market.service.models.Brand
import br.com.market.service.models.Product
import br.com.market.service.models.ProductBrand
import br.com.market.service.repository.BrandRepository
import br.com.market.service.repository.ProductBrandRepository
import br.com.market.service.repository.ProductRepository
import br.com.market.service.view.ProductView
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val brandRepository: BrandRepository,
    private val productBrandRepository: ProductBrandRepository
) {

    fun findAllProducts(): List<ProductView> {
        return productRepository.findAll().map(ProductViewMapper()::map)
    }

    fun saveProduct(productDTO: ProductDTO) {
        val product = Product(name = productDTO.name)
        val brands = productDTO.brands.map { Brand(name = it) }

        productRepository.save(product)
        brandRepository.saveAll(brands)

        val productBrands = brands.map { ProductBrand(product = product, brand = it) }
        productBrandRepository.saveAll(productBrands)
    }
}