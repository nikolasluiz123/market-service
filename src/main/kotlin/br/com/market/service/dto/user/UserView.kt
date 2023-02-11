package br.com.market.service.dto.user

data class UserView(
    var id: Long?,
    var name: String,
    var email: String,
    var password: String,
    var roles: List<RoleView>
)