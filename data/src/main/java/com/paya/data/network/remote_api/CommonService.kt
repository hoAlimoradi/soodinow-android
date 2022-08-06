package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.BaseModel
import com.paya.domain.models.remote.CheckVersionRemoteModel
import com.paya.domain.models.remote.ConfigRemoteModel
import com.paya.domain.models.remote.ProvinceRemoteModel
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface CommonService {

	@GET("auth/iran_states/")
	suspend fun getCity(@Header("Authorization") auth: String): ApiResponse<BaseModel<List<ProvinceRemoteModel>>>

	@GET("check_version/{version}/")
	suspend fun checkVersion(@Path("version") version: String): ApiResponse<BaseModel<CheckVersionRemoteModel>>

	@GET("get_default_info/")
	suspend fun getConfig(@Header("Authorization") auth: String): ApiResponse<BaseModel<ConfigRemoteModel>>
}