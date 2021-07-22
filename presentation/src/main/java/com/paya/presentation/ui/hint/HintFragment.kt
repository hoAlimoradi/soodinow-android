package com.paya.presentation.ui.hint

import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.phone.SmsRetriever
import com.google.android.gms.tasks.Task
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentHintBinding
import com.paya.presentation.utils.AppSignatureHelper
import com.paya.presentation.utils.MySMSBroadcastReceiver
import com.paya.presentation.utils.getVersionName
import com.paya.presentation.viewmodel.HintViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HintFragment : BaseFragment<HintViewModel>() {

	private var mBinding: FragmentHintBinding? = null
	private val mViewModel: HintViewModel by viewModels()
	private var registerSms: MySMSBroadcastReceiver? = null
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

	override fun onResume() {
		super.onResume()
		//requestHint()
	}

	override fun onPause() {
		super.onPause()
		activity?.let { activity ->
			if (registerSms != null) {
				activity.unregisterReceiver(registerSms)
				registerSms = null
			}
		}
	}

	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
	}

	/*private fun requestHint() {

		val client = context?.let {
			SmsRetriever.getClient(it)
		}

		client?.let { client ->
			val task: Task<Void> = client.startSmsRetriever()

			task.addOnSuccessListener {
				Log.d("", "")
				// Successfully started retriever, expect broadcast intent
				// ...
				registerSms = MySMSBroadcastReceiver()
				registerSms?.initOTPListener(object : MySMSBroadcastReceiver.OTPReceiveListener {
					override fun onOTPReceived(otp: String) {
						Log.d("OTPReceiveListener", otp)
					}

					override fun onOTPTimeOut() {

					}

				})
				activity?.let { activity ->
					val filter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)
					activity.registerReceiver(registerSms, filter)

				}
			}

			task.addOnFailureListener{
				Log.d("", "")
				// Failed to start retriever, inspect Exception for more details
				// ...
			}
		}

	}*/

	override val baseViewModel: BaseViewModel
		get() = mViewModel

}