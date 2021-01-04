package com.paya.presentation.ui.cashManager.source

import androidx.paging.PagingSource
import com.paya.domain.models.repo.HistoryPriceBodyRepoModel
import com.paya.domain.models.repo.HistoryPriceRepoModel
import com.paya.domain.models.repo.PriceModel
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.price.HistoryPriceUseCase

class HistoryPriceDataSource(
    private val historyPriceUseCase: UseCase<HistoryPriceBodyRepoModel, HistoryPriceRepoModel>,
    private val filter: String
) : PagingSource<Int, PriceModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PriceModel> {
        try {
            // Start refresh at page 1 if undefined.
            val nextPage = params.key ?: 1
            val body = HistoryPriceBodyRepoModel(nextPage, filter)
            val response = historyPriceUseCase.action(body)
            return response.data?.priceHistory?.let {
                LoadResult.Page(
                    data = it,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = response.data!!.currentPage + 1
                )
            }!!
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
