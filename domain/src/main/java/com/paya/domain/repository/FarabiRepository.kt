package com.paya.domain.repository


import com.paya.domain.models.repo.FarabiTokenRepoModel
import com.paya.domain.tools.Resource

interface FarabiRepository {
	suspend fun setToken(token: String): Resource<FarabiTokenRepoModel>
	
}