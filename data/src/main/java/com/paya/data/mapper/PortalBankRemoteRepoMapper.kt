package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.PortalBankRemoteModel
import com.paya.domain.models.repo.PortalBankRepoModel
import javax.inject.Inject

class PortalBankRemoteRepoMapper @Inject constructor() : Mapper<
        @JvmSuppressWildcards List<PortalBankRemoteModel>,
        @JvmSuppressWildcards List<PortalBankRepoModel>
        > {

    override fun map(param: List<PortalBankRemoteModel>): List<PortalBankRepoModel> {
        return param.map {
            PortalBankRepoModel(
                it.name ?: "",
                it.persianName ?: "",
                it.imgUrl ?: ""

            )
        }
    }

}