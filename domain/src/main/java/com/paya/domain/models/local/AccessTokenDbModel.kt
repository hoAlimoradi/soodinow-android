package com.paya.domain.models.local

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class AccessTokenDbModel(
	@Id var id: Long = 0,
	val accessToken: String? = ""
)