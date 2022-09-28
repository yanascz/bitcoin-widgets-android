package cz.yanas.bitcoin.widgets

data class MempoolStatus(
    val blockHeight: Int,
    val fastestFee: Int,
    val halfHourFee: Int,
    val hourFee: Int,
    val economyFee: Int,
    val minimumFee : Int
)
