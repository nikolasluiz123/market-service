package br.com.market.service.repository.category

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Category
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomCategoryRepositoryImpl: ICustomCategoryRepository{

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findCategoryByLocalId(localId: String): Category? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT c")
            add("FROM ${Category::class.java.name} c ")
            add("WHERE c.localId = :pLocalId")
        }

        params.add(Parameter(name = "pLocalId", value = localId))

        val query = entityManager.createQuery(sql.toString(), Category::class.java)
        query.setParameters(params)

        return try {
            query.singleResult
        } catch (e: NoResultException) {
            return null
        }
    }

    override fun findCategories(marketId: Long, limit: Int?, offset: Int?): List<Category> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT c")
            add("FROM ${Category::class.java.name} c ")
            add("WHERE c.market.id = :pMarketId")
        }

        params.add(Parameter(name = "pMarketId", value = marketId))

        val query = entityManager.createQuery(sql.toString(), Category::class.java)
        query.setParameters(params)
        query.maxResults = limit ?: Int.MAX_VALUE
        query.firstResult = offset ?: 0

        return query.resultList
    }
}