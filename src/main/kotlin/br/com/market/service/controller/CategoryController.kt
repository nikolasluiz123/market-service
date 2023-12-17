package br.com.market.service.controller

import br.com.market.service.controller.ControllerConstants.TIMEOUT
import br.com.market.service.dto.CategoryDTO
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.response.ReadResponse
import br.com.market.service.response.SingleValueResponse
import br.com.market.service.service.CategoryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/category")
class CategoryController(private val service: CategoryService) {

    @PostMapping
    @Transactional(timeout = TIMEOUT)
    fun save(@RequestBody @Valid categoryDTO: CategoryDTO): ResponseEntity<PersistenceResponse> {
        service.save(categoryDTO)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/toggleActive/{id}")
    @Transactional(timeout = TIMEOUT)
    fun toggleActive(@PathVariable id: String): ResponseEntity<PersistenceResponse> {
        service.toggleActive(id)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping
    @Transactional(timeout = TIMEOUT)
    fun getListCategory(
        @RequestParam simpleFilter: String?,
        @RequestParam marketId: Long,
        @RequestParam limit: Int,
        @RequestParam offset: Int
    ): ResponseEntity<ReadResponse<CategoryDTO>> {
        val values = service.getListCategory(simpleFilter, marketId, limit, offset)
        return ResponseEntity.ok(ReadResponse(values = values, code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping("/{id}")
    @Transactional(timeout = TIMEOUT)
    fun findCategoryByLocalId(@PathVariable id: String): ResponseEntity<SingleValueResponse<CategoryDTO>> {
        val value = service.findCategoryByLocalId(id)
        return ResponseEntity.ok(SingleValueResponse(value = value, code = HttpStatus.OK.value(), success = true))
    }
}