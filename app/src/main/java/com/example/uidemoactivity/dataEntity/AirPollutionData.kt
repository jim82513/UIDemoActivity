package com.example.uidemoactivity.dataEntity

import com.google.gson.annotations.SerializedName

data class AirPollutionData(
    @SerializedName("records")
    var mInfoEntity: List<AirPollutionInfo>
)

