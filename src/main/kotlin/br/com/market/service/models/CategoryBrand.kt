package br.com.market.service.models

import br.com.market.service.models.base.MobileMarketRestrictionModel
import jakarta.persistence.*

/**
 * Classe que representa a tabela intermediária
 * das categorias e marcas.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "categories_brands")
data class CategoryBrand(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "market_id")
    override var market: Market? = null,
    @Column(name = "local_id")
    override var localId: String? = null,
    @ManyToOne @JoinColumn(name = "category_id")
    var category: Category? = null,
    @ManyToOne @JoinColumn(name = "brand_id")
    var brand: Brand? = null
) : MobileMarketRestrictionModel()