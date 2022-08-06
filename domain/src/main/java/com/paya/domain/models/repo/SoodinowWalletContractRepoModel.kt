package com.paya.domain.models.repo

import com.paya.domain.tools.NoObfuscate

data class SoodinowWalletContractRepoModel(
    val pointTitle: String,
    val name: String,
    val description: String,
    val trimesterValue: Int,
    val monthlyValue: Int,
    val weeklyValue: Int
): NoObfuscate