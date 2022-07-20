package cz.yanas.bitcoin.client

import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.net.Socket

class BitcoinClient(val host: String, val port: Int) {

    fun getVersionMessage(): VersionMessage {
        Socket(host, port).use {
            sendVersionMessage(it, VersionMessage(
                protocolVersion = 70001,
                userAgent = "/BitcoinClient:1.0.0/"
            ))

            return receiveVersionMessage(it)
        }
    }

    private fun sendVersionMessage(socket: Socket, versionMessage: VersionMessage) {
        val payload = ByteArrayOutputStream()
        versionMessage.writeTo(BitcoinOutputStream(payload))

        val messageHeader = MessageHeader(
            command = VersionMessage.COMMAND,
            payloadSize = payload.size().toUInt(),
            payloadChecksum = payload.toByteArray().checksum()
        )

        val bos = BitcoinOutputStream(socket.getOutputStream())
        messageHeader.writeTo(bos)
        bos.write(payload.toByteArray())
        bos.flush()
    }

    private fun receiveVersionMessage(socket: Socket): VersionMessage {
        val input = socket.getInputStream()
        val messageHeader = MessageHeader(BitcoinInputStream(input))

        if (messageHeader.magicNumber != MagicNumber.MAIN.rawValue) {
            throw BitcoinClientException("Invalid magic number")
        }
        if (messageHeader.command != VersionMessage.COMMAND) {
            throw BitcoinClientException("Unexpected command")
        }

        val payload = input.readNBytes(messageHeader.payloadSize.toInt())

        if (payload.checksum() != messageHeader.payloadChecksum) {
            throw BitcoinClientException("Invalid checksum")
        }

        return VersionMessage(BitcoinInputStream(ByteArrayInputStream(payload)))
    }

}
