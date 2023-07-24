package br.com.market.service.models

import br.com.market.service.models.base.MobileMarketModel
import jakarta.persistence.*

/**
 * Classe que representa a tabela das imagens dos produtos.
 *
 * @author Nikolas Luiz Schmitt
 */
@Entity(name = "products_images")
data class ProductImage(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "market_id")
    override var market: Market? = null,
    @Column(name = "local_id")
    override var localId: String? = null,
    var bytes: ByteArray? = null,
    var imageUrl: String? = null,
    @ManyToOne @JoinColumn(name = "product_id")
    var product: Product? = null,
    var principal: Boolean? = false
) : MobileMarketModel() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ProductImage

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}