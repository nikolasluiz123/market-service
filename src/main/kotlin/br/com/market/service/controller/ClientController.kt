package br.com.market.service.controller

import br.com.market.service.dto.ClientDTO
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.response.SingleValueResponse
import br.com.market.service.service.ClientService
import br.com.market.service.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/client")
class ClientController(
    private val clientService: ClientService,
    private val userService: UserService,
) {

    @PostMapping
    @Transactional(timeout = 600)
    fun saveClient(@RequestBody @Valid clientDTO: ClientDTO): ResponseEntity<PersistenceResponse> {
        val responseRegisterUser = userService.register(clientDTO.user)

        return if (responseRegisterUser.success) {
            val responseSaveClient = clientService.save(clientDTO)

            if (responseSaveClient.success) {
                ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
            } else {
                ResponseEntity.ok(PersistenceResponse(code = HttpStatus.BAD_REQUEST.value()))
            }
        } else {
            ResponseEntity.ok(PersistenceResponse(code = HttpStatus.BAD_REQUEST.value()))
        }
    }

    @GetMapping("/email")
    @Transactional(timeout = 600)
    fun isUniqueEmail(@RequestParam email: String): ResponseEntity<SingleValueResponse<Boolean>> {
        val isUnique = clientService.isUniqueEmail(email)
        return ResponseEntity.ok(SingleValueResponse(value = isUnique, code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping("/cpf")
    @Transactional(timeout = 600)
    fun isUniqueCPF(@RequestParam cpf: String): ResponseEntity<SingleValueResponse<Boolean>> {
        val isUnique = clientService.isUniqueCPF(cpf)
        return ResponseEntity.ok(SingleValueResponse(value = isUnique, code = HttpStatus.OK.value(), success = true))
    }

}