package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.network.remote_api.FarabiService
import com.paya.data.sharedpreferences.PreferenceHelper
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.repo.FarabiInfoRepoModel
import com.paya.domain.models.repo.FarabiTokenRepoModel
import com.paya.domain.models.repo.UserFarabiRepoModel
import com.paya.domain.repository.FarabiRepository
import com.paya.domain.tools.Resource
import javax.inject.Inject

class FarabiRepositoryImpl @Inject constructor(
    private val farabiService: FarabiService,
    private val farabiAuthRemoteRepoMapper: Mapper<String, FarabiTokenRepoModel>,
    private val userFarabiRemoteRepoMapper: Mapper<List<*>, UserFarabiRepoModel>,
    private val farabiInfoRemoteRepoMapper: Mapper<String, FarabiInfoRepoModel>,
    private val preferenceHelper: PreferenceHelper,
) : FarabiRepository {

    override suspend fun setToken(token: String): Resource<FarabiTokenRepoModel> {
        return getResourceFromApiResponse(
            farabiService.setToken(
                preferenceHelper.getAccessToken(),
                token
            )
        ) {
            farabiAuthRemoteRepoMapper.map(it.data)
        }
    }

    override suspend fun getUserFarabi(token: String): Resource<UserFarabiRepoModel> {
        return getResourceFromApiResponse(farabiService.getUserFarabi(token)) {
            userFarabiRemoteRepoMapper.map(it)
        }
    }

    override suspend fun setFarabiInfo(userFarabiRepoModel: UserFarabiRepoModel): Resource<FarabiInfoRepoModel> {
        return getResourceFromApiResponse(
            farabiService.setFarabiInfo(
                preferenceHelper.getAccessToken(),
                userFarabiRepoModel.entryOrderUserId,
                userFarabiRepoModel.investorBourseCodeId,
                userFarabiRepoModel.personageId,
                userFarabiRepoModel.userName
            )
        ) {
            farabiInfoRemoteRepoMapper.map(it.data)
        }
    }


}