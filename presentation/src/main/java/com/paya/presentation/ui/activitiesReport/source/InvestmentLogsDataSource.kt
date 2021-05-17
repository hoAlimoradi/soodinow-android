package com.paya.presentation.ui.activitiesReport.source

import androidx.paging.PagingSource
import com.paya.domain.models.repo.InvestmentLogsRepoBodyModel
import com.paya.domain.models.repo.InvestmentLogsRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.utils.callResource
import com.paya.presentation.viewmodel.FinancialReportViewModel

class InvestmentLogsDataSource(
    private val investmentUseCase: UseCase<InvestmentLogsRepoBodyModel, InvestmentLogsRepoModel>,
    private val viewModel: FinancialReportViewModel,
) : PagingSource<Int, Any>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Any> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPage = params.key ?: 1
            val body = InvestmentLogsRepoBodyModel(
                nextPage,
                25,
                type = viewModel.type,
                dateFrom = viewModel.dateFrom,
                dateTo = viewModel.dateTo
            )
            if (nextPage == 1) {
                viewModel.loading.setValue(Resource.loading(null))
            }
            val response = callResource(viewModel, investmentUseCase.action(body))
            viewModel.loading.setValue(Resource.idle(null))
            if (response.status == Status.SUCCESS) {
                response.data?.let {
                    LoadResult.Page(
                        data = it.results,
                        prevKey = if (nextPage == 1) null else nextPage - 1,
                        nextKey = if (it.next.isEmpty()) null else nextPage + 1
                    )
                }!!
            } else {
                LoadResult.Error(Exception(response.message))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
