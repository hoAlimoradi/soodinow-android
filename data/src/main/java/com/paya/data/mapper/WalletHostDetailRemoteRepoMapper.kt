package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.WalletHostDetailRemoteModel
import com.paya.domain.models.repo.*
import javax.inject.Inject

class WalletHostDetailRemoteRepoMapper @Inject constructor() : Mapper<
        WalletHostDetailRemoteModel,
        WalletHostDetailRepoModel
        > {

    override fun map(param: WalletHostDetailRemoteModel, ): WalletHostDetailRepoModel {
        val color1 = "#076022"
        val color2 = "#85db48"
        return param.let {
            WalletHostDetailRepoModel(
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
                            )
                        }
                            ?: EfficiencyModel(0.0, false),
                        efficiency.threeMonth?.let { threeMonth ->
                            EfficiencyModel(
                                threeMonth.percent ?: 0.0,
                                threeMonth.negative ?: false
                            )
                        }
                            ?: EfficiencyModel(0.0, false),
                    )
                } ?: EfficiencyHostListRepoModel(
                    EfficiencyModel(0.0, false),
                    EfficiencyModel(0.0, false),
                    EfficiencyModel(0.0, false)
                ),
                it.basketDetail?.let { basket ->
                    basket.map { item ->
                        BasketHostsDetailRepoModel(item.namad ?: "", item.percent ?: 0.0F,when(item.namad) {
                            "آکورد" ->  "#17502f"
                            "کیان" ->  "#3e2cad"
                            "افران" ->  "#46a118"
                            "اعتماد" ->  "#076022"
                            else -> "#85db48"
                        })
                    }
                } ?: emptyList()
            )
        }
    }

}