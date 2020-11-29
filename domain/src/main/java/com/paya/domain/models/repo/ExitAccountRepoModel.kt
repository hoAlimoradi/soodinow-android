package com.paya.domain.models.repo

data class ExitAccountRepoModel(
	val existing: Boolean,
	val activeBoxId: List<Long>
)