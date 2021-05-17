package com.paya.domain.models.remote

import com.paya.domain.tools.NoObfuscate

data class DeveloperNameServerModel(
    val first: String? = "",
    val last: String? = ""
): NoObfuscate