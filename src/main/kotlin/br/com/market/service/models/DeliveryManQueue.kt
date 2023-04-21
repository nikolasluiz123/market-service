package br.com.market.service.models

import br.com.market.service.models.base.MobileCompanyModel
import jakarta.persistence.*
import java.util.*

@Entity(name = "delivery_mans_queue")
data class DeliveryManQueue(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "company_id")
    override var company: Company? = null,
    @Column(name = "local_id")
    override var localId: UUID? = null,
    @Column(name = "delivery_started")
    var deliveryStarted: Boolean = false,
    @OneToOne @PrimaryKeyJoinColumn(name = "purchase_cart_id")
    var purchaseCart: PurchaseCart? = null
): MobileCompanyModel()