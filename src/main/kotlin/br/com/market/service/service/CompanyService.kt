package br.com.market.service.service

import br.com.market.service.dto.company.CompanyDTO
import br.com.market.service.dto.theme.ThemeDefinitionsDTO
import br.com.market.service.models.Company
import br.com.market.service.models.ThemeDefinitions
import br.com.market.service.repository.company.CompanyRepository
import br.com.market.service.repository.company.ThemeDefinitionsRepository
import org.springframework.stereotype.Service

@Service
class CompanyService(
    private val companyRepository: CompanyRepository,
    private val themeDefinitionsRepository: ThemeDefinitionsRepository
) {

    fun save(companyDTO: CompanyDTO) {
        lateinit var theme: ThemeDefinitions

        with(companyDTO.themeDefinitionsDTO) {
            theme = themeDefinitionsRepository.save(
                ThemeDefinitions(
                    id = id,
                    active = active,
                    colorPrimary = colorPrimary,
                    colorSecondary = colorSecondary,
                    colorTertiary = colorTertiary,
                    imageLogo = imageLogo
                )
            )
        }

        with(companyDTO) {
            companyRepository.save(Company(id = id, active = active, name = name, themeDefinitions = theme))
        }
    }

    fun findAll(): List<CompanyDTO> {
        return companyRepository.findAll().map {
            CompanyDTO(
                id = it.id,
                name = it.name,
                themeDefinitionsDTO = ThemeDefinitionsDTO(
                    id = it.themeDefinitions?.id,
                    colorPrimary = it.themeDefinitions?.colorPrimary!!,
                    colorSecondary = it.themeDefinitions?.colorSecondary!!,
                    colorTertiary = it.themeDefinitions?.colorTertiary!!,
                    imageLogo = it.themeDefinitions?.imageLogo!!
                )
            )
        }
    }
}