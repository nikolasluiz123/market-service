package br.com.market.service.models

import br.com.market.service.models.base.MobileMarketModel
import jakarta.persistence.*

/**
 * Classe que representa a tabela das marcas.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "brands")
data class Brand(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "market_id")
    override var market: Market? = null,
    @Column(name = "local_id")
    override var localId: String? = null,
    var name: String? = null
) : MobileMarketModel()