package br.com.market.service.models.base

import br.com.market.service.models.Market

/**
 * Classe abstrata que obriga a existência da
 * referência para o [Market] ao qual o dado
 * pertence.
 *
 * @author Nikolas Luiz Schmitt
 */
abstract class MarketModel : BaseModel() {
    abstract var market: Market?
}