package com.example.data.database.developer_name

import com.example.domain.models.local.DeveloperNameDbModel

interface DeveloperNameDbApi {
	suspend fun insert(model : DeveloperNameDbModel)
	suspend fun delete()
	suspend fun get(): DeveloperNameDbModel?
}