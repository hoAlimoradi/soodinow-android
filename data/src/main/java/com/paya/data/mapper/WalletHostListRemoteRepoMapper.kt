package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.WalletHostListRemoteModel
import com.paya.domain.models.repo.BasketHostsRepoModel
import com.paya.domain.models.repo.EfficiencyHostListRepoModel
import com.paya.domain.models.repo.EfficiencyModel
import com.paya.domain.models.repo.WalletHostListRepoModel
import javax.inject.Inject

class WalletHostListRemoteRepoMapper @Inject constructor() : Mapper<
        @JvmSuppressWildcards List<WalletHostListRemoteModel>,
        @JvmSuppressWildcards List<WalletHostListRepoModel>
        > {

    override fun map(param: List<WalletHostListRemoteModel>, ): List<WalletHostListRepoModel> {
        return param.map {
            WalletHostListRepoModel(
                it.id ?: 0,
                it.name ?: "",
                it.descriptionTitle ?: "",
                it.descriptionBody ?: "",
                it.accessLevel ?: "",
                it.fromRisk ?: 0.0,
                it.toRisk ?: 0.0,
                it.properties ?: emptyList(),
                it.basket?.let { basket -> BasketHostsRepoModel(basket.fixIncome ?: 0.0,basket.gold?:0.0,basket.sahami?:0.0) }
                    ?: BasketHostsRepoModel(0.0,0.0,0.0),
                it.efficiency?.let { efficiency ->
                    EfficiencyHostListRepoModel(
                        efficiency.week?.let { week ->
                            EfficiencyModel(
                                week.percent ?: 0.0,
                                week.negative ?: false
                            )
                        } ?: EfficiencyModel(0.0, false),
                        efficiency.month?.let { month ->
                            EfficiencyModel(
                                month.percent ?: 0.0,
                            month.negative ?: false
                        ) }
                            ?: EfficiencyModel(0.0, false),
                        efficiency.threeMonth?.let { threeMonth ->  EfficiencyModel(
                            threeMonth.percent ?: 0.0,
                            threeMonth.negative ?: false
                        ) }
                            ?: EfficiencyModel(0.0, false),
                    )
                } ?: EfficiencyHostListRepoModel(
                    EfficiencyModel(0.0, false),
                    EfficiencyModel(0.0, false),
                    EfficiencyModel(0.0, false)
                )
            )
        }
    }

}