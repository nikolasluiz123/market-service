package br.com.market.service.repository.storageoperationshistory

import br.com.market.service.models.StorageOperationHistory

interface ICustomStorageOperationsHistoryRepository {

    fun findStorageOperationHistoryByLocalId(localId: String): StorageOperationHistory?

}