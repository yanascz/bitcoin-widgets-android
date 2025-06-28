package cz.yanas.bitcoin.widgets

import android.util.Log
import cz.yanas.bitcoin.mempool.MempoolClient

object MempoolStatusProvider {

    private val mempoolClient = MempoolClient()

    fun getMempoolStatus(): MempoolStatus? {
        try {
            return doGetMempoolStatus()
        } catch (throwable: Throwable) {
            Log.e("MempoolStatusProvider", "Mempool status not available", throwable)
            return null
        }
    }

    private fun doGetMempoolStatus(): MempoolStatus {
        val blockHeight = mempoolClient.getBlockHeight()
        val recommendedFees = mempoolClient.getRecommendedFees()

        return MempoolStatus(
            blockHeight = blockHeight,
            fastestFee = recommendedFees.fastestFee,
            halfHourFee = recommendedFees.halfHourFee,
            hourFee = recommendedFees.hourFee,
            economyFee = recommendedFees.economyFee,
            minimumFee = recommendedFees.minimumFee
        )
    }

}
