package com.paya.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.paya.domain.tools.UseCase
import com.paya.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HintViewModel @Inject constructor(
    private val isLoginUseCase: UseCase<Unit, Boolean>
) : BaseViewModel() {

    enum class UserState { IS_HINT_SHOWED, IS_PASSWORD_SET, NONE }

    var isLogin = false

    init {
        isLogin()
    }

    private fun isLogin() {
        viewModelScope.launch {
            isLoginUseCase.action(Unit).data?.let {
                isLogin = it
            }
        }
    }

}