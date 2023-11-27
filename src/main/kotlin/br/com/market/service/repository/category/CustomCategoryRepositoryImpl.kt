package br.com.market.service.repository.category

import br.com.market.service.dto.CategoryDTO
import br.com.market.service.extensions.getResultList
import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Category
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import jakarta.persistence.Tuple
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomCategoryRepositoryImpl : ICustomCategoryRepository {

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

    override fun getListCategory(simpleFilter: String?, marketId: Long, limit: Int, offset: Int): List<CategoryDTO> {
        val params = mutableListOf<Parameter>()
        val select = StringJoiner("\n\t")

        with(select) {
            add(" select c.id as categoryId, ")
            add("        c.local_id as categoryLocalId, ")
            add("        c.name as categoryName, ")
            add("        c.active as categoryActive, ")
            add("        c.market_id as marketId ")
        }

        val from = StringJoiner("\n\t")

        with(from) {
            add(" from categories c ")
        }

        val where = StringJoiner("\n\t")

        with(where) {
            add(" where c.active ")
            add(" and c.market_id = :pMarketId ")

            params.add(Parameter("pMarketId", marketId))

            if (!simpleFilter.isNullOrEmpty()) {
                add(" and c.name like :pSimpleFilter ")
                params.add(Parameter("pSimpleFilter", "%${simpleFilter}%"))
            }
        }

        val orderBy = StringJoiner("\n\t")

        with(orderBy) {
            add(" order by c.name ")
        }

        val sql = StringJoiner("\r\n")
        with(sql) {
            add(select.toString())
            add(from.toString())
            add(where.toString())
            add(orderBy.toString())
            add("limit :pLimit offset :pOffset ")

            params.add(Parameter("pLimit", limit))
            params.add(Parameter("pOffset", offset))
        }

        val query = entityManager.createNativeQuery(sql.toString(), Tuple::class.java)
        query.setParameters(params)

        return query.getResultList(Tuple::class.java).map { tuple ->
            CategoryDTO(
                id = tuple.get("categoryId", Long::class.javaObjectType),
                name = tuple.get("categoryName", String::class.javaObjectType),
                localId = tuple.get("categoryLocalId", String::class.javaObjectType),
                marketId = tuple.get("marketId", Long::class.javaObjectType),
                active = tuple.get("categoryActive", Boolean::class.javaObjectType),
            )
        }
    }
}