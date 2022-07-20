package cz.yanas.bitcoin.client

import java.io.InputStream

fun InputStream.readNBytes(length: Int): ByteArray {
    val bytes = ByteArray(length)
    this.read(bytes)

    return bytes
}
