package br.com.market.service.models

import br.com.market.service.models.base.BaseModel
import jakarta.persistence.*

/**
 * Classe que representa a tabela das empresas.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "companies")
data class Company(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    var name: String = "",
    @OneToOne @JoinColumn(name = "theme_definitions_id")
    var themeDefinitions: ThemeDefinitions? = null
): BaseModel()