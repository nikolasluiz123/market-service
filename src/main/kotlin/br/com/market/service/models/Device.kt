package br.com.market.service.models

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

@Entity(name = "devices")
data class Device(
    @Id
    var id: String? = null,
    var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "company_id")
    var company: Company? = null,
    var name: String? = null,
)
