package br.com.market.service.models

import br.com.market.service.models.base.BaseModel
import jakarta.persistence.*

@Entity(name = "companies")
data class Company(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    var name: String = "",
    @OneToOne @PrimaryKeyJoinColumn(name = "theme_definitions_id")
    var themeDefinitions: ThemeDefinitions? = null
): BaseModel()