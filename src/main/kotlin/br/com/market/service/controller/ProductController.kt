package br.com.market.service.controller

import br.com.market.service.dto.filter.ProductFiltersDTO
import br.com.market.service.dto.filter.ProductImageFiltersDTO
import br.com.market.service.dto.product.ProductBodyDTO
import br.com.market.service.dto.product.ProductDTO
import br.com.market.service.dto.product.ProductImageDTO
import br.com.market.service.response.MarketServiceResponse
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.response.ReadResponse
import br.com.market.service.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/product")
class ProductController(private val service: ProductService) {

    @PostMapping
    @Transactional(timeout = 600)
    fun saveProduct(@RequestBody @Valid productBodyDTO: ProductBodyDTO): ResponseEntity<PersistenceResponse> {
        service.saveProduct(productBodyDTO)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/image")
    @Transactional(timeout = 600)
    fun updateProductImage(@RequestBody @Valid productImageDTO: ProductImageDTO): ResponseEntity<PersistenceResponse> {
        service.updateProductImage(productImageDTO)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/toggleActive")
    @Transactional(timeout = 600)
    fun toggleActiveProduct(@RequestParam productLocalId: String): ResponseEntity<PersistenceResponse> {
        service.toggleActiveProduct(productLocalId)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/image/toggleActive")
    @Transactional(timeout = 600)
    fun toggleActiveProductImage(@RequestParam productImageLocalId: String): ResponseEntity<PersistenceResponse> {
        service.toggleActiveProductImage(productImageLocalId)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/sync")
    @Transactional(timeout = 600)
    fun sync(@RequestBody @Valid productBodyDTOs: List<ProductBodyDTO>): ResponseEntity<MarketServiceResponse> {
        service.sync(productBodyDTOs)
        return ResponseEntity.ok(MarketServiceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping
    @Transactional(timeout = 600)
    fun findAllProductDTOs(@RequestBody productFiltersDTO: ProductFiltersDTO): ResponseEntity<ReadResponse<ProductDTO>> {
        val values = service.findAllProductDTOs(productFiltersDTO)
        return ResponseEntity.ok(ReadResponse(values = values, code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping("/images")
    @Transactional(timeout = 600)
    fun findAllProductImageDTOs(@RequestBody productImageFiltersDTO: ProductImageFiltersDTO): ResponseEntity<ReadResponse<ProductImageDTO>> {
        val values = service.findAllProductImageDTOs(productImageFiltersDTO)
        return ResponseEntity.ok(ReadResponse(values = values, code = HttpStatus.OK.value(), success = true))
    }
}