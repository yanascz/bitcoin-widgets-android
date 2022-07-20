package cz.yanas.bitcoin.bitnodes

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import org.apache.hc.client5.http.fluent.Request
import org.apache.hc.core5.http.ContentType

class BitnodesClient {

    private val baseUrl = "https://bitnodes.io/api/v1"

    private val gson = Gson().newBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

    fun checkNode(host: String, port: Int): CheckNodeResponse {
        val checkNodeRequest = Request.post("$baseUrl/checknode/")
            .bodyString("address=$host&port=$port", ContentType.APPLICATION_FORM_URLENCODED)
        val checkNodeResponseBody = checkNodeRequest.execute().returnContent()

        return gson.fromJson(checkNodeResponseBody.asString(), CheckNodeResponse::class.java)
    }

    fun getNodeStatus(host: String, port: Int): NodeStatusResponse {
        val nodeStatusRequest = Request.get("$baseUrl/nodes/$host-$port/")
        val nodeStatusResponse = nodeStatusRequest.execute().returnContent()
        val responseBody = gson.fromJson(nodeStatusResponse.asString(), Map::class.java)
        val responseData = responseBody["data"] as List<*>

        return NodeStatusResponse(
            status = responseBody["status"] as String,
            protocolVersion = (responseData[0] as Double).toInt(),
            userAgent = responseData[1] as String,
            height = (responseData[4] as Double).toInt()
        )
    }

}
