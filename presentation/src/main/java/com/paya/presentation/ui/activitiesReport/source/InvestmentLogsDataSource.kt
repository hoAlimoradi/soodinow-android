package com.paya.presentation.ui.activitiesReport.source

import androidx.paging.PagingSource
import com.paya.domain.models.repo.InvestmentLogsModel
import com.paya.domain.models.repo.InvestmentLogsRepoBodyModel
import com.paya.domain.models.repo.InvestmentLogsRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import com.paya.presentation.viewmodel.FinancialReportViewModel

class InvestmentLogsDataSource(
    private val investmentUseCase: UseCase<InvestmentLogsRepoBodyModel, InvestmentLogsRepoModel>,
    private val viewModel: BaseViewModel,
) : PagingSource<Int, InvestmentLogsModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, InvestmentLogsModel> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPage = params.key ?: 1
            val body = InvestmentLogsRepoBodyModel(nextPage, 25)
            if (nextPage == 1 && viewModel is FinancialReportViewModel) {
                viewModel.loading.setValue(Resource.loading(null))
            }
            val response = callResource(viewModel, investmentUseCase.action(body))
            if (viewModel is FinancialReportViewModel)
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
