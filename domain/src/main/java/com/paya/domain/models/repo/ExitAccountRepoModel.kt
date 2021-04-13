package com.paya.domain.models.repo

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ExitAccountRepoModel(
	val existing: Boolean,
	val activeBox: List<ActiveBoxRepo>
)

data class ActiveBoxRepo(
	val id: Long,
	val type:String,
	val subType:String,
	val createAt:String,
	val userName:String,
	val price:Long,
) : Serializable