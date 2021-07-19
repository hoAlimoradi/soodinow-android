package com.paya.domain.models.repo

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class ResetPhoneVerifyBodyRepoModel(
	val phoneNumber: String,
	val code: String
): NoObfuscate