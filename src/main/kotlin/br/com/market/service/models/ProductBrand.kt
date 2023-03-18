package br.com.market.service.models

import jakarta.persistence.*
import java.util.*

@Entity(name = "products_brands")
data class ProductBrand(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var idLocal: UUID? = null,
    @OneToOne
    var product: Product = Product(),
    @OneToOne
    var brand: Brand = Brand(),
    var count: Int = 0
)