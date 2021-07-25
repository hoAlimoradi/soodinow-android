package com.paya.data.repository

import androidx.lifecycle.Transformations.map
import com.paya.common.Mapper
import com.paya.data.mapper.ConfigRemoteRepoMapper
import com.paya.data.network.remote_api.CommonService
import com.paya.data.network.remote_api.QuestionService
import com.paya.data.sharedpreferences.PreferenceHelper
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.remote.*
import com.paya.domain.models.repo.*
import com.paya.domain.repository.CommonRepository
import com.paya.domain.repository.QuestionsRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
	private val commonService: CommonService,
	private val cityRemoteRepoMapper: Mapper<List<ProvinceRemoteModel>, List<ProvinceRepoModel>>,
	private val checkVersionRemoteRepoMapper: Mapper<CheckVersionRemoteModel, CheckVersionRepoModel>,
	private val configRemoteRepoMapper: Mapper<ConfigRemoteModel, ConfigRepoModel>,
	private val preferenceHelper: PreferenceHelper,
): CommonRepository{
	
	override suspend fun getCity(): Resource<List<ProvinceRepoModel>> {
		val response = commonService.getCity(preferenceHelper.getAccessToken())
		return getResourceFromApiResponse(response){
			cityRemoteRepoMapper.map(it.data)
		}
	}

	override suspend fun checkVersion(version: String): Resource<CheckVersionRepoModel> {
		val response = commonService.checkVersion(version)
		return getResourceFromApiResponse(response){
			checkVersionRemoteRepoMapper.map(it.data)
		}
	}

	override suspend fun getConfig(): Resource<ConfigRepoModel> {
		val response = commonService.getConfig(preferenceHelper.getAccessToken())
		return getResourceFromApiResponse(response){
			configRemoteRepoMapper.map(it.data)
		}
	}


}