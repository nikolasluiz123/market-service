package br.com.market.service.repository.company

import br.com.market.service.models.Company
import org.springframework.data.jpa.repository.JpaRepository


interface ICompanyRepository : JpaRepository<Company, Long>