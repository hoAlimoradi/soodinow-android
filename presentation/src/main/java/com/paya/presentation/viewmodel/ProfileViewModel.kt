package com.paya.presentation.viewmodel

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.mikephil.charting.data.PieEntry
import com.paya.domain.models.repo.BoxHistoryRepoModel
import com.paya.domain.models.repo.BoxHistoryRequestModel
import com.paya.domain.models.repo.CircleChartDataRepoModel
import com.paya.domain.models.repo.ExitAccountRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.adapter.chartLablel.ChartLabelAdapter
import com.paya.presentation.ui.home.fragments.CardAccount
import com.paya.presentation.ui.model.PieChartModel
import com.paya.presentation.ui.profile.enum.FilterProfile
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getBoxHistoryUseCase: UseCase<BoxHistoryRequestModel, BoxHistoryRepoModel>,
    private val existAccountUseCase: UseCase<Unit, ExitAccountRepoModel>,
    private val existAccountWalletUseCase: UseCase<String, ExitAccountRepoModel>
) : BaseViewModel() {


    val pieChartStatus = MutableLiveData<PieChartModel>()
    private var pieChartModel: PieChartModel? = null
    private var profile = MutableLiveData<Resource<BoxHistoryRepoModel>>()
    val existOrderAccount = MutableLiveData<Resource<ExitAccountRepoModel>>()
    val existWalletAccount = MutableLiveData<Resource<ExitAccountRepoModel>>()
    val existAccount = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    val cardAccounts = mutableListOf<CardAccount>()
    var isOrderE = false
    var isWalletE = false

    var currentBoxId: Long = 0


    fun setErrorMessage(message: String) {
        errorMessage.value = message
    }

    fun getFirstExistAccount() {
        cardAccounts.clear()
        isOrderE = false
        isWalletE = false
        getExistAccount()
        getExistWalletAccount()
    }
    fun getExistAccount() {
        showLoading()
        isOrderE = false
        viewModelScope.launch {
            val resource = callResource(this@ProfileViewModel, existAccountUseCase.action(Unit))
            if (resource.status == Status.SUCCESS) {

                resource.data?.activeBox?.let {
                    it.forEach { activeBox ->
                        cardAccounts.add(CardAccount.newInstance(activeBox, false))
                    }


                }
            }
            hideLoading()
            existOrderAccount.postValue(resource)
            isOrderE = true
            concatExistAccount()
        }
    }

    fun getExistWalletAccount() {
        showLoading()
        isWalletE = false
        viewModelScope.launch {
            val resource = callResource(this@ProfileViewModel, existAccountWalletUseCase.action(""))
            if (resource.status == Status.SUCCESS) {
                resource.data?.activeBox?.let {
                    it.forEach { activeBox ->
                        cardAccounts.add(CardAccount.newInstance(activeBox, true))
                    }

                }
            }
            hideLoading()
            existWalletAccount.postValue(resource)
            isWalletE = true
            concatExistAccount()
        }
    }

    private fun concatExistAccount() {
        if (isOrderE && isWalletE) {
            if (cardAccounts.isEmpty() ||(existOrderAccount.value?.status == Status.ERROR && existWalletAccount.value?.status == Status.ERROR)) {
                setErrorMessage(
                    "شما حساب فعال ندارید \n" +
                            "ابتدا پیمان خود را با ما ببندید"
                )
                hideLoading()
                existAccount.postValue(false);
            } else {

                existAccount.postValue(true);
            }

        }
    }

    fun getProfile(
        boxId: Long,
        isWallet: Boolean
    ) {


        profile.value = Resource.loading(null)
        viewModelScope.launch {
            showLoading()
            val response = callResource(
                this@ProfileViewModel, getBoxHistoryUseCase.action(
                    BoxHistoryRequestModel(
                        boxId,
                        FilterProfile.week.name,
                        if (isWallet) "low_investment" else "fixed",
                        1
                    )
                )
            )
            response.data?.circleChart?.let {
                fillEntries(it)
            }
            hideLoading()
        }

    }

    private fun fillEntries(chartData: List<CircleChartDataRepoModel>) {
        viewModelScope.launch(Dispatchers.Main) {
            if (pieChartModel != null)
                return@launch
            pieChartModel = PieChartModel()
            pieChartModel?.let { pieChartModel ->
                if (chartData.isNotEmpty() && pieChartModel.chartLabels.isEmpty() && pieChartModel.entries.isEmpty()) {
                    var allSize: Long = 0
                    chartData.forEach {
                        allSize += it.quantity
                    }
                    chartData.forEachIndexed { _, pieChartData ->
                        if (pieChartModel.entries.size <= chartData.size)
                            pieChartModel.entries.add(PieEntry(((pieChartData.quantity * 100) / allSize).toFloat()))
                        if (pieChartModel.chartColor.size <= chartData.size)
                            pieChartModel.chartColor.add(Color.parseColor(pieChartData.color))
                        if (pieChartModel.chartLabels.size <= chartData.size)
                            pieChartModel.chartLabels.add(
                                ChartLabelAdapter.ChartLabelModel(
                                    pieChartData.name,
                                    pieChartData.color
                                )
                            )
                    }
                    pieChartStatus.value = pieChartModel
                }
            }
        }
    }


}

