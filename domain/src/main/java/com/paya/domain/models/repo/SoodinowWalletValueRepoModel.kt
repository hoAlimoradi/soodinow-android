package com.paya.domain.models.repo

import com.paya.domain.tools.NoObfuscate


data class SoodinowWalletValuePercent(
    val isin : String,
    val namad : String,
    val percent : Double
): NoObfuscate


data class SoodinowWalletValueRepoModel (
    val id : Int,
    val value : Double,
    val percent : List<SoodinowWalletValuePercent>
): NoObfuscate