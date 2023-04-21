package br.com.market.service.models.base

import br.com.market.service.models.Company

abstract class CompanyModel : BaseModel() {
    abstract var company: Company?
}