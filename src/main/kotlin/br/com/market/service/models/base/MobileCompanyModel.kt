package br.com.market.service.models.base

abstract class MobileCompanyModel : CompanyModel() {
    abstract var localId: String?
}