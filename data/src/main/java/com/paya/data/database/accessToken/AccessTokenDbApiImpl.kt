package com.paya.data.database.accessToken

import com.paya.domain.models.local.AccessTokenDbModel
import io.objectbox.Box
import javax.inject.Inject

class AccessTokenDbApiImpl @Inject constructor(
	private val accessTokenBox: Box<AccessTokenDbModel>
) : AccessTokenDbApi {
	override suspend fun getSingle(): AccessTokenDbModel? {
		val accessTokens = accessTokenBox.all
		return accessTokens.lastOrNull()
	}
	
	override suspend fun update(model: AccessTokenDbModel) {
		val accessToken = getSingle()
		if (accessToken != null)
			model.id = accessToken.id
		accessTokenBox.put(model)
	}
	
}