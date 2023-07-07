package br.com.market.service.repository.user

import br.com.market.service.dto.filter.UserFiltersDTO
import br.com.market.service.models.User

interface ICustomUserRepository {

    fun findUserByLocalId(localId: String): User?

    fun findAll(userFiltersDTO: UserFiltersDTO): List<User>
}