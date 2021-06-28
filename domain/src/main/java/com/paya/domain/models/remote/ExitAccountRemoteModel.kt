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
	@SerializedName("user_profit")
	val userProfit:List<UserProfit>?,
): NoObfuscate


data class UserProfit(
	@SerializedName("days")
	val days: Int?,
	@SerializedName("pre_box_value")
	val preBoxValue: Float?,
	@SerializedName("now_box_value")
	val nowBoxValue: Float?,
	@SerializedName("pure_profit")
	val pureProfit: Float?,
	@SerializedName("total_profit")
	val totalProfit: Double?,
	@SerializedName("name")
	val name: String?,
	@SerializedName("soodinow_profit")
	val soodinowProfit: Float?


) : NoObfuscate
