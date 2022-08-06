package com.paya.domain.models.repo

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class PerLoginRepoModel(
	val username: String
): NoObfuscate