package com.example.uidemoactivity.dataEntity

import com.google.gson.annotations.SerializedName

data class AirPollutionData(
    @SerializedName("records")
    val infoList: List<AirPollutionInfo>
)

