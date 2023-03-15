package br.com.market.service.repository.brand

import br.com.market.service.dto.brand.BrandView
import br.com.market.service.extensions.getResultList
import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Brand
import br.com.market.service.models.Product
import br.com.market.service.models.ProductBrand
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.persistence.Tuple
import java.util.*

class BrandRepositoryImpl : CustomBrandRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findProductBrands(productId: UUID): List<BrandView> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("select b.id as id,")
            add("       p.name || ' ' || b.name as nomeProduto,")
            add("       pb.count as estoque")
            add("from products p")
            add("inner join products_brands pb on p.id = pb.product_id")
            add("inner join brands b on b.id = pb.brand_id")
            add("where p.id = :pProdutoId")
        }

        params.add(Parameter("pProdutoId", productId))

        val query = entityManager.createNativeQuery(sql.toString(), Tuple::class.java)
        query.setParameters(params)

        return query.getResultList(Tuple::class.java).map { tuple ->
            BrandView(
                id = tuple.get("id") as Long,
                name = tuple.get("nomeProduto") as String,
                count = tuple.get("estoque") as Int
            )
        }
    }

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