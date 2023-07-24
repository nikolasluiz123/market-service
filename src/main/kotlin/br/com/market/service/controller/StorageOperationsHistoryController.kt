package br.com.market.service.controller

import br.com.market.service.dto.storageoperationhistory.StorageOperationHistoryDTO
import br.com.market.service.response.MarketServiceResponse
import br.com.market.service.response.PersistenceResponse
import br.com.market.service.response.ReadResponse
import br.com.market.service.service.StorageOperationsHistoryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/storageOperationsHistory")
class StorageOperationsHistoryController(private val service: StorageOperationsHistoryService) {

    @PostMapping
    @Transactional(timeout = 600)
    fun save(@RequestBody @Valid storageOperationHistoryDTO: StorageOperationHistoryDTO): ResponseEntity<PersistenceResponse> {
        service.save(storageOperationHistoryDTO)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/inactivate")
    @Transactional(timeout = 600)
    fun inactivate(@RequestParam localId: String): ResponseEntity<PersistenceResponse> {
        service.inactivate(localId)
        return ResponseEntity.ok(PersistenceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @PostMapping("/sync")
    @Transactional(timeout = 600)
    fun sync(@RequestBody @Valid storageOperationHistoryDTOS: List<StorageOperationHistoryDTO>): ResponseEntity<MarketServiceResponse> {
        service.sync(storageOperationHistoryDTOS)
        return ResponseEntity.ok(MarketServiceResponse(code = HttpStatus.OK.value(), success = true))
    }

    @GetMapping
    @Transactional(timeout = 600)
    fun findAllStorageOperationsHistoryDTOs(@RequestParam marketId: Long): ResponseEntity<ReadResponse<StorageOperationHistoryDTO>> {
        val values = service.findAllStorageOperationsHistoryDTOs(marketId)
        return ResponseEntity.ok(ReadResponse(values = values, code = HttpStatus.OK.value(), success = true))
    }
}