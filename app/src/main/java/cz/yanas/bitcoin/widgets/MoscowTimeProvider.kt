package cz.yanas.bitcoin.widgets

import android.util.Log
import cz.yanas.bitcoin.blockchain.BlockchainClient

object MoscowTimeProvider {

    private val blockchainClient = BlockchainClient()

    fun getMoscowTime(): MoscowTime? {
        try {
            return doGetMoscowTime()
        } catch (throwable: Throwable) {
            Log.e("MoscowTimeProvider", "Moscow time not available", throwable)
            return null
        }
    }

    private fun doGetMoscowTime(): MoscowTime {
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
