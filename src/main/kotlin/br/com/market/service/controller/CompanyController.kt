package br.com.market.service.controller

import br.com.market.service.dto.CompanyDTO
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.response.ReadResponse
import br.com.market.service.service.CompanyService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/company")
class CompanyController(private val service: CompanyService) {

    @GetMapping
    @Transactional(timeout = 600)
    fun findAll(): ResponseEntity<ReadResponse<CompanyDTO>> {
        val values = service.findAll()
        return ResponseEntity.ok(ReadResponse(values = values, code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping
    @Transactional(timeout = 600)
    fun save(@RequestBody @Valid companyDTO: CompanyDTO): ResponseEntity<PersistenceResponse> {
        service.save(companyDTO)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/toggleActive")
    @Transactional(timeout = 600)
    fun toggleActive(@RequestParam companyId: Long): ResponseEntity<PersistenceResponse> {
        service.toggleActive(companyId)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping("/{deviceId}")
    @Transactional(timeout = 600)
    fun findByDeviceId(@PathVariable deviceId: String): ResponseEntity<ReadResponse<CompanyDTO>> {
        val company = service.findByDeviceId(deviceId)

        return ResponseEntity.ok(
            ReadResponse(
                values = company.let(::listOf),
                code = HttpStatus.OK.value(),
                success = true,
            )
        )
    }
}