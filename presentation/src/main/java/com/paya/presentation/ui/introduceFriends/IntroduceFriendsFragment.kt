package com.paya.presentation.ui.introduceFriends

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.GetAppLinkRepoModel
import com.paya.domain.tools.Resource
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentIntroduceFirendsBinding
import com.paya.presentation.utils.*
import com.paya.presentation.viewmodel.IntroduceFriendsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroduceFriendsFragment : BaseFragment<IntroduceFriendsViewModel>() {

	private val mViewModel: IntroduceFriendsViewModel by viewModels()
	private var mBinding: FragmentIntroduceFirendsBinding? = null


	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = FragmentIntroduceFirendsBinding.inflate(inflater, container, false)
		return mBinding?.root
	}

	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
	}
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observe(mViewModel.status, ::readyGetAppLink)
		mBinding?.apply {
			toolbar.backClick = {
				findNavController().popBackStack()
			}
			copyBtn.setOnClickListener {
				context?.let { it1 ->
					link.text.toString().copy(it1)
					Toast.makeText(it1, "کپی شد", Toast.LENGTH_SHORT).show()
				}
			}
			telegramBtn.setOnClickListener {
				activity?.let { activity -> activity.intentMessageTelegram(link.text.toString()) }
			}
			instagramBtn.setOnClickListener {
				activity?.let { activity -> activity.intentMessageInstagram(link.text.toString()) }
			}
			whatsAppBtn.setOnClickListener {
				activity?.let { activity -> activity.intentMessageWhatsApp(link.text.toString()) }
			}
		}
	}

	private fun readyGetAppLink(resource: Resource<GetAppLinkRepoModel>) {
		resource.data?.let { mBinding?.apply { link.text = it.link } }
	}

	override val baseViewModel: BaseViewModel
		get() = mViewModel

}