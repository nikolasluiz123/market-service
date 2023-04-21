package br.com.market.service.models

import br.com.market.service.models.base.BaseModel
import jakarta.persistence.*

@Entity(name = "theme_definitions")
data class ThemeDefinitions(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @Column(name = "color_primary")
    var colorPrimary: String? = null,
    @Column(name = "color_secondary")
    var colorSecondary: String? = null,
    @Column(name = "color_tertiary")
    var colorTertiary: String? = null,
    @Column(name = "image_logo")
    var imageLogo: ByteArray? = null,
) : BaseModel() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ThemeDefinitions

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}