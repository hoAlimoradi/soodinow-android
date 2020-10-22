package com.paya.data.database.userInfo

import com.paya.domain.models.local.UserInfoDbModel
import io.objectbox.Box
import javax.inject.Inject

class UserInfoDbApiImpl @Inject constructor(
	private val userInfoBox: Box<UserInfoDbModel>
) : UserInfoDbApi {
	override suspend fun getSingle(): UserInfoDbModel? {
		val userInfoList = userInfoBox.all
		return userInfoList.lastOrNull()
	}
	
	override suspend fun updateAccessToken(accessToken: String) {
		val userInfo = getSingle() ?: UserInfoDbModel()
		userInfo.accessToken = accessToken
		userInfoBox.put(userInfo)
	}
	
	override suspend fun updateIsPasswordSet(isPasswordSet: Boolean) {
		val userInfo = getSingle() ?: UserInfoDbModel()
		userInfo.isPasswordSet = isPasswordSet
		userInfoBox.put(userInfo)
	}
	
	override suspend fun updateIsHintShowed(isHintShowed: Boolean) {
		val userInfo = getSingle() ?: UserInfoDbModel()
		userInfo.isHintShowed = isHintShowed
		userInfoBox.put(userInfo)
	}
	
}