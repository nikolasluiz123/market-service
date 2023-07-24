package br.com.market.service.repository.storageoperationshistory

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.StorageOperationHistory
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomStorageOperationsHistoryRepositoryImpl: ICustomStorageOperationsHistoryRepository{

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findStorageOperationHistoryByLocalId(localId: String): StorageOperationHistory? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT s")
            add("FROM ${StorageOperationHistory::class.java.name} s ")
            add("WHERE s.localId = :pLocalId")
        }

        params.add(Parameter(name = "pLocalId", value = localId))

        val query = entityManager.createQuery(sql.toString(), StorageOperationHistory::class.java)
        query.setParameters(params)

        return try {
            query.singleResult
        } catch (e: NoResultException) {
            return null
        }
    }

    override fun findAll(marketId: Long): List<StorageOperationHistory> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT s")
            add("FROM ${StorageOperationHistory::class.java.name} s ")
            add("WHERE s.market.id = :pMarketId")
        }

        params.add(Parameter(name = "pMarketId", value = marketId))

        val query = entityManager.createQuery(sql.toString(), StorageOperationHistory::class.java)
        query.setParameters(params)

        return query.resultList
    }
}