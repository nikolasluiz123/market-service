package br.com.market.service.service

import br.com.market.service.dto.NewProductDTO
import br.com.market.service.dto.UpdateProductDTO
import br.com.market.service.mappers.ProductViewMapper
import br.com.market.service.models.Brand
import br.com.market.service.models.Product
import br.com.market.service.models.ProductBrand
import br.com.market.service.repository.BrandRepository
import br.com.market.service.repository.ProductBrandRepository
import br.com.market.service.repository.ProductRepository
import br.com.market.service.view.ProductView
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
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

    fun saveProduct(productDTO: NewProductDTO) {
        val product = Product(name = productDTO.name)
        val brands = productDTO.brands.map { Brand(name = it) }

        productRepository.save(product)
        brandRepository.saveAll(brands)

        val productBrands = brands.map { ProductBrand(product = product, brand = it) }
        productBrandRepository.saveAll(productBrands)
    }

    fun updateProduct(productDTO: UpdateProductDTO) {
        val product = productRepository.findById(productDTO.id).orElseThrow(::NotFoundException)
        product.name = productDTO.name

        productDTO.brands.forEach {
            val brand = brandRepository.findById(it.id).orElseThrow(::NotFoundException)
            brand.name = it.name
        }
    }
}