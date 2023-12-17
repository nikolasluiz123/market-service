package br.com.market.service.controller

import br.com.market.service.controller.ControllerConstants.TIMEOUT
import br.com.market.service.dto.BrandAndReferencesDTO
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.response.ReadResponse
import br.com.market.service.response.SingleValueResponse
import br.com.market.service.service.BrandService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/brand")
class BrandController(private val service: BrandService) {

    @PostMapping
    @Transactional(timeout = TIMEOUT)
    fun save(@RequestBody @Valid brandBodyDTO: BrandAndReferencesDTO): ResponseEntity<PersistenceResponse> {
        service.save(brandBodyDTO)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/toggleActive/{categoryId}/{brandId}")
    @Transactional(timeout = TIMEOUT)
    fun toggleActive(@PathVariable categoryId: String, @PathVariable brandId: String): ResponseEntity<PersistenceResponse> {
        service.toggleActive(categoryId, brandId)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping
    @Transactional(timeout = TIMEOUT)
    fun getListBrand(
        @RequestParam simpleFilter: String?,
        @RequestParam categoryLocalId: String?,
        @RequestParam marketId: Long,
        @RequestParam limit: Int,
        @RequestParam offset: Int
    ): ResponseEntity<ReadResponse<BrandAndReferencesDTO>> {
        val values = service.getListBrand(simpleFilter, categoryLocalId, marketId, limit, offset)
        return ResponseEntity.ok(ReadResponse(values = values, code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping("/{categoryId}/{brandId}")
    @Transactional(timeout = TIMEOUT)
    fun findBrandByLocalId(@PathVariable categoryId: String, @PathVariable brandId: String): ResponseEntity<SingleValueResponse<BrandAndReferencesDTO>> {
        val value = service.findBrandByLocalId(categoryId = categoryId, brandId = brandId)
        return ResponseEntity.ok(SingleValueResponse(value = value, code = HttpStatus.OK.value(), success = true))
    }
}