package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class WalletPreWithdrawRemoteModel(
   @SerializedName("max_value") val maxValue: Long?
) : NoObfuscate