package com.paya.presentation.ui.hint

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentHintBinding
import com.paya.presentation.utils.getVersionName
import com.paya.presentation.viewmodel.HintViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HintFragment : BaseFragment<HintViewModel>() {

	private var mBinding: FragmentHintBinding? = null
	private val mViewModel: HintViewModel by viewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = FragmentHintBinding.inflate(
			inflater,
			container,
			false
		)

		return mBinding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		mBinding?.apply {
			register.setOnClickListener {
				findNavController().navigate(R.id.navigateToRegisterFragment)
			}
			login.setOnClickListener {
				findNavController().navigate(R.id.navigateFromHintToLoginFragment)
			}
			versionTxt.text = "  نسخه  ${context?.let { getVersionName(it) }}"
		}
		/*val appS = AppSignatureHelper(requireContext().applicationContext)
		Log.d("appSignatures", appS.appSignatures.toString())
		Log.d("appSignaturesHash", appS.hashCode().toString())*/
	}




	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
	}



	override val baseViewModel: BaseViewModel
		get() = mViewModel

}