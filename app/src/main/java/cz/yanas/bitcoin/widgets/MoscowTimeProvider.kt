package cz.yanas.bitcoin.widgets

import cz.yanas.bitcoin.blockchain.BlockchainClient

object MoscowTimeProvider {

    private val blockchainClient = BlockchainClient()

    fun getMoscowTime(): MoscowTime {
        val tickers = blockchainClient.getTickers()
        val primaryCurrencyCode = "USD"
        val secondaryCurrencyCode = "EUR"

        return MoscowTime(
            primaryPrice = tickers[primaryCurrencyCode]!!.last,
            primaryCurrencyCode = primaryCurrencyCode,
            secondaryPrice = tickers[secondaryCurrencyCode]!!.last,
            secondaryCurrencyCode = secondaryCurrencyCode,
        )
    }

}
