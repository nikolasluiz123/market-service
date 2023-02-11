package br.com.market.service.dto.user

data class NewUserDTO(
    var id: Long? = null,
    var name: String,
    var email: String,
    var password: String,
    var roles: List<NewRoleDTO>
)