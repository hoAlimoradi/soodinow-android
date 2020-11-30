package com.paya.domain.models.repo

data class ProfileRepoModel(
	val id:Int,
	val name: String,
	val personalCode: String,
	val birthDay: String,
	val bban: String,
	val complete: Boolean,
	val mobile: String,
	
)