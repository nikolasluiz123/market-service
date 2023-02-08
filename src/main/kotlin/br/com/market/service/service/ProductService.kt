package br.com.market.service.service

import br.com.market.service.dto.product.DeleteProductDTO
import br.com.market.service.dto.product.NewProductDTO
import br.com.market.service.dto.product.UpdateProductDTO
import br.com.market.service.mappers.BrandDTOMapper
import br.com.market.service.mappers.ProductViewMapper
import br.com.market.service.models.Product
import br.com.market.service.models.ProductBrand
import br.com.market.service.repository.brand.BrandRepository
import br.com.market.service.repository.product.ProductBrandRepository
import br.com.market.service.repository.product.ProductRepository
import br.com.market.service.dto.product.ProductView
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException
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
        val product = productRepository.findById(productDTO.id).orElseThrow(::NotFoundException)
        product.name = productDTO.name

        productDTO.brands.forEach {
            val brand = brandRepository.findById(it.id).orElseThrow(::NotFoundException)
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