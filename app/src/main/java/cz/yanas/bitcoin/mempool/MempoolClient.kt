package cz.yanas.bitcoin.mempool

import com.google.gson.Gson
import org.apache.hc.client5.http.fluent.Request

class MempoolClient {

    private val baseUrl = "https://mempool.space/api"

    private val gson = Gson()

    fun getBlockHeight(): Int {
        val blockHeightRequest = Request.get("$baseUrl/blocks/tip/height")
        val blockHeightResponseBody = blockHeightRequest.execute().returnContent()

        return blockHeightResponseBody.asString().toInt()
    }

    fun getRecommendedFees(): RecommendedFees {
        val recommendedFeesRequest = Request.get("$baseUrl/v1/fees/recommended")
        val recommendedFeesResponse = recommendedFeesRequest.execute().returnContent()

        return gson.fromJson(recommendedFeesResponse.asString(), RecommendedFees::class.java)
    }

}
