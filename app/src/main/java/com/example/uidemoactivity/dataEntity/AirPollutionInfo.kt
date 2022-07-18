package com.example.uidemoactivity.dataEntity

import com.google.gson.annotations.SerializedName

data class AirPollutionInfo(
    @SerializedName("siteid")
    val siteID: String,
    @SerializedName("sitename")
    val siteName: String,
    @SerializedName("county")
    val county: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("pm2.5")
    val pmStatus: String
)
