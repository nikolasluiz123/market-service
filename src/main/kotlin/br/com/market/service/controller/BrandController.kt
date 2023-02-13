package br.com.market.service.controller

import br.com.market.service.dto.brand.BrandView
import br.com.market.service.dto.brand.UpdateStorageDTO
import br.com.market.service.service.BrandService
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/brand")
class BrandController(private val service: BrandService) {

    @GetMapping("/{productId}")
    fun findProductBrands(@PathVariable productId: Long): List<BrandView> {
        return service.findProductBrands(productId)
    }

    @PostMapping("/add")
    @Transactional
    fun sumStorageCount(@RequestBody storageDTO: UpdateStorageDTO): ResponseEntity<Void> {
        service.sumStorageCount(storageDTO)
        return ResponseEntity.ok().build()
    }

    @PostMapping("/subtract")
    @Transactional
    fun subtractStorageCount(@RequestBody storageDTO: UpdateStorageDTO): ResponseEntity<Void> {
        service.subtractStorageCount(storageDTO)
        return ResponseEntity.ok().build()
    }
}