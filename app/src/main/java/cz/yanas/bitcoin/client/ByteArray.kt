package cz.yanas.bitcoin.client

import java.security.MessageDigest

fun ByteArray.checksum(): UInt {
    val digest = MessageDigest.getInstance("SHA-256")
    val doubleSha256 = digest.digest(digest.digest(this))

    val b1 = doubleSha256[0].toUByte().toUInt()
    val b2 = doubleSha256[1].toUByte().toUInt()
    val b3 = doubleSha256[2].toUByte().toUInt()
    val b4 = doubleSha256[3].toUByte().toUInt()

    return b1 + (b2 shl 8) + (b3 shl 16) + (b4 shl 24)
}
