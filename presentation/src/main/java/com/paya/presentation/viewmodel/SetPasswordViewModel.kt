package com.paya.presentation.viewmodel

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel

class SetPasswordViewModel @ViewModelInject constructor(

) : ViewModel() {
	
	val password = ObservableField<String>()
	val repeatPassword = ObservableField<String>()
	
	fun submit(){
		val password = password.get()
		val repeatPassword = repeatPassword.get()
		
		if (password.isNullOrBlank() || repeatPassword.isNullOrBlank()){
		
		}
	}
	
}