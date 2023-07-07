package br.com.market.service.dto.auth

import br.com.market.service.dto.base.MobileDTO

data class UserDTO(
    override var localId: String,
    override var id: Long? = null,
    override var active: Boolean = true,
    var name: String = "",
    var email: String = "",
    var password: String = "",
    var token: String? = null,
    var companyId: Long? = null
): MobileDTO()
