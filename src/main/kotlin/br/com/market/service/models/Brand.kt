package br.com.market.service.models

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Brand(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var name: String = ""
)