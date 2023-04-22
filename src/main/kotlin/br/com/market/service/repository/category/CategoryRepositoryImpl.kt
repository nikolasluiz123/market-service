package br.com.market.service.repository.category

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Category
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import java.util.*

class CategoryRepositoryImpl: ICustomCategoryRepository{

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findCategoryByLocalId(localId: UUID): Category? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT c")
            add("FROM ${Category::class.java.name} c ")
            add("WHERE c.idLocal = :pIdLocal")
        }

        params.add(Parameter(name = "pIdLocal", value = localId))

        val query = entityManager.createQuery(sql.toString(), Category::class.java)
        query.setParameters(params)

        return try {
            query.singleResult
        } catch (e: NoResultException) {
            return null
        }
    }
}