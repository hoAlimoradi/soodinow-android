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

fun <T> callResource(viewModel: BaseViewModel,resource: Resource<T>) : Resource<T> {
	if (resource.status == Status.UNAUTHORIZED) {
		viewModel.unAuthorized(resource.message!!) {  }
	} else if (resource.status == Status.FARABITOKEN) {
		viewModel.farabiAuth()
	}
	return resource
}