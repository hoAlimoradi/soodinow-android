package com.paya.domain.repository

import com.paya.domain.models.repo.ActivateRepoModel
import com.paya.domain.models.repo.RegisterRepoModel
import com.paya.domain.tools.Resource

interface AuthRepository {
	suspend fun register(phoneNumber: String): Resource<RegisterRepoModel>
	suspend fun activate(
		phoneNumber: String,
		code: String
	): Resource<ActivateRepoModel>
}