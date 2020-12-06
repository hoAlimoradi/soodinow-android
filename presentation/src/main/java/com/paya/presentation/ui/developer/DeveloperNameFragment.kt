package com.paya.presentation.ui.developer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.paya.domain.models.repo.DeveloperNameRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.DeveloperNameViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_developer_name.*

@AndroidEntryPoint
class DeveloperNameFragment : BaseFragment<DeveloperNameViewModel>() {
	
	private val viewModel: DeveloperNameViewModel by viewModels()
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View = inflater.inflate(
		R.layout.fragment_developer_name,
		container,
		false
	)
	
	override fun onViewCreated(
		view: View,
		savedInstanceState: Bundle?
	) {
		super.onViewCreated(view,savedInstanceState)
		
		viewModel.getDeveloperName()
		
		observe(viewModel.developerName, ::showDeveloperName)
	}
	
	private fun showDeveloperName(devNameResource : Resource<DeveloperNameRepoModel>){
		if(devNameResource.status == Status.SUCCESS){
			val devName = devNameResource.data ?: return
			tvFirstName.text = devName.first
			tvLastName.text = devName.last
		}
	}
	
	override val baseViewModel: BaseViewModel
		get() = viewModel
}