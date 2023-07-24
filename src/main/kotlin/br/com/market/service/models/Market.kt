package br.com.market.service.models

import br.com.market.service.models.base.BaseModel
import jakarta.persistence.*

/**
 * Classe que representa a tabela dos mercados da empresa.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "markets")
data class Market(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "company_id")
    var company: Company? = null,
    @OneToOne @JoinColumn(name = "addres_id")
    var address: Address? = null,
    var name: String? = null
) : BaseModel()
