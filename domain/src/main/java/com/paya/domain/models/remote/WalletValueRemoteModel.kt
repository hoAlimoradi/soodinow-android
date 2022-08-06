package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class WalletValueRemoteModel(
   @SerializedName("id") val id: Int?,
   @SerializedName("balance") val balance: Long?,
   @SerializedName("block_value") val blockValue: Long?,
) : NoObfuscate