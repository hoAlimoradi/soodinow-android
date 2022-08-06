package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.repo.WalletBuyRepoModel
import javax.inject.Inject

class WalletBuyRemoteRepoMapper @Inject constructor() : Mapper<
        String,
        WalletBuyRepoModel
        > {

    override fun map(param: String): WalletBuyRepoModel {
        return WalletBuyRepoModel(
            ""
        )
    }

}