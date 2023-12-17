package br.com.market.service.controller

import br.com.market.service.controller.ControllerConstants.TIMEOUT
import br.com.market.service.dto.DeviceDTO
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.response.ReadResponse
import br.com.market.service.service.DeviceService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/device")
class DeviceController(private val service: DeviceService) {

    @PostMapping
    @Transactional(timeout = TIMEOUT)
    fun save(@RequestBody @Valid deviceDTO: DeviceDTO): ResponseEntity<PersistenceResponse> {
        service.save(deviceDTO)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/toggleActive")
    @Transactional(timeout = TIMEOUT)
    fun toggleActive(@RequestParam deviceId: String): ResponseEntity<PersistenceResponse> {
        service.toggleActive(deviceId)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping
    @Transactional(timeout = TIMEOUT)
    fun findAll(): ResponseEntity<ReadResponse<DeviceDTO>> {
        val values = service.findAll()
        return ResponseEntity.ok(ReadResponse(values = values, code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping("/{id}")
    @Transactional(timeout = TIMEOUT)
    fun findById(@PathVariable id: String): ResponseEntity<ReadResponse<DeviceDTO>> {
        val device = service.findById(id)

        return ResponseEntity.ok(
            ReadResponse(
                values = device?.let(::listOf) ?: emptyList(),
                code = HttpStatus.OK.value(),
                success = true,
            )
        )
    }
}