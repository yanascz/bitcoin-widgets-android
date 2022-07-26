package cz.yanas.bitcoin.mempool

data class RecommendedFees(
    val fastestFee: Int,
    val halfHourFee: Int,
    val hourFee: Int,
    val economyFee: Int,
    val minimumFee : Int
)
