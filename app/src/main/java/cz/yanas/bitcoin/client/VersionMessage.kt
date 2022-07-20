package cz.yanas.bitcoin.client

import org.apache.hc.core5.util.TimeValue
import java.util.Date

data class VersionMessage(
    val protocolVersion: Int,
    val services: ULong = 0u,
    val timestamp: Date = Date(),
    val recipient: NetworkAddress = NetworkAddress(),
    val sender: NetworkAddress = NetworkAddress(),
    val nonce: ULong = Math.random().toULong(),
    val userAgent: String,
    val blockHeight: Int = 0,
    val relayTxs: Boolean = false
) {

    companion object {
        const val COMMAND = "version"
    }

    constructor(bis: BitcoinInputStream) : this(
        protocolVersion = bis.readInt(),
        services = bis.readULong(),
        timestamp = Date(TimeValue.ofSeconds(bis.readLong()).toMilliseconds()),
        recipient = NetworkAddress(bis),
        sender = NetworkAddress(bis),
        nonce = bis.readULong(),
        userAgent = bis.readString(),
        blockHeight = bis.readInt(),
        relayTxs = bis.readBoolean()
    )

    fun writeTo(bos: BitcoinOutputStream) {
        bos.writeInt(protocolVersion)
        bos.writeULong(services)
        bos.writeLong(TimeValue.ofMilliseconds(timestamp.time).toSeconds())
        recipient.writeTo(bos)
        sender.writeTo(bos)
        bos.writeULong(nonce)
        bos.writeString(userAgent)
        bos.writeInt(blockHeight)
        bos.writeBoolean(relayTxs)
    }

}
