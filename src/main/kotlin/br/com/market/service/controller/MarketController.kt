package br.com.market.service.controller

import br.com.market.service.dto.MarketDTO
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.response.ReadResponse
import br.com.market.service.service.MarketService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/market")
class MarketController(private val service: MarketService) {

    @GetMapping
    @Transactional(timeout = 600)
    fun findAll(): ResponseEntity<ReadResponse<MarketDTO>> {
        val values = service.findAll()
        return ResponseEntity.ok(ReadResponse(values = values, code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping
    @Transactional(timeout = 600)
    fun save(@RequestBody @Valid marketDTO: MarketDTO): ResponseEntity<PersistenceResponse> {
        service.save(marketDTO)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/toggleActive")
    @Transactional(timeout = 600)
    fun toggleActive(@RequestParam marketId: Long): ResponseEntity<PersistenceResponse> {
        service.toggleActive(marketId)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping("/{deviceId}")
    @Transactional(timeout = 600)
    fun findByDeviceId(@PathVariable deviceId: String): ResponseEntity<ReadResponse<MarketDTO>> {
        val market = service.findByDeviceId(deviceId)

        return ResponseEntity.ok(
            ReadResponse(
                values = market.let(::listOf),
                code = HttpStatus.OK.value(),
                success = true,
            )
        )
    }
}