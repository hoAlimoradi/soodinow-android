package com.paya.domain.models.repo

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class GetAuthLinkRepoModel(
	val link: String,
): NoObfuscate