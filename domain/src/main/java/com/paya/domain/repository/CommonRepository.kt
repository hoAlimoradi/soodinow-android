package com.paya.domain.repository

import com.paya.domain.models.remote.UserTestBody
import com.paya.domain.models.repo.ProvinceRepoModel
import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.domain.models.repo.UserTestRepoModel
import com.paya.domain.tools.Resource

interface CommonRepository {
	suspend fun getCity(): Resource<List<ProvinceRepoModel>>
}