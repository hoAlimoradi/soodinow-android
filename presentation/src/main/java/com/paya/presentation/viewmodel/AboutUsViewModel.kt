package com.paya.presentation.viewmodel

import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.*
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
@HiltViewModel
class AboutUsViewModel @Inject constructor(
    private val getAboutUsContentUseCase: UseCase<Unit, List<AboutUsModel>>
) : BaseViewModel() {

    val aboutUSListMutableLiveData = MutableLiveData<Resource<List<AboutUsModel>>>()

    fun getWhySoodinows() {
        viewModelScope.launch {

            val response  = callResource(this@AboutUsViewModel,getAboutUsContentUseCase.action(Unit))
            aboutUSListMutableLiveData.value = response
        }

    }

}

