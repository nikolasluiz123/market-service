package br.com.market.service.repository.user

import br.com.market.service.dto.filter.UserFiltersDTO
import br.com.market.service.extensions.setParameters
import br.com.market.service.models.User
import br.com.market.service.query.Parameter
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import jakarta.persistence.PersistenceContext
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class CustomUserRepositoryImpl: ICustomUserRepository {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    override fun findUserByLocalId(localId: String): User? {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT u")
            add("FROM ${User::class.java.name} u ")
            add("WHERE u.localId = :pLocalId")
        }

        params.add(Parameter(name = "pLocalId", value = localId))

        val query = entityManager.createQuery(sql.toString(), User::class.java)
        query.setParameters(params)

        return try {
            query.singleResult
        } catch (e: NoResultException) {
            return null
        }
    }

    override fun findAll(userFiltersDTO: UserFiltersDTO): List<User> {
        val params = mutableListOf<Parameter>()
        val sql = StringJoiner("\n\t")

        with(sql) {
            add("SELECT u")
            add("FROM ${User::class.java.name} u ")
            add("WHERE u.company.id = :pCompanyId")
        }

        params.add(Parameter(name = "pCompanyId", value = userFiltersDTO.companyId))

        val query = entityManager.createQuery(sql.toString(), User::class.java)
        query.setParameters(params)

        return query.resultList
    }
}