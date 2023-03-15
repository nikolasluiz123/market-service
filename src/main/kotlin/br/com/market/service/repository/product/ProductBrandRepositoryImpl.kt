package br.com.market.service.repository.product

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Product
import br.com.market.service.models.ProductBrand
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import java.util.*

class ProductBrandRepositoryImpl : CustomProductBrandRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findByLocalProductId(localProductId: UUID): List<ProductBrand> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT p")
            add("FROM ${ProductBrand::class.java.name} p ")
            add("WHERE p.product.idLocal = :pIdLocal")
        }

        params.add(Parameter(name = "pIdLocal", value = localProductId))

        val query = entityManager.createQuery(sql.toString(), ProductBrand::class.java)
        query.setParameters(params)

        return query.resultList
    }

    override fun findByLocalBrandId(localBrandId: UUID): Optional<ProductBrand> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT pb")
            add("FROM ${ProductBrand::class.java.name} pb ")
            add("WHERE pb.brand.idLocal = :pIdLocal")
        }

        params.add(Parameter(name = "pIdLocal", value = localBrandId))

        val query = entityManager.createQuery(sql.toString(), ProductBrand::class.java)
        query.setParameters(params)

        return try {
            Optional.of(query.singleResult)
        } catch (e: Exception) {
            Optional.empty<ProductBrand>()
        }
    }

}