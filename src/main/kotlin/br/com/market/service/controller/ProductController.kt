package br.com.market.service.controller

import br.com.market.service.dto.product.ProductBodyDTO
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/product")
class ProductController(private val service: ProductService) {

    @PostMapping
    @Transactional(timeout = 600)
    fun save(@RequestBody @Valid productBodyDTO: ProductBodyDTO): ResponseEntity<PersistenceResponse> {
        service.save(productBodyDTO)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }
}