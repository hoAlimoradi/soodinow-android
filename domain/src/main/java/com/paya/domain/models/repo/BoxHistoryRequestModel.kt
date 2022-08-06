package com.paya.domain.models.repo

import retrofit2.http.Path
import retrofit2.http.Query

data class BoxHistoryRequestModel(
	val boxId: Long,
	val type: String,
	val objectType: String,
	val number: Int
)