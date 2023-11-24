package br.com.market.service.dto

class AuthenticationResultDTO(
    var company: CompanyDTO,
    var market: MarketDTO,
    var device: DeviceDTO,
    var user: UserDTO,
    var token: String,
    var userLocalId: String,
)