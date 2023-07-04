package br.com.market.service.models

import br.com.market.service.security.enumeration.EnumRole
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "users")
data class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String = "",
    var email: String = "",
    private var password: String = "",
    @Enumerated(EnumType.STRING)
    var role: EnumRole = EnumRole.USER,
    var localId: String? = null,
    var token: String? = null,
    @ManyToOne @JoinColumn(name = "company_id")
    var company: Company? = null,
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String  = password

    fun setPassword(password: String) {
        this.password = password
    }

    override fun getUsername(): String  = email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}