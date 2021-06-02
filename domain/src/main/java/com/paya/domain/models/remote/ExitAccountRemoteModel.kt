package com.paya.domain.models.remote

import com.google.gson.annotations.SerializedName
import com.paya.domain.tools.NoObfuscate

data class ExitAccountRemoteModel(
	val existing: Boolean,
	@SerializedName("active_box_id")
	val activeBox: List<ActiveBoxRemote>?
): NoObfuscate

data class ActiveBoxRemote(
	val id: Long?,
	val type:String?,
	@SerializedName("sub_type")
	val subType:String?,
	@SerializedName("create_at")
	val createAt:String?,
	@SerializedName("user_name")
	val userName:String?,
	@SerializedName("total_price")
	val price:Float?,
): NoObfuscate

