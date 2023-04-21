package br.com.market.service.repository.company

import br.com.market.service.models.ThemeDefinitions
import org.springframework.data.jpa.repository.JpaRepository


interface ThemeDefinitionsRepository : JpaRepository<ThemeDefinitions, Long>