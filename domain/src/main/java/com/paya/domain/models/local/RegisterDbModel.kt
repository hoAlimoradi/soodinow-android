package com.paya.domain.models.local

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class RegisterDbModel(
	@Id var id: Long = 0,
	val username: String? = ""
)