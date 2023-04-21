package br.com.market.service.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.util.*

@Entity(name = "products2")
data class Product2(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var idLocal: UUID? = null,
    var name: String = "",
    var imageUrl: String = ""
)