package br.com.market.service.repository.address

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Address
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomAddressRepositoryImpl: ICustomAddressRepository{

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findAddressByLocalId(localId: String): Address? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT a")
            add("FROM ${Address::class.java.name} a ")
            add("WHERE a.localId = :pLocalId")
        }

        params.add(Parameter(name = "pLocalId", value = localId))

        val query = entityManager.createQuery(sql.toString(), Address::class.java)
        query.setParameters(params)

        return try {
            query.singleResult
        } catch (e: NoResultException) {
            return null
        }
    }
}