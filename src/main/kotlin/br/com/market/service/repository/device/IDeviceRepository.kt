package br.com.market.service.repository.device

import br.com.market.service.models.Device
import org.springframework.data.jpa.repository.JpaRepository

interface IDeviceRepository : JpaRepository<Device, Long>