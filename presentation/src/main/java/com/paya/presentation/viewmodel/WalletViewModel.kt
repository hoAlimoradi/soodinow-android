package com.paya.presentation.viewmodel


import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.SoodinowWalletValueRepoModel
import com.paya.domain.repository.LowRiskInvestmentRepository
import com.paya.domain.tools.Resource
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@HiltViewModel
class WalletViewModel @Inject constructor(
    private val lowRiskInvestmentRepository: LowRiskInvestmentRepository
) : BaseViewModel() {

    val soodinowWalletValueRepoModelResourceMutableLiveData = MutableLiveData<Resource<SoodinowWalletValueRepoModel>>()

    fun getSoodinowWalletValue() {
        showLoading()
        viewModelScope.launch {
            val response = callResource(
                this@WalletViewModel, lowRiskInvestmentRepository.getSoodinowWalletValue()
            )
            soodinowWalletValueRepoModelResourceMutableLiveData.postValue(response)
        }

        hideLoading()

    }

}
