package cz.yanas.bitcoin.client

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class MessageHeaderTest {

    @Test
    @ExperimentalUnsignedTypes
    fun constructor() {
        val bytes = ubyteArrayOf(
            0xFAu, 0xBFu, 0xB5u, 0xDAu,
            0x76u, 0x65u, 0x72u, 0x73u, 0x69u, 0x6Fu, 0x6Eu, 0x00u, 0x00u, 0x00u, 0x00u, 0x00u,
            0x65u, 0x00u, 0x00u, 0x00u,
            0x35u, 0x8Du, 0x49u, 0x32u
        ).toByteArray()

        val header = MessageHeader(BitcoinInputStream(ByteArrayInputStream(bytes)))

        assertThat(header.magicNumber).isEqualTo(0xDAB5BFFAu)
        assertThat(header.command).isEqualTo("version")
        assertThat(header.payloadSize).isEqualTo(101u)
        assertThat(header.payloadChecksum).isEqualTo(0x32498D35u)
    }

    @Test
    fun writeTo() {
        val header = MessageHeader(command = "version", payloadSize = 100u, payloadChecksum = 0x37518C19u)

        val baos = ByteArrayOutputStream()
        header.writeTo(BitcoinOutputStream(baos))

        assertThat(baos.toByteArray()).containsExactly(
            0xF9, 0xBE, 0xB4, 0xD9,
            0x76, 0x65, 0x72, 0x73, 0x69, 0x6F, 0x6E, 0x00, 0x00, 0x00, 0x00, 0x00,
            0x64, 0x00, 0x00, 0x00,
            0x19, 0x8C, 0x51, 0x37
        )
    }

}
