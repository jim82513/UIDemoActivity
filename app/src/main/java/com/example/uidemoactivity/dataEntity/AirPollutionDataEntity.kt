package com.example.uidemoactivity.dataEntity

import com.google.gson.annotations.SerializedName

data class AirPollutionDataEntity(
    @SerializedName("records")
    var mInfoEntity: List<AirPollutionInfoEntity>
)

