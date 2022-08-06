package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.repo.CashWithdrawRequestRepoModel
import javax.inject.Inject

class CashWithdrawRequestRemoteRepoMapper @Inject constructor() : Mapper<
        String,
        CashWithdrawRequestRepoModel
        > {

    override fun map(param: String): CashWithdrawRequestRepoModel {
        return CashWithdrawRequestRepoModel(
            param ?: ""
        )
    }

}