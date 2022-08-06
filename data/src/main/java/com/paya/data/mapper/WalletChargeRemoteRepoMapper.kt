package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.WalletChargeRemoteModel
import com.paya.domain.models.repo.WalletChargeRepoModel
import javax.inject.Inject

class WalletChargeRemoteRepoMapper @Inject constructor() : Mapper<
        WalletChargeRemoteModel,
        WalletChargeRepoModel
        > {

    override fun map(param: WalletChargeRemoteModel): WalletChargeRepoModel {
        return WalletChargeRepoModel(
            param.link ?: ""
        )
    }

}