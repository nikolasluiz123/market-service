package br.com.market.service.controller

import br.com.market.service.dto.product.DeleteProductDTO
import br.com.market.service.dto.product.NewProductDTO
import br.com.market.service.dto.product.ProductView
import br.com.market.service.dto.product.UpdateProductDTO
import br.com.market.service.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/product")
class ProductController(private val service: ProductService) {

    @GetMapping
    fun findAllProducts(): List<ProductView> {
        return service.findAllProducts()
    }

    @PostMapping
    @Transactional(rollbackFor = [Exception::class])
    fun saveProduct(@RequestBody @Valid productDTO: NewProductDTO): ResponseEntity<Void> {
        service.saveProduct(productDTO)
        return ResponseEntity.ok().build()
    }

    @PutMapping
    @Transactional(rollbackFor = [Exception::class])
    fun updateProduct(@RequestBody productDTO: UpdateProductDTO): ResponseEntity<Void> {
        service.updateProduct(productDTO)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping
    @Transactional(rollbackFor = [Exception::class])
    fun deleteProduct(@RequestBody productDTO: DeleteProductDTO): ResponseEntity<Void> {
        service.deleteProduct(productDTO)
        return ResponseEntity.ok().build()
    }
}