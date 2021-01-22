package com.paya.data.repository

import com.paya.common.Mapper
import com.paya.data.database.developer_name.DeveloperNameDbApi
import com.paya.data.datasource.developer.DeveloperDataSource
import com.paya.data.network.apiresponse.*
import com.paya.data.network.remote_api.DeveloperNameService
import com.paya.data.network.remote_api.FarabiService
import com.paya.data.utils.getResourceFromApiResponse
import com.paya.domain.models.local.DeveloperNameDbModel
import com.paya.domain.models.remote.DeveloperNameServerModel
import com.paya.domain.models.repo.DeveloperNameRepoModel
import com.paya.domain.models.repo.FarabiTokenRepoModel
import com.paya.domain.repository.DeveloperRepository
import com.paya.domain.repository.FarabiRepository
import com.paya.domain.tools.Resource
import java.lang.Exception
import javax.inject.Inject

class FarabiRepositoryImpl @Inject constructor(
    private val farabiService: FarabiService,
    private val farabiAuthRemoteRepoMapper: Mapper<String, FarabiTokenRepoModel>,
) : FarabiRepository {

    override suspend fun setToken(token: String): Resource<FarabiTokenRepoModel> {
        return getResourceFromApiResponse(farabiService.setToken(token)) {
            farabiAuthRemoteRepoMapper.map(it.data)
        }
    }

}