package com.paya.data.network.remote_api

import com.paya.data.network.apiresponse.ApiResponse
import com.paya.domain.models.remote.BaseModel
import com.paya.domain.models.remote.BoxHistoryRemoteModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BoxHistoryService {
	
	@GET("order/box_history/{boxId}/")
	suspend fun getBoxHistory(
		@Path("boxId") boxId: Long,
		@Query("type") type: String,
		@Query("number") number: Int
	): ApiResponse<BaseModel<BoxHistoryRemoteModel>>
	
}