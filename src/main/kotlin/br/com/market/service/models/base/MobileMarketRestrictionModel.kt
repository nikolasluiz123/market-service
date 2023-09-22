package br.com.market.service.models.base

/**
 * Classe abstrata que obriga a exitência dos atributos
 * necessários na base remota que são gerados pelo móvel.
 *
 * @author Nikolas Luiz Schmitt
 */
abstract class MobileMarketRestrictionModel : MarketModel() {
    abstract var localId: String?
}