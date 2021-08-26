package com.paya.domain.repository

import com.paya.domain.models.remote.UserTestBody
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource

interface CommonRepository {
	suspend fun getCity(): Resource<List<ProvinceRepoModel>>

	suspend fun checkVersion(version: String): Resource<CheckVersionRepoModel>

	suspend fun getConfig(): Resource<ConfigRepoModel>

	suspend fun saveAppLink(appLink: String): Resource<Unit>

	suspend fun getAppLink(): Resource<GetAppLinkRepoModel>

	suspend fun getWhySoodinowList(): Resource<List<WhySoodinowModel>>

	suspend fun getAboutUsContent(): Resource<List<AboutUsModel>>
}