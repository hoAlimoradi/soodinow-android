package com.paya.domain.models.remote

import com.paya.domain.tools.NoObfuscate

data class BoxTypeRemoteModel(
	val types: List<BoxTypeParam>
): NoObfuscate

data class BoxTypeParam(val type: String, val name: String): NoObfuscate