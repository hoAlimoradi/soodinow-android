package com.paya.data.database.developer_name

import com.paya.domain.models.local.DeveloperNameDbModel
import io.objectbox.Box
import javax.inject.Inject

class DeveloperNameDbApiImpl @Inject constructor(
	private val developerNameBox: Box<DeveloperNameDbModel>
) : DeveloperNameDbApi {
	
	override suspend fun insert(model: DeveloperNameDbModel) {
		developerNameBox.put(model)
	}
	
	override suspend fun delete() {
		developerNameBox.removeAll()
	}
	
	override suspend fun get(): DeveloperNameDbModel? =
		developerNameBox.query().build().findFirst()
}