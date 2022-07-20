package cz.yanas.bitcoin.widgets

import cz.yanas.bitcoin.mempool.MempoolClient

object MempoolStatusProvider {

    private val mempoolClient = MempoolClient()

    fun getMempoolStatus(): MempoolStatus {
        val blockHeight = mempoolClient.getBlockHeight()
        val recommendedFees = mempoolClient.getRecommendedFees()

        return MempoolStatus(
            blockHeight = blockHeight,
            fastestFee = recommendedFees.fastestFee,
            halfHourFee = recommendedFees.halfHourFee,
            hourFee = recommendedFees.hourFee,
            minimumFee = recommendedFees.minimumFee
        )
    }

}
