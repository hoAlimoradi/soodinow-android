package com.example.data.network.remote_api

import com.example.domain.models.remote.DeveloperNameServerModel
import retrofit2.http.GET

interface DeveloperNameService{
	@GET("/ShahbaziSajjad/get_developer_name")
	suspend fun getName() : DeveloperNameServerModel
}