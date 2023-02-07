package br.com.market.service.controller

import br.com.market.service.dto.ProductDTO
import br.com.market.service.service.ProductService
import br.com.market.service.view.ProductView
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/produtos")
class ProductController(private val service: ProductService) {

    @GetMapping
    fun findAllProducts(): List<ProductView> {
        return service.findAllProducts()
    }

    @PostMapping
    fun saveProduct(@RequestBody productDTO: ProductDTO) {
        service.saveProduct(productDTO)
    }
}