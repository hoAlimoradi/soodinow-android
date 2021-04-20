package com.paya.domain.usecase.farabi

import com.paya.domain.models.repo.FarabiInfoRepoModel
import com.paya.domain.models.repo.FarabiTokenRepoModel
import com.paya.domain.models.repo.MarketRepoModel
import com.paya.domain.models.repo.UserFarabiRepoModel
import com.paya.domain.repository.FarabiRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class FarabiInfoUseCase @Inject constructor(
	private val farabiRepository: FarabiRepository
): UseCase<UserFarabiRepoModel, FarabiInfoRepoModel> {
	override suspend fun action(param: UserFarabiRepoModel): Resource<FarabiInfoRepoModel> {
		return farabiRepository.setFarabiInfo(param)
	}
}