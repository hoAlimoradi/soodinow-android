package com.paya.domain.models.repo

data class WalletChargeRepoBodyModel(
    val charge: Long,
    val callbackUrl: String,
    val bankingPortal: String
)
