package cz.yanas.bitcoin.client

import java.nio.charset.StandardCharsets

data class MessageHeader(
    val magicNumber: UInt = MagicNumber.MAIN.rawValue,
    val command: String,
    val payloadSize: UInt,
    val payloadChecksum: UInt
) {

    constructor(bis: BitcoinInputStream) : this(
        magicNumber = bis.readUInt(),
        command = toCommand(bis.readNBytes(COMMAND_SIZE)),
        payloadSize = bis.readUInt(),
        payloadChecksum = bis.readUInt()
    )

    fun writeTo(bos: BitcoinOutputStream) {
        bos.writeUInt(magicNumber)
        bos.write(command.toByteArray(StandardCharsets.US_ASCII).copyOf(COMMAND_SIZE))
        bos.writeUInt(payloadSize)
        bos.writeUInt(payloadChecksum)
    }

    private companion object {

        const val COMMAND_SIZE = 12

        fun toCommand(bytes: ByteArray): String {
            return bytes.copyOf(bytes.indexOf(0x00)).toString(StandardCharsets.US_ASCII)
        }

    }

}
