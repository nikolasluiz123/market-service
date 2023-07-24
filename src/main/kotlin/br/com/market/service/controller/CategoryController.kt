package br.com.market.service.controller

import br.com.market.service.dto.category.CategoryDTO
import br.com.market.service.response.MarketServiceResponse
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.response.ReadResponse
import br.com.market.service.service.CategoryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/category")
class CategoryController(private val service: CategoryService) {

    @PostMapping
    @Transactional(timeout = 600)
    fun save(@RequestBody @Valid categoryDTO: CategoryDTO): ResponseEntity<PersistenceResponse> {
        service.save(categoryDTO)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/toggleActive")
    @Transactional(timeout = 600)
    fun toggleActive(@RequestBody @Valid categoryDTO: CategoryDTO): ResponseEntity<PersistenceResponse> {
        service.toggleActive(categoryDTO)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/sync")
    @Transactional(timeout = 6000)
    fun sync(@RequestBody @Valid categoriesDTOs: List<CategoryDTO>): ResponseEntity<MarketServiceResponse> {
        service.sync(categoriesDTOs)
        return ResponseEntity.ok(MarketServiceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping
    @Transactional(timeout = 600)
    fun findAll(@RequestParam marketId: Long): ResponseEntity<ReadResponse<CategoryDTO>> {
        val values = service.findAll(marketId)
        return ResponseEntity.ok(ReadResponse(values = values, code = HttpStatus.OK.value(), success = true))
    }
}