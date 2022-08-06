package com.paya.domain.usecase.farabi

import com.paya.domain.models.repo.FarabiTokenRepoModel
import com.paya.domain.models.repo.MarketRepoModel
import com.paya.domain.models.repo.UserFarabiRepoModel
import com.paya.domain.repository.FarabiRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class UserFarabiUseCase @Inject constructor(
	private val farabiRepository: FarabiRepository
): UseCase<String, UserFarabiRepoModel> {
	override suspend fun action(param: String): Resource<UserFarabiRepoModel> {
		return farabiRepository.getUserFarabi(param)
	}
}