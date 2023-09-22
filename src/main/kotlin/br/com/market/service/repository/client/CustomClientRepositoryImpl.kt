package br.com.market.service.repository.client

import br.com.market.service.extensions.setParameters
import br.com.market.service.models.Client
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomClientRepositoryImpl : ICustomClientRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findClientByLocalId(localId: String): Client? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT c")
            add("FROM ${Client::class.java.name} c ")
            add("WHERE c.localId = :pLocalId")
        }

        params.add(Parameter(name = "pLocalId", value = localId))

        val query = entityManager.createQuery(sql.toString(), Client::class.java)
        query.setParameters(params)

        return try {
            query.singleResult
        } catch (e: NoResultException) {
            return null
        }
    }

    override fun isUniqueCPF(cpf: String): Boolean {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add(" SELECT EXISTS ( ")
            add("                   SELECT 1 ")
            add("                   FROM ${Client::class.java.name} c ")
            add("                   WHERE c.cpf = :pCPF ")
            add("               ) as unique ")
        }

        params.add(Parameter(name = "pCPF", value = cpf))

        val query = entityManager.createQuery(sql.toString(), Boolean::class.java)
        query.setParameters(params)

        return try {
            !query.singleResult
        } catch (e: NoResultException) {
            return true
        }
    }

}