package br.com.market.service.models

import br.com.market.service.models.base.MobileCompanyModel
import jakarta.persistence.*
import java.util.*

@Entity(name = "categories_brands")
data class CategoryBrand(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override var id: Long? = null,
    override var active: Boolean = true,
    @ManyToOne @JoinColumn(name = "company_id")
    override var company: Company? = null,
    @Column(name = "local_id")
    override var localId: UUID? = null,
    @ManyToOne @JoinColumn(name = "category_id")
    var category: Category? = null,
    @ManyToOne @JoinColumn(name = "brand_id")
    var brand: Brand? = null
) : MobileCompanyModel()