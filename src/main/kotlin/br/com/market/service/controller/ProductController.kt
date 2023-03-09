package br.com.market.service.controller

import br.com.market.service.dto.product.DeleteProductDTO
import br.com.market.service.dto.product.NewProductDTO
import br.com.market.service.dto.product.ProductView
import br.com.market.service.dto.product.UpdateProductDTO
import br.com.market.service.response.MarketServiceResponse
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
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
    @Transactional(rollbackFor = [Exception::class], timeout = 600)
    fun saveProduct(@RequestBody @Valid productDTO: NewProductDTO): ResponseEntity<PersistenceResponse> {
        val savedProduct = service.saveProduct(productDTO)
        return ResponseEntity.ok(
            PersistenceResponse(
                idRemote = savedProduct.id,
                code = HttpStatus.OK.value(),
                success = true
            )
        )
    }

    @PutMapping
    @Transactional(rollbackFor = [Exception::class], timeout = 600)
    fun updateProduct(@RequestBody productDTO: UpdateProductDTO): ResponseEntity<PersistenceResponse> {
        val updatedProduct = service.updateProduct(productDTO)

        return ResponseEntity.ok(
            PersistenceResponse(
                idLocal = updatedProduct.idLocal,
                idRemote = updatedProduct.id,
                code = HttpStatus.OK.value(),
                success = true
            )
        )
    }

    @PostMapping("/synchronize")
    @Transactional(rollbackFor = [Exception::class], timeout = 600)
    fun syncProducts(@RequestBody @Valid productDTOs: List<NewProductDTO>): ResponseEntity<MarketServiceResponse> {
        service.syncProducts(productDTOs)
        return ResponseEntity.ok(MarketServiceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @DeleteMapping
    @Transactional(rollbackFor = [Exception::class], timeout = 600)
    fun deleteProduct(@RequestBody productDTO: DeleteProductDTO): ResponseEntity<Void> {
        service.deleteProduct(productDTO)
        return ResponseEntity.ok().build()
    }
}