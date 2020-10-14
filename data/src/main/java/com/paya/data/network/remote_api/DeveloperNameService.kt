package com.paya.data.network.remote_api

import com.paya.domain.models.remote.DeveloperNameServerModel
import retrofit2.http.GET

interface DeveloperNameService{
	@GET("/ShahbaziSajjad/get_developer_name")
	suspend fun getName() : DeveloperNameServerModel
}