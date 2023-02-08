package br.com.market.service.repository

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.ProductBrand
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import java.util.StringJoiner

class ProductBrandRepositoryImpl : CustomProductBrandRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findByProductId(id: Long): List<ProductBrand> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT p")
            add("FROM ${ProductBrand::class.java.name} p ")
            add("WHERE p.product.id = :pProductId")
        }

        params.add(Parameter(name = "pProductId", value = id))

        val query = entityManager.createQuery(sql.toString(), ProductBrand::class.java)
        query.setParameters(params)

        return query.resultList
    }
}