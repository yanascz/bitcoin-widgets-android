package cz.yanas.bitcoin.client

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.util.Date

class BitcoinClientTest {

    @Test
    fun getVersionMessage() {
        val client = BitcoinClient(host = "127.0.0.1", port = 8333)
        val message = client.getVersionMessage()

        assertThat(message.protocolVersion).isEqualTo(70016)
        assertThat(message.services).isEqualTo(1033uL)
        assertThat(message.timestamp).isCloseTo(Date(), 21000)
        assertThat(message.recipient).isNotNull()
        assertThat(message.sender).isNotNull()
        assertThat(message.nonce).isNotNull()
        assertThat(message.userAgent).isEqualTo("/Satoshi:23.0.0/")
        assertThat(message.blockHeight).isGreaterThanOrEqualTo(740597)
        assertThat(message.relayTxs).isTrue()
    }

}
