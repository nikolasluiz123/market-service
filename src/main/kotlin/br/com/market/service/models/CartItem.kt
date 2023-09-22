package br.com.market.service.models

import br.com.market.service.models.base.MobileMarketRestrictionModel
import jakarta.persistence.*

/**
 * Classe que representa a tabela dos itens contidos no carrinho de compras.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "carts_items")
data class CartItem(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "market_id")
    override var market: Market? = null,
    @Column(name = "local_id")
    override var localId: String? = null,
    var quantity: Int = 0,
    @ManyToOne @JoinColumn(name = "product_id")
    var product: Product? = null,
    @ManyToOne @JoinColumn(name = "purchase_cart_id")
    var purchaseCart: PurchaseCart
): MobileMarketRestrictionModel()