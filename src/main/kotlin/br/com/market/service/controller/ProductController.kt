package br.com.market.service.controller

import br.com.market.service.dto.product.DeleteProductDTO
import br.com.market.service.dto.product.NewProductDTO
import br.com.market.service.dto.product.UpdateProductDTO
import br.com.market.service.service.ProductService
import br.com.market.service.dto.product.ProductView
import jakarta.validation.Valid
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/produtos")
class ProductController(private val service: ProductService) {

    @GetMapping
    fun findAllProducts(): List<ProductView> {
        return service.findAllProducts()
    }

    @PostMapping
    @Transactional(rollbackFor = [Exception::class])
    fun saveProduct(@RequestBody @Valid productDTO: NewProductDTO) {
        service.saveProduct(productDTO)
    }

    @PutMapping
    @Transactional(rollbackFor = [Exception::class])
    fun updateProduct(@RequestBody productDTO: UpdateProductDTO) {
        service.updateProduct(productDTO)
    }

    @DeleteMapping
    @Transactional(rollbackFor = [Exception::class])
    fun deleteProduct(@RequestBody productDTO: DeleteProductDTO) {
        service.deleteProduct(productDTO)
    }
}