package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.WalletValueRemoteModel
import com.paya.domain.models.repo.WalletValueRepoModel
import javax.inject.Inject

class WalletValueRemoteRepoMapper @Inject constructor() : Mapper<
        WalletValueRemoteModel,
        WalletValueRepoModel
        > {

    override fun map(param: WalletValueRemoteModel): WalletValueRepoModel {
        return WalletValueRepoModel(
            param.id ?: 0,
            param.balance ?: 0,
            param.blockValue ?: 0
        )
    }

}