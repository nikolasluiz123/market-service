package br.com.market.service.service

import br.com.market.service.dto.device.DeviceDTO
import br.com.market.service.models.Device
import br.com.market.service.repository.company.ICompanyRepository
import br.com.market.service.repository.device.IDeviceRepository
import org.springframework.stereotype.Service

@Service
class DeviceService(
    private val deviceRepository: IDeviceRepository,
    private val companyRepository: ICompanyRepository
) {

    fun save(deviceDTO: DeviceDTO) {
        with(deviceDTO) {
            val device = if (id != null) {
                deviceRepository.findById(id!!).get().copy(
                    name = name,
                    imei = imei,
                    company = companyRepository.findById(companyId!!).get(),
                    active = active
                )
            } else {
                Device(
                    active = active,
                    company = companyRepository.findById(companyId!!).get(),
                    imei = imei,
                    name = name
                )
            }

            deviceRepository.save(device)
        }
    }

    fun toggleActive(deviceId: Long) {
        deviceRepository.findById(deviceId).get().apply {
            deviceRepository.save(copy(active = !active))
        }
    }

    fun findAll(): List<DeviceDTO> {
        return deviceRepository.findAll().map {
            DeviceDTO(
                id = it.id,
                active = it.active,
                name = it.name,
                imei = it.imei,
                companyId = it.company?.id!!
            )
        }
    }
}