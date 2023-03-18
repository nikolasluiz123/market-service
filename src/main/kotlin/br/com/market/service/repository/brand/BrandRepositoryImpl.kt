package br.com.market.service.repository.brand

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Brand
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import java.util.*

class BrandRepositoryImpl : CustomBrandRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findBrandByLocalId(localBrandId: UUID): Optional<Brand> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("select b")
            add("from ${Brand::class.java.name} b")
            add("where b.idLocal = :pBrandId")
        }

        params.add(Parameter("pBrandId", localBrandId))

        val query = entityManager.createQuery(sql.toString(), Brand::class.java)
        query.setParameters(params)

        return try {
            Optional.of(query.singleResult)
        } catch (e: Exception) {
            Optional.empty<Brand>()
        }
    }

}