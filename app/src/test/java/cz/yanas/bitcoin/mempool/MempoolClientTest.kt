package cz.yanas.bitcoin.mempool

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class MempoolClientTest {

    @Test
    fun getBlockHeight() {
        val client = MempoolClient()
        val blockHeight = client.getBlockHeight()

        assertThat(blockHeight).isGreaterThanOrEqualTo(755465)
    }

    @Test
    fun getRecommendedFees() {
        val client = MempoolClient()
        val recommendedFees = client.getRecommendedFees()

        assertThat(recommendedFees.fastestFee).isPositive()
        assertThat(recommendedFees.halfHourFee).isPositive()
        assertThat(recommendedFees.hourFee).isPositive()
        assertThat(recommendedFees.economyFee).isPositive()
        assertThat(recommendedFees.minimumFee).isPositive()
    }

}
