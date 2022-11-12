package cz.yanas.bitcoin.blockchain

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BlockchainClientTest {

    @Test
    fun getTickers() {
        val client = BlockchainClient()
        val tickers = client.getTickers()

        assertThat(tickers).isNotEmpty()
        assertThat(tickers).hasEntrySatisfying("USD") {
            assertThat(it.symbol).isEqualTo("USD")
            assertThat(it.last).isGreaterThan(0.0)
        }
    }

}
