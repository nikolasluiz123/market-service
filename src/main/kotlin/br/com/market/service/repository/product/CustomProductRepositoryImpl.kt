package br.com.market.service.repository.product

import br.com.market.service.dto.filter.ProductFiltersDTO
import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Product
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomProductRepositoryImpl : ICustomProductRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findProductByLocalId(localId: String): Product? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT p")
            add("FROM ${Product::class.java.name} p ")
            add("WHERE p.localId = :pLocalId")
        }

        params.add(Parameter(name = "pLocalId", value = localId))

        val query = entityManager.createQuery(sql.toString(), Product::class.java)
        query.setParameters(params)

        return try {
            query.singleResult
        } catch (e: NoResultException) {
            return null
        }
    }

    override fun findAll(productFiltersDTO: ProductFiltersDTO): List<Product> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT p")
            add("FROM ${Product::class.java.name} p ")
            add("WHERE p.company.id = :pCompanyId")
        }

        params.add(Parameter(name = "pCompanyId", value = productFiltersDTO.companyId))

        val query = entityManager.createQuery(sql.toString(), Product::class.java)
        query.setParameters(params)

        return query.resultList
    }

}