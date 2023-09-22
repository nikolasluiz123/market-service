package br.com.market.service.models

import br.com.market.service.models.base.MobileMarketRestrictionModel
import jakarta.persistence.*
import java.time.LocalDate

/**
 * Classe que representa a tabela dos cartões dos clientes.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "cards")
data class Card(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "market_id")
    override var market: Market? = null,
    @Column(name = "local_id")
    override var localId: String? = null,
    var accaount: String? = null,
    var agency: String? = null,
    var bank: String? = null,
    var validity: LocalDate? = null,
    @ManyToOne @JoinColumn(name = "client_id")
    var client: Client? = null,
) : MobileMarketRestrictionModel()