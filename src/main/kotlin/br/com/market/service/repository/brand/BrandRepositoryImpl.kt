package br.com.market.service.repository.brand

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Brand
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import java.util.*

class BrandRepositoryImpl : ICustomBrandRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findBrandByLocalId(localId: UUID): Brand? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT c")
            add("FROM ${Brand::class.java.name} c ")
            add("WHERE c.idLocal = :pIdLocal")
        }

        params.add(Parameter(name = "pIdLocal", value = localId))

        val query = entityManager.createQuery(sql.toString(), Brand::class.java)
        query.setParameters(params)

        return try {
            query.singleResult
        } catch (e: NoResultException) {
            return null
        }
    }

}