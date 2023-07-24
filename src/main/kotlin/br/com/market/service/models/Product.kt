package br.com.market.service.models

import br.com.market.service.models.base.MobileMarketModel
import br.com.market.service.models.enumeration.EnumUnit
import jakarta.persistence.*

/**
 * Classe que representa a tabela dos produtos.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "products")
data class Product(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "market_id")
    override var market: Market? = null,
    @Column(name = "local_id")
    override var localId: String? = null,
    var name: String? = null,
    var price: Double = 0.0,
    var quantity: Double = 0.0,
    @Column(name = "quantity_unit")
    var quantityUnit: EnumUnit? = null,
    @ManyToOne @JoinColumn(name = "category_brand_id")
    var categoryBrand: CategoryBrand? = null
): MobileMarketModel()