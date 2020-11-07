package com.paya.data.database.userInfo

import com.paya.domain.models.local.UserInfoDbModel

interface UserInfoDbApi {
	suspend fun getSingle(): UserInfoDbModel?
	suspend fun updateIsPasswordSet(isPasswordSet: Boolean)
	suspend fun updateIsHintShowed(isHintShowed: Boolean)
}