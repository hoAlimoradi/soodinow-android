package com.paya.domain.models.repo

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class WalletValueRepoModel(
   val id: Int,
   val balance: Long,
   val blockValue: Long,
) : NoObfuscate