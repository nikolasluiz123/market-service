package br.com.market.service.controller

import br.com.market.service.dto.brand.BrandView
import br.com.market.service.dto.brand.UpdateStorageDTO
import br.com.market.service.service.BrandService
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/marcas")
class BrandController(private val service: BrandService) {

    @GetMapping("/{productId}")
    fun findProductBrands(@PathVariable productId: Long): List<BrandView> {
        return service.findProductBrands(productId)
    }

    @PostMapping("/adicionar")
    @Transactional
    fun sumStorageCount(@RequestBody storageDTO: UpdateStorageDTO) {
        service.sumStorageCount(storageDTO)
    }

    @PostMapping("/subtrair")
    @Transactional
    fun subtractStorageCount(@RequestBody storageDTO: UpdateStorageDTO) {
        service.subtractStorageCount(storageDTO)
    }
}