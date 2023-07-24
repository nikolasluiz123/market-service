package br.com.market.service.service

import br.com.market.service.dto.device.DeviceDTO
import br.com.market.service.models.Device
import br.com.market.service.repository.device.IDeviceRepository
import br.com.market.service.repository.market.IMarketRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class DeviceService(
    private val deviceRepository: IDeviceRepository,
    private val marketRepository: IMarketRepository
) {

    fun save(deviceDTO: DeviceDTO) {
        with(deviceDTO) {
            val device = deviceRepository.findById(id!!).getOrNull()?.copy(
                id = id,
                name = name,
                market = marketRepository.findById(marketId!!).get(),
                active = active
            ) ?: Device(
                id = id,
                active = active,
                market = marketRepository.findById(marketId!!).get(),
                name = name
            )

            deviceRepository.save(device)
        }
    }

    fun toggleActive(deviceId: String) {
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
                marketId = it.market?.id!!
            )
        }
    }

    fun findById(id: String): DeviceDTO? {
        return deviceRepository.findById(id).getOrNull()?.run {
            DeviceDTO(
                id = id,
                active = active,
                name = name,
                marketId = market?.id!!
            )
        }
    }
}