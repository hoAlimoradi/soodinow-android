package com.paya.domain.models.local

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class UserInfoDbModel(
	@Id var id: Long = 0,
	var isPasswordSet: Boolean = false,
	var isHintShowed: Boolean = false
)