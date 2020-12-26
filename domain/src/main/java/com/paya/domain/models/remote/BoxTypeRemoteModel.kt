package com.paya.domain.models.remote

data class BoxTypeRemoteModel(
	val types: List<BoxTypeParam>
)

data class BoxTypeParam(val type: String, val name: String)