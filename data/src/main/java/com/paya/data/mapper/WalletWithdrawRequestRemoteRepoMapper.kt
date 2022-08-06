package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.WalletChargeRemoteModel
import com.paya.domain.models.remote.WalletPreWithdrawRemoteModel
import com.paya.domain.models.remote.WalletWithdrawRequestRemoteModel
import com.paya.domain.models.repo.WalletChargeRepoModel
import com.paya.domain.models.repo.WalletPreWithdrawRepoModel
import com.paya.domain.models.repo.WalletWithdrawRequestRepoModel
import javax.inject.Inject

class WalletWithdrawRequestRemoteRepoMapper @Inject constructor() : Mapper<
        WalletWithdrawRequestRemoteModel,
        WalletWithdrawRequestRepoModel
        > {

    override fun map(param: WalletWithdrawRequestRemoteModel): WalletWithdrawRequestRepoModel {
        return WalletWithdrawRequestRepoModel(
            param.sell ?: 0L
        )
    }

}