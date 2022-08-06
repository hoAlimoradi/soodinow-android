package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.WalletChargeRemoteModel
import com.paya.domain.models.remote.WalletPreWithdrawRemoteModel
import com.paya.domain.models.repo.WalletChargeRepoModel
import com.paya.domain.models.repo.WalletPreWithdrawRepoModel
import javax.inject.Inject

class WalletPreWithdrawRemoteRepoMapper @Inject constructor() : Mapper<
        WalletPreWithdrawRemoteModel,
        WalletPreWithdrawRepoModel
        > {

    override fun map(param: WalletPreWithdrawRemoteModel): WalletPreWithdrawRepoModel {
        return WalletPreWithdrawRepoModel(
            param.maxValue ?: 0L
        )
    }

}