package cz.yanas.bitcoin.widgets

import cz.yanas.bitcoin.bitnodes.BitnodesClient
import cz.yanas.bitcoin.client.BitcoinClient

object NodeStatusProvider {

    private val bitnodesClient = BitnodesClient()

    fun getNodeStatus(configuration: NodeConfiguration): NodeStatus {
        if (configuration.host.endsWith(".onion")) {
            val cachedStatus = bitnodesClient.getNodeStatus(configuration.host, configuration.port)
            val currentStatus = bitnodesClient.checkNode(configuration.host, configuration.port)

            return NodeStatus(
                blockHeight = currentStatus.height,
                userAgent = cachedStatus.userAgent,
                protocolVersion = cachedStatus.protocolVersion
            )
        }

        val bitcoinClient = BitcoinClient(configuration.host, configuration.port)
        val versionMessage = bitcoinClient.getVersionMessage()

        return NodeStatus(
            blockHeight = versionMessage.blockHeight,
            userAgent = versionMessage.userAgent,
            protocolVersion = versionMessage.protocolVersion
        )
    }

}
