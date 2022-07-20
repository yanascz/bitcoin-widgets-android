package cz.yanas.bitcoin.client

import java.net.Inet6Address

data class NetworkAddress(
    val services: ULong = 0u,
    val address: Inet6Address = toAddress(ByteArray(ADDRESS_SIZE)),
    val port: UShort = 0u
) {

    constructor(bis: BitcoinInputStream) : this(
        services = bis.readULong(),
        address = toAddress(bis.readNBytes(ADDRESS_SIZE)),
        port = bis.readPort()
    )

    fun writeTo(bos: BitcoinOutputStream) {
        bos.writeULong(services)
        bos.write(address.address)
        bos.writePort(port)
    }

    private companion object {

        const val ADDRESS_SIZE = 16

        fun toAddress(bytes: ByteArray): Inet6Address {
            return Inet6Address.getByAddress(bytes) as Inet6Address
        }

    }

}
