package com.paya.presentation.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.base.BaseViewModel

fun <T> LifecycleOwner.observe(liveData: LiveData<T>?,action: (t: T) -> Unit) {
	liveData?.observe(this, Observer { t -> action(t) })
}

fun <T> callResource(viewModel: BaseViewModel,resource: Resource<T>,isShowError: Boolean = true) : Resource<T> {
	if (resource.code == 401) {
		viewModel.unAuthorized(resource.message!!) {  }
	} else if(resource.code == 1033){
		viewModel.existProfileUser()
	} else if (resource.code == 1009) {
		viewModel.farabiAuth()
	} else if(resource.code == 1051){
		viewModel.showError(resource.message ?: "عملیات شما با خطا مواجه شد","هنگام دریافت مجوز، شما را با پیامک مطلع خواهیم ساخت.",isShowError)
	}
	else if (resource.status == Status.ERROR) {
		viewModel.showError(resource.message ?: "عملیات شما با خطا مواجه شد",null,isShowError)
	}
	return resource
}