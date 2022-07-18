package com.example.uidemoactivity.dataEntity

import com.google.gson.annotations.SerializedName

data class AirPollutionInfoEntity(
    @SerializedName("siteid")
    var mSiteID: String,
    @SerializedName("sitename")
    var mSiteName: String,
    @SerializedName("county")
    var mCounty: String,
    @SerializedName("status")
    var mStatus: String,
    @SerializedName("pm2.5")
    var mPMStatus: String
)
