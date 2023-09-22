package br.com.market.service.service

import br.com.market.service.dto.CompanyDTO
import br.com.market.service.dto.ThemeDefinitionsDTO
import br.com.market.service.models.Company
import br.com.market.service.models.ThemeDefinitions
import br.com.market.service.repository.company.ICompanyRepository
import br.com.market.service.repository.company.ICustomCompanyRepository
import br.com.market.service.repository.company.ThemeDefinitionsRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class CompanyService(
    private val companyRepository: ICompanyRepository,
    private val customCompanyRepository: ICustomCompanyRepository,
    private val themeDefinitionsRepository: ThemeDefinitionsRepository
) {

    fun save(companyDTO: CompanyDTO) {
        lateinit var theme: ThemeDefinitions

        with(companyDTO.themeDefinitionsDTO) {
            theme = if (id != null) {
                themeDefinitionsRepository.findById(id!!).get()
            } else {
                ThemeDefinitions(
                    active = active,
                    colorPrimary = colorPrimary,
                    colorSecondary = colorSecondary,
                    colorTertiary = colorTertiary,
                    imageLogo = imageLogo
                )
            }

            theme = themeDefinitionsRepository.save(theme)
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
                ),
                active = it.active
            )
        }
    }

    fun toggleActive(companyId: Long) {
        companyRepository.findById(companyId).getOrNull()?.let {
            companyRepository.save(it.copy(active = !it.active))

            val themeDefinitions = themeDefinitionsRepository.findById(it.themeDefinitions?.id!!).get().copy(active = !it.themeDefinitions!!.active)
            themeDefinitionsRepository.save(themeDefinitions)
        }
    }

    fun findByDeviceId(deviceId: String): CompanyDTO {
        return customCompanyRepository.findByDeviceId(deviceId)!!.run {
            CompanyDTO(
                id = id,
                name = name,
                themeDefinitionsDTO = ThemeDefinitionsDTO(
                    id = themeDefinitions?.id,
                    colorPrimary = themeDefinitions?.colorPrimary!!,
                    colorSecondary = themeDefinitions?.colorSecondary!!,
                    colorTertiary = themeDefinitions?.colorTertiary!!,
                    imageLogo = themeDefinitions?.imageLogo!!
                ),
                active = active
            )
        }
    }
}