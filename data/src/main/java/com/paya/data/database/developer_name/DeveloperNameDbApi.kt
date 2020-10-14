package com.paya.data.database.developer_name

import com.paya.domain.models.local.DeveloperNameDbModel

interface DeveloperNameDbApi {
	suspend fun insert(model : DeveloperNameDbModel)
	suspend fun delete()
	suspend fun get(): DeveloperNameDbModel?
}