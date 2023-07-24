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

    override fun findCategoryBrands(marketId: Long): List<CategoryBrand> {
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

        return query.resultList
    }

}