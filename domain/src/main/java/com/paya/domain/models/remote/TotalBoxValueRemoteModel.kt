package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class TotalBoxValueRemoteModel(
    @SerializedName("total_value") val totalValue: Long,

    ) : NoObfuscate