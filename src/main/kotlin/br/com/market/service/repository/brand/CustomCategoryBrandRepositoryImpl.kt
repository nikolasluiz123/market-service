package br.com.market.service.repository.brand

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.CategoryBrand
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomCategoryBrandRepositoryImpl : ICustomCategoryBrandRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findCategoryBrandByLocalId(localId: String): CategoryBrand? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT c")
            add("FROM ${CategoryBrand::class.java.name} c ")
            add("WHERE c.localId = :pLocalId")
        }

        params.add(Parameter(name = "pLocalId", value = localId))

        val query = entityManager.createQuery(sql.toString(), CategoryBrand::class.java)
        query.setParameters(params)

        return try {
            query.singleResult
        } catch (e: NoResultException) {
            return null
        }
    }

    override fun findCategoryBrands(marketId: Long, limit: Int?, offset: Int?): List<CategoryBrand> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT c")
            add("FROM ${CategoryBrand::class.java.name} c ")
            add("WHERE c.market.id = :pMarketId")
        }

        params.add(Parameter(name = "pMarketId", value = marketId))

        val query = entityManager.createQuery(sql.toString(), CategoryBrand::class.java)
        query.setParameters(params)
        query.maxResults = limit ?: Int.MAX_VALUE
        query.firstResult = offset ?: 0

        return query.resultList
    }

    override fun findCategoryBrandBy(categoryLocalId: String, brandLocalId: String): CategoryBrand {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add(" select cb ")
            add(" from ${CategoryBrand::class.java.name} cb ")
            add(" where cb.category.localId = :pCategoryId ")
            add(" and cb.brand.localId = :pBrandId ")
        }

        params.add(Parameter(name = "pCategoryId", value = categoryLocalId))
        params.add(Parameter(name = "pBrandId", value = brandLocalId))

        val query = entityManager.createQuery(sql.toString(), CategoryBrand::class.java)
        query.setParameters(params)

        return query.singleResult
    }

}