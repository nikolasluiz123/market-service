package br.com.market.service.controller

import br.com.market.service.controller.ControllerConstants.TIMEOUT
import br.com.market.service.controller.params.ProductServiceSearchParams
import br.com.market.service.controller.params.StorageOperationsHistoryServiceSearchParams
import br.com.market.service.dto.ProductAndReferencesDTO
import br.com.market.service.dto.StorageOperationHistoryDTO
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.response.ReadResponse
import br.com.market.service.service.StorageOperationsHistoryService
import com.google.gson.Gson
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/storageOperationsHistory")
class StorageOperationsHistoryController(private val service: StorageOperationsHistoryService) {

    @PostMapping
    @Transactional(timeout = TIMEOUT)
    fun save(@RequestBody @Valid storageOperationHistoryDTO: StorageOperationHistoryDTO): ResponseEntity<PersistenceResponse> {
        service.save(storageOperationHistoryDTO)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/inactivate")
    @Transactional(timeout = TIMEOUT)
    fun inactivate(@RequestParam localId: String): ResponseEntity<PersistenceResponse> {
        service.inactivate(localId)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping
    @Transactional(timeout = TIMEOUT)
    fun getListStorageOperations(@RequestParam("jsonParams") jsonParams: String): ResponseEntity<ReadResponse<StorageOperationHistoryDTO>> {
        val params = Gson().fromJson(jsonParams, StorageOperationsHistoryServiceSearchParams::class.java)
        val values = service.getListStorageOperations(params)

        return ResponseEntity.ok(ReadResponse(values = values, code = HttpStatus.OK.value(), success = true))
    }
}