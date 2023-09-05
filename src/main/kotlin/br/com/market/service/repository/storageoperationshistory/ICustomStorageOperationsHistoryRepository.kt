package br.com.market.service.repository.storageoperationshistory

import br.com.market.service.models.StorageOperationHistory

interface ICustomStorageOperationsHistoryRepository {

    fun findStorageOperationHistoryByLocalId(localId: String): StorageOperationHistory?

    fun findAll(marketId: Long, limit: Int? = null, offset: Int? = null): List<StorageOperationHistory>

}