package br.com.market.service.models

import br.com.market.service.models.base.MobileCompanyModel
import br.com.market.service.models.enumeration.EnumPaymentType
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

@Entity(name = "purchases_carts")
data class PurchaseCart(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "company_id")
    override var company: Company? = null,
    @Column(name = "local_id")
    override var localId: String? = null,
    var date: LocalDateTime = LocalDateTime.now(),
    @Column(name = "payment_type")
    var paymentType: EnumPaymentType? = null,
    var delivery: Boolean = false,
    @ManyToOne @JoinColumn(name = "client_id")
    var client: Client? = null
) : MobileCompanyModel()