package cz.yanas.bitcoin.client

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.nio.charset.StandardCharsets

class ByteArrayKtTest {

    @Test
    fun checksum() {
        val bytes = "hello".toByteArray(StandardCharsets.US_ASCII)

        assertThat(bytes.checksum()).isEqualTo(0xDFC99595u)
    }

}
