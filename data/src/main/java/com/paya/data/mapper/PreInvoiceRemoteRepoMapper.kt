package com.paya.data.mapper

import com.paya.common.Mapper
import com.paya.domain.models.remote.PreInvoiceRemoteModel
import com.paya.domain.models.repo.PreInvoiceRepoModel
import javax.inject.Inject

class PreInvoiceRemoteRepoMapper @Inject constructor() : Mapper<
        PreInvoiceRemoteModel,
        PreInvoiceRepoModel
        > {

    override fun map(param: PreInvoiceRemoteModel): PreInvoiceRepoModel {
        return PreInvoiceRepoModel(
            param.uuid ?: "",
            param.hostId?.let { it[0].toInt() } ?: -1,
            param.price?.let { it[0].toLong() } ?: 0L
        )
    }

}