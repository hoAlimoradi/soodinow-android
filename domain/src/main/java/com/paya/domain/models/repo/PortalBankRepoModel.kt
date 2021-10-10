package com.paya.domain.models.repo

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class PortalBankRepoModel(
	val name: String,
	val persianName: String,
	val imgUrl: String
): NoObfuscate

