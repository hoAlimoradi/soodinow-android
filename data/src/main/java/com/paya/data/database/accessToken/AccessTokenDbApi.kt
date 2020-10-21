package com.paya.data.database.accessToken

import com.paya.domain.models.local.AccessTokenDbModel

interface AccessTokenDbApi {
	suspend fun getSingle(): AccessTokenDbModel?
	suspend fun update(model: AccessTokenDbModel)
}