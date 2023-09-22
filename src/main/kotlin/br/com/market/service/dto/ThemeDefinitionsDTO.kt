package br.com.market.service.dto

import br.com.market.service.dto.base.BaseDTO

data class ThemeDefinitionsDTO(
    var colorPrimary: String,
    var colorSecondary: String,
    var colorTertiary: String,
    var imageLogo: ByteArray,
    override var id: Long? = null,
    override var active: Boolean = true,
) : BaseDTO() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ThemeDefinitionsDTO

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}