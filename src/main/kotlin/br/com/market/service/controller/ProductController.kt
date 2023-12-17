package br.com.market.service.controller

import br.com.market.service.controller.ControllerConstants.TIMEOUT
import br.com.market.service.controller.params.ProductServiceSearchParams
import br.com.market.service.dto.ProductAndReferencesDTO
import br.com.market.service.dto.ProductClientDTO
import br.com.market.service.dto.ProductImageDTO
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.response.ReadResponse
import br.com.market.service.response.SingleValueResponse
import br.com.market.service.service.ProductService
import com.google.gson.Gson
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/product")
class ProductController(private val service: ProductService) {

    @PostMapping
    @Transactional(timeout = TIMEOUT)
    fun saveProduct(@RequestBody @Valid productAndReferencesDTO: ProductAndReferencesDTO): ResponseEntity<PersistenceResponse> {
        service.saveProduct(productAndReferencesDTO)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/image")
    @Transactional(timeout = TIMEOUT)
    fun updateProductImage(@RequestBody @Valid productImageDTO: ProductImageDTO): ResponseEntity<PersistenceResponse> {
        service.updateProductImage(productImageDTO)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/toggleActive")
    @Transactional(timeout = TIMEOUT)
    fun toggleActiveProduct(@RequestParam productLocalId: String): ResponseEntity<PersistenceResponse> {
        service.toggleActiveProduct(productLocalId)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/image/toggleActive")
    @Transactional(timeout = TIMEOUT)
    fun toggleActiveProductImage(@RequestParam productId: String, @RequestParam imageId: String): ResponseEntity<PersistenceResponse> {
        service.toggleActiveProductImage(productId, imageId)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping("/client")
    @Transactional
    fun findProducts(@RequestParam simpleFilter: String?, @RequestParam limit: Int, @RequestParam offset: Int): ResponseEntity<ReadResponse<ProductClientDTO>> {
        val values = service.findProducts(simpleFilter, limit, offset)
        return ResponseEntity.ok(ReadResponse(values = values, code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping
    @Transactional(timeout = TIMEOUT)
    fun getListProducts(@RequestParam("jsonParams") jsonParams: String): ResponseEntity<ReadResponse<ProductAndReferencesDTO>> {
        val params = Gson().fromJson(jsonParams, ProductServiceSearchParams::class.java)
        val values = service.getListProducts(params)

        return ResponseEntity.ok(ReadResponse(values = values, code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping("/{productId}")
    @Transactional(timeout = TIMEOUT)
    fun findProductByLocalId(@PathVariable productId: String): ResponseEntity<SingleValueResponse<ProductAndReferencesDTO>> {
        val value = service.findProductByLocalId(productId)
        return ResponseEntity.ok(SingleValueResponse(value = value, code = HttpStatus.OK.value(), success = true))
    }
}