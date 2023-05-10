package br.com.market.service.repository.product

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Product
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import java.util.*

class ProductRepositoryImpl : ICustomProductRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findProductByLocalId(localId: UUID): Product? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT p")
            add("FROM ${Product::class.java.name} p ")
            add("WHERE p.idLocal = :pIdLocal")
        }

        params.add(Parameter(name = "pIdLocal", value = localId))

        val query = entityManager.createQuery(sql.toString(), Product::class.java)
        query.setParameters(params)

        return try {
            query.singleResult
        } catch (e: NoResultException) {
            return null
        }
    }

}