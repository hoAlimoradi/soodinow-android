package com.paya.domain.repository

import com.paya.domain.models.remote.UserTestBody
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource

interface CommonRepository {
	suspend fun getCity(): Resource<List<ProvinceRepoModel>>

	suspend fun checkVersion(version: String): Resource<CheckVersionRepoModel>

	suspend fun getConfig(): Resource<ConfigRepoModel>
}