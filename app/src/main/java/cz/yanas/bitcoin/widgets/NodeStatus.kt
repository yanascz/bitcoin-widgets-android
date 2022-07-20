package cz.yanas.bitcoin.widgets

data class NodeStatus(
    val blockHeight: Int,
    val userAgent: String,
    val protocolVersion: Int
)
