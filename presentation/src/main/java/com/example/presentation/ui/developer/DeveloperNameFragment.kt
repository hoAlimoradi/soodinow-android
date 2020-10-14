package com.example.presentation.ui.developer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.domain.models.repo.DeveloperNameRepoModel
import com.example.presentation.R
import com.example.presentation.base.BaseFragment
import com.example.presentation.utils.observe
import com.example.presentation.viewmodel.DeveloperNameViewModel
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
	
	private fun showDeveloperName(devName : DeveloperNameRepoModel){
		tvFirstName.text = devName.first
		tvLastName.text = devName.last
	}
}