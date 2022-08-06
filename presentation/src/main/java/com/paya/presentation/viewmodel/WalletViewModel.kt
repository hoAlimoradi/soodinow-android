package com.paya.presentation.viewmodel


import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.InvestingInfoRepoModel
import com.paya.domain.models.repo.SoodinowWalletValueRepoModel
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.domain.usecase.wallet.InvestingInfoUseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val investingInfoUseCase: UseCase<Unit, InvestingInfoRepoModel>
) : BaseViewModel() {

    val soodinowWalletValueRepoModelResourceMutableLiveData = MutableLiveData<Resource<InvestingInfoRepoModel>>()

    fun getSoodinowWalletValue() {
        showLoading()
        viewModelScope.launch {
            val response = callResource(
                this@WalletViewModel, investingInfoUseCase.action(Unit)
            )
            soodinowWalletValueRepoModelResourceMutableLiveData.postValue(response)
        }

        hideLoading()

    }

}
