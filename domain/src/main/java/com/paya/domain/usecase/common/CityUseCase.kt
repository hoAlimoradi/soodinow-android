package com.paya.domain.usecase.common

import com.paya.domain.models.repo.BoxTypeRepoModel
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.models.repo.MarketRepoModel
import com.paya.domain.models.repo.ProvinceRepoModel
import com.paya.domain.repository.CommonRepository
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.repository.MarketRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import javax.inject.Inject

class CityUseCase @Inject constructor(
	private val commonRepository: CommonRepository
): UseCase<Unit, @JvmSuppressWildcards List<ProvinceRepoModel>> {
	override suspend fun action(param: Unit): Resource<List<ProvinceRepoModel>> {
		return commonRepository.getCity()
	}
}