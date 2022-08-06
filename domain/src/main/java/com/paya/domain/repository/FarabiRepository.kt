package com.paya.domain.repository


import com.paya.domain.models.repo.FarabiInfoRepoModel
import com.paya.domain.models.repo.FarabiTokenRepoModel
import com.paya.domain.models.repo.UserFarabiRepoModel
import com.paya.domain.tools.Resource

interface FarabiRepository {
	suspend fun setToken(token: String): Resource<FarabiTokenRepoModel>
	suspend fun getUserFarabi(token: String) : Resource<UserFarabiRepoModel>
	suspend fun setFarabiInfo(userFarabiRepoModel: UserFarabiRepoModel) : Resource<FarabiInfoRepoModel>

}