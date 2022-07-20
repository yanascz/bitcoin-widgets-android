package cz.yanas.bitcoin.bitnodes

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class BitnodesClientTest {

    private val host = "todo.onion"
    private val port = 8333

    @Test
    fun checkNode() {
        val client = BitnodesClient()
        val response = client.checkNode(host, port)

        assertThat(response.userAgent).isEqualTo("/Satoshi:23.0.0/")
        assertThat(response.height).isGreaterThanOrEqualTo(740597)
    }

    @Test
    fun getNodeStatus() {
        val client = BitnodesClient()
        val response = client.getNodeStatus(host, port)

        assertThat(response.status).isEqualTo("UP")
        assertThat(response.protocolVersion).isEqualTo(70016)
        assertThat(response.userAgent).isEqualTo("/Satoshi:23.0.0/")
        assertThat(response.height).isGreaterThanOrEqualTo(740597)
    }

}
