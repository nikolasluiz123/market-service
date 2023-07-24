package br.com.market.service.models

import br.com.market.service.models.base.MobileMarketModel
import jakarta.persistence.*
import java.time.LocalDateTime

/**
 * Classe que representa a tabela das avaliações dos produtos.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "products_ratings")
data class ProductRating(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "market_id")
    override var market: Market? = null,
    @Column(name = "local_id")
    override var localId: String? = null,
    var rating: Double = 0.0,
    var comment: String? = null,
    var date: LocalDateTime = LocalDateTime.now(),
    @ManyToOne @JoinColumn(name = "product_id")
    var product: Product? = null,
    @ManyToOne @JoinColumn(name = "client_id")
    var client: Client? = null
) : MobileMarketModel()