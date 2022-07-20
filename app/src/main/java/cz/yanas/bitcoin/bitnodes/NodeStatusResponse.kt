package cz.yanas.bitcoin.bitnodes

data class NodeStatusResponse(
    val status: String,
    val protocolVersion: Int,
    val userAgent: String,
    val height: Int
)
