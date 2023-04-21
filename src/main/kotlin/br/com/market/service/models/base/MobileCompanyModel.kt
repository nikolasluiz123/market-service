package br.com.market.service.models.base

import java.util.*

abstract class MobileCompanyModel : CompanyModel() {
    abstract var localId: UUID?
}