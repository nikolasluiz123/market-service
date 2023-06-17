package br.com.market.service.repository.storageoperationshistory

import br.com.market.service.models.StorageOperationHistory
import org.springframework.data.jpa.repository.JpaRepository

interface IStorageOperationsHistoryRepository: JpaRepository<StorageOperationHistory, Long>