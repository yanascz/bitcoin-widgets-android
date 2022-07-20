package cz.yanas.bitcoin.client

import java.io.EOFException
import java.io.FilterInputStream
import java.io.InputStream
import java.nio.charset.StandardCharsets

class BitcoinInputStream(input: InputStream) : FilterInputStream(input) {

    fun readBoolean(): Boolean {
        return `in`.read() == 1
    }

    fun readInt(): Int {
        val b1 = `in`.read()
        val b2 = `in`.read()
        val b3 = `in`.read()
        val b4 = `in`.read()

        if ((b1 or b2 or b3 or b4) < 0) {
            throw EOFException()
        }

        return b1 + (b2 shl 8) + (b3 shl 16) + (b4 shl 24)
    }

    fun readUInt(): UInt {
        return readInt().toUInt()
    }

    fun readLong(): Long {
        val b1 = `in`.read().toLong()
        val b2 = `in`.read().toLong()
        val b3 = `in`.read().toLong()
        val b4 = `in`.read().toLong()
        val b5 = `in`.read().toLong()
        val b6 = `in`.read().toLong()
        val b7 = `in`.read().toLong()
        val b8 = `in`.read().toLong()

        if ((b1 or b2 or b3 or b4 or b5 or b6 or b7 or b8) < 0) {
            throw EOFException()
        }

        return b1 + (b2 shl 8) + (b3 shl 16) + (b4 shl 24) + (b5 shl 32) + (b6 shl 40) + (b7 shl 48) + (b8 shl 56)
    }

    fun readULong(): ULong {
        return readLong().toULong()
    }

    fun readPort(): UShort {
        val b1 = `in`.read()
        val b2 = `in`.read()

        if ((b1 or b2) < 0) {
            throw EOFException()
        }

        return ((b1 shl 8) + b2).toUShort()
    }

    fun readString(): String {
        val length = `in`.read() // TODO: Variable length
        val bytes = ByteArray(length);
        `in`.read(bytes)

        return bytes.toString(StandardCharsets.US_ASCII)
    }

}
