package cz.yanas.bitcoin.client

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.net.Inet6Address

class NetworkAddressTest {

    @Test
    @ExperimentalUnsignedTypes
    fun constructor() {
        val bytes = ubyteArrayOf(
            0x0Du, 0x04u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u,
            0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u,
            0x20u, 0x8Du
        ).toByteArray()

        val networkAddress = NetworkAddress(BitcoinInputStream(ByteArrayInputStream(bytes)))

        assertThat(networkAddress.services).isEqualTo(1037uL)
        assertThat(networkAddress.address).isEqualTo(Inet6Address.getByAddress(ByteArray(16)))
        assertThat(networkAddress.port).isEqualTo(8333.toUShort())
    }

    @Test
    fun writeTo() {
        val networkAddress = NetworkAddress()

        val baos = ByteArrayOutputStream()
        networkAddress.writeTo(BitcoinOutputStream(baos))

        assertThat(baos.toByteArray()).containsExactly(
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x00, 0x00
        )
    }

}
