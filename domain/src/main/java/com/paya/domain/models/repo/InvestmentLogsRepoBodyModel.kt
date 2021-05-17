package com.paya.domain.models.repo

data class InvestmentLogsRepoBodyModel(
    val page: Int,
    val pageSize: Int,
    val state: String = "",
    val type: String = "",
    val investmentType: String = "",
    val dateFrom: String = "",
    val dateTo: String = "",
) {
    fun getFilters(): Map<String, String> {
        val map = HashMap<String, String>()
        if (state.isNotEmpty())
            map["state"] = state
        if (type.isNotEmpty())
            map["type"] = type
        if (investmentType.isNotEmpty())
            map["investment_type"] = investmentType
        if (dateFrom.isNotEmpty())
            map["date_from"] = dateFrom
        if (dateTo.isNotEmpty())
            map["date_to"] = dateTo
        return map
    }
}