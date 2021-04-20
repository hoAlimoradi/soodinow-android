package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CommonService {

	@GET("auth/iran_states/")
	suspend fun getCity(): ApiResponse<BaseModel<List<ProvinceRemoteModel>>>

	
}