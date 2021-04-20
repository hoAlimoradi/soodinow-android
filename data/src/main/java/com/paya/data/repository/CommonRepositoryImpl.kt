package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.network.remote_api.CommonService
import com.paya.data.network.remote_api.QuestionService
import com.paya.data.sharedpreferences.PreferenceHelper
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.remote.*
import com.paya.domain.models.repo.ProvinceRepoModel
import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.domain.models.repo.UserTestRepoModel
import com.paya.domain.repository.CommonRepository
import com.paya.domain.repository.QuestionsRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

class CommonRepositoryImpl @Inject constructor(
	private val commonService: CommonService,
	private val cityRemoteRepoMapper: Mapper<List<ProvinceRemoteModel>, List<ProvinceRepoModel>>,

): CommonRepository{
	
	override suspend fun getCity(): Resource<List<ProvinceRepoModel>> {
		val response = commonService.getCity()
		return getResourceFromApiResponse(response){
			cityRemoteRepoMapper.map(it.data)
		}
	}

	
}