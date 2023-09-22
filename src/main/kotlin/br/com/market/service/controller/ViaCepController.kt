package br.com.market.service.controller

import br.com.market.service.dto.ViaCepDTO
import br.com.market.service.response.SingleValueResponse
import br.com.market.service.service.ViaCepClient
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/viacep")
class ViaCepController(
    private val service: ViaCepClient
) {

    @GetMapping("/cep")
    fun getAddressByCep(@RequestParam("cep") cep: String): ResponseEntity<SingleValueResponse<ViaCepDTO>> {
        val viaCepDTO = service.getAddressByCep(cep)

        return if (viaCepDTO != null) {
            ResponseEntity.ok(SingleValueResponse(value = viaCepDTO, code = HttpStatus.OK.value(), success = true))
        } else {
            ResponseEntity.ok(SingleValueResponse(value = null, code = HttpStatus.NOT_FOUND.value()))
        }
    }

}