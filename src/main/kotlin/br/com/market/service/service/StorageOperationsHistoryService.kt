package br.com.market.service.service

import br.com.market.service.controller.params.StorageOperationsHistoryServiceSearchParams
import br.com.market.service.dto.StorageOperationHistoryDTO
import br.com.market.service.models.StorageOperationHistory
import br.com.market.service.repository.market.IMarketRepository
import br.com.market.service.repository.product.ICustomProductRepository
import br.com.market.service.repository.storageoperationshistory.ICustomStorageOperationsHistoryRepository
import br.com.market.service.repository.storageoperationshistory.IStorageOperationsHistoryRepository
import br.com.market.service.repository.user.ICustomUserRepository
import org.springframework.stereotype.Service

@Service
class StorageOperationsHistoryService(
    private val storageOperationsHistoryRepository: IStorageOperationsHistoryRepository,
    private val customStorageOperationsHistoryRepository: ICustomStorageOperationsHistoryRepository,
    private val customProductRepository: ICustomProductRepository,
    private val customUserRepository: ICustomUserRepository,
    private val marketRepository: IMarketRepository
) {

    fun save(dto: StorageOperationHistoryDTO) {
        with(dto) {
            val storageOperationHistory = customStorageOperationsHistoryRepository.findStorageOperationHistoryByLocalId(localId)?.copy(
                product = customProductRepository.findProductByLocalId(dto.productId!!),
                dateRealization = dto.dateRealization,
                datePrevision = dto.datePrevision,
                operationType = dto.operationType,
                description = dto.description,
                user = customUserRepository.findUserByLocalId(dto.userId!!),
                active = dto.active,
                localId = dto.localId,
                quantity = dto.quantity
            ) ?: StorageOperationHistory(
                product = customProductRepository.findProductByLocalId(dto.productId!!),
                dateRealization = dto.dateRealization,
                datePrevision = dto.datePrevision,
                operationType = dto.operationType,
                description = dto.description,
                user = customUserRepository.findUserByLocalId(dto.userId!!),
                active = dto.active,
                localId = dto.localId,
                quantity = dto.quantity,
                market = marketRepository.findById(dto.marketId!!).get()
            )

            storageOperationsHistoryRepository.save(storageOperationHistory)
        }
    }

    fun sync(storageOperationHistoryDTOS: List<StorageOperationHistoryDTO>) {
        storageOperationHistoryDTOS.forEach(::save)
    }

    fun findStorageOperationsHistoryDTOs(marketId: Long, limit: Int? = null, offset: Int? = null): List<StorageOperationHistoryDTO> {
        return customStorageOperationsHistoryRepository.findAll(marketId, limit, offset).map {
            StorageOperationHistoryDTO(
                localId = it.localId!!,
                id = it.id,
                active = it.active,
                productId = it.product?.localId,
                quantity = it.quantity!!,
                datePrevision = it.datePrevision,
                dateRealization = it.dateRealization,
                operationType = it.operationType,
                description = it.description,
                userId = it.user?.localId,
                marketId = it.market?.id
            )
        }
    }

    fun inactivate(localId: String) {
        customStorageOperationsHistoryRepository.findStorageOperationHistoryByLocalId(localId)?.let {
            storageOperationsHistoryRepository.save(it.copy(active = false))
        }
    }

    fun getListStorageOperations(params: StorageOperationsHistoryServiceSearchParams): List<StorageOperationHistoryDTO> {
        return customStorageOperationsHistoryRepository.getListStorageOperations(params)
    }

}