package cz.yanas.bitcoin.blockchain

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.apache.hc.client5.http.fluent.Request

class BlockchainClient {

    private val baseUrl = "https://blockchain.info"

    private val gson = Gson()

    fun getTickers(): Map<String, Ticker> {
        val tickersRequest = Request.get("$baseUrl/ticker")
        val tickersResponse = tickersRequest.execute().returnContent()

        return gson.fromJson(
            tickersResponse.asString(),
            TypeToken.getParameterized(Map::class.java, String::class.java, Ticker::class.java).type
        )
    }

}
