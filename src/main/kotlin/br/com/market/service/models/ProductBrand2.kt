package br.com.market.service.models

import jakarta.persistence.*
import java.util.*

@Entity(name = "products_brands")
data class ProductBrand2(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var idLocal: UUID? = null,
    @OneToOne
    var product2: Product2 = Product2(),
    @OneToOne
    var brand2: Brand2 = Brand2(),
    var count: Int = 0
)