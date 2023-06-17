package br.com.market.service.repository.user

import br.com.market.service.models.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface IUserRepository : JpaRepository<User, Long> {

    fun findByEmail(email: String): Optional<User>
}