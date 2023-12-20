package br.com.market.service.repository.storageoperationshistory

import br.com.market.service.controller.params.StorageOperationsHistoryServiceSearchParams
import br.com.market.service.dto.StorageOperationHistoryDTO
import br.com.market.service.extensions.setParameters
import br.com.market.service.models.StorageOperationHistory
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import jakarta.persistence.Tuple
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

    override fun findAll(marketId: Long, limit: Int?, offset: Int?): List<StorageOperationHistory> {
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
        query.maxResults = limit ?: Int.MAX_VALUE
        query.firstResult = offset ?: 0

        return query.resultList
    }

    override fun getListStorageOperations(params: StorageOperationsHistoryServiceSearchParams): List<StorageOperationHistoryDTO> {
        val queryParams = mutableListOf<Parameter>()

        val select = StringJoiner("\n\t")

        with(select) {
            add(" select op.local_id as localId, ")
            add("        op.date_realization as dateRealization, ")
            add("        op.date_prevision as datePrevision, ")
            add("        op.operation_type as operationType, ")
            add("        op.description as description, ")
            add("        op.quantity as quantity, ")
            add("        p.local_id as productId, ")
            add("        u.local_id as userId ")
        }

        val from = StringJoiner("\n\t")

        with(from) {
            add(" from storage_operations_history op ")
            add(" inner join products p on p.id = op.product_id ")
            add(" inner join categories_brands cb on cb.id = p.category_brand_id ")
            add(" inner join brands b on b.id = cb.brand_id ")
            add(" inner join categories c on c.id = cb.category_id ")
            add(" inner join users u on u.id = op.user_id ")
        }

        val where = StringJoiner("\n\t")

        with(where) {
            add(" where op.active ")
            add(" and c.local_id = :pCategoryId ")
            add(" and b.local_id = :pBrandId ")

            queryParams.add(Parameter("pCategoryId", params.filters.categoryId))
            queryParams.add(Parameter("pBrandId", params.filters.brandId))

            if (!params.filters.productId.isNullOrBlank()) {
                add(" and p.local_id = :pProductId ")
                queryParams.add(Parameter("pProductId", params.filters.productId!!))
            }

            if (!params.filters.quickFilter.isNullOrBlank()) {
                add(" and ( ")
                add("       p.name ilike :pQuickFilter or ")
                add("       u.name ilike :pQuickFilter or ")
                add("       op.description ilike :pQuickFilter ")
                add("     ) ")

                queryParams.add(Parameter("pQuickFilter", "%${params.filters.quickFilter}%"))
            }

            if (params.filters.productName.isFilterApplied()) {
                add(" and p.name ilike :pProductName ")
                queryParams.add(Parameter("pProductName","%${params.filters.productName.value}%"))
            }

            if (params.filters.description.isFilterApplied()) {
                add(" and op.description like :pDescription ")
                queryParams.add(Parameter("pDescription", "%${params.filters.description.value}%"))
            }

            if (params.filters.datePrevision.isFilterApplied()) {
                val dateFrom = params.filters.datePrevision.value?.first?.toString()
                val dateTo = params.filters.datePrevision.value?.second?.toString()

                when {
                    dateFrom != null && dateTo != null -> {
                        add(" and op.date_prevision between :pDateFrom and :pDateTo ")
                        queryParams.add(Parameter("pDateFrom", dateFrom))
                        queryParams.add(Parameter("pDateTo", dateTo))
                    }
                    dateFrom != null -> {
                        add(" and op.date_prevision >= :pDateFrom ")
                        queryParams.add(Parameter("pDateFrom", dateFrom))
                    }
                    dateTo != null -> {
                        add(" and op.date_prevision <= :pDateTo ")
                        queryParams.add(Parameter("pDateTo", dateTo))
                    }
                }
            }

            if (params.filters.dateRealization.isFilterApplied()) {
                val dateFrom = params.filters.dateRealization.value?.first?.toString()
                val dateTo = params.filters.dateRealization.value?.second?.toString()

                when {
                    dateFrom != null && dateTo != null -> {
                        add(" and op.date_realization between :pDateFrom and :pDateTo ")
                        queryParams.add(Parameter("pDateFrom", dateFrom))
                        queryParams.add(Parameter("pDateTo", dateTo))
                    }
                    dateFrom != null -> {
                        add(" and op.date_realization >= :pDateFrom ")
                        queryParams.add(Parameter("pDateFrom", dateFrom))
                    }
                    dateTo != null -> {
                        add(" and op.date_realization <= :pDateTo ")
                        queryParams.add(Parameter("pDateTo", dateTo))
                    }
                }
            }

            if (params.filters.operationType.isFilterApplied()) {
                add(" and op.operation_type = :pOperationType ")
                queryParams.add(Parameter("pOperationType", params.filters.operationType.value!!))
            }

            if (params.filters.quantity.isFilterApplied()) {
                add(" and op.quantity = :pQuantity ")
                queryParams.add(Parameter("pQuantity", params.filters.quantity.value!!))
            }

            if (params.filters.responsible.isFilterApplied()) {
                add(" and u.id = :pUserId ")
                queryParams.add(Parameter("pUserId", params.filters.responsible.value?.second!!))
            }
        }

        val sql = StringJoiner("\r\n")
        with(sql) {
            add(select.toString())
            add(from.toString())
            add(where.toString())
        }

        val query = entityManager.createNativeQuery(sql.toString(), Tuple::class.java)
        query.setParameters(queryParams)
    }
}