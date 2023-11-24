package br.com.market.service.controller

import br.com.market.service.dto.AddressDTO
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.service.AddressService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/address")
class AddressController(private val service: AddressService) {

    @PostMapping
    @Transactional(timeout = 600)
    fun saveAll(@RequestBody list: List<AddressDTO>): ResponseEntity<PersistenceResponse> {
        service.saveAll(list)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

}