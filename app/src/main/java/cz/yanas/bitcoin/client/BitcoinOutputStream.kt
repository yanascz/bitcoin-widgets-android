package cz.yanas.bitcoin.client

import java.io.FilterOutputStream
import java.io.OutputStream
import java.nio.charset.StandardCharsets

class BitcoinOutputStream(out: OutputStream) : FilterOutputStream(out) {

    fun writeBoolean(value: Boolean) {
        out.write(if (value) 1 else 0)
    }

    fun writeInt(value: Int) {
        out.write(value)
        out.write(value ushr 8)
        out.write(value ushr 16)
        out.write(value ushr 24)
    }

    fun writeUInt(value: UInt) {
        writeInt(value.toInt())
    }

    fun writeLong(value: Long) {
        out.write(value.toInt())
        out.write((value ushr 8).toInt())
        out.write((value ushr 16).toInt())
        out.write((value ushr 24).toInt())
        out.write((value ushr 32).toInt())
        out.write((value ushr 40).toInt())
        out.write((value ushr 48).toInt())
        out.write((value ushr 56).toInt())
    }

    fun writeULong(value: ULong) {
        writeLong(value.toLong())
    }

    fun writePort(value: UShort) {
        out.write(value.toInt() ushr 8)
        out.write(value.toInt())
    }

    fun writeString(value: String) {
        out.write(value.length) // TODO: Variable length
        out.write(value.toByteArray(StandardCharsets.US_ASCII))
    }

}
