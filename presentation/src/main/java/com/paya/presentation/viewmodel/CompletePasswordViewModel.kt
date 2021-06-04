package com.paya.presentation.viewmodel

import android.util.Log
import androidx.databinding.ObservableField
import javax.inject.Inject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paya.domain.models.repo.UserInfoRepoModel
import com.paya.domain.models.repo.SetPasswordRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.callResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
@HiltViewModel
class CompletePasswordViewModel @Inject constructor(
) : BaseViewModel() {
	

	
}