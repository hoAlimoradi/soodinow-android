package com.paya.presentation.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableField
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentLoginDialogBinding
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.shortToast

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val TITLE = "TITLE"
private const val HINT_USERNAME= "HINT_USERNAME"
private const val HINT_PASSWORD= "HINT_PASSWORD"
private const val SUBMIT_TITLE= "SUBMIT_TITLE"
private const val CANCEL_TITLE= "CANCEL_TITLE"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginDialogFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginDialogFragment : DialogFragment() {
	// TODO: Rename and change types of parameters
	private var title: String? = null
	private var hintUsername: String? = null
	private var hintPassword: String? = null
	private var submitTitle: String? = null
	private var cancelTitle: String? = null
	private  var username = ObservableField<String?>()
	private  var password = ObservableField<String?>()
	
	private lateinit var mBinding: FragmentLoginDialogBinding
	var callBack: CallBack? = null
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			title = it.getString(TITLE)
			hintUsername = it.getString(HINT_USERNAME)
			hintPassword = it.getString(HINT_PASSWORD)
			submitTitle = it.getString(SUBMIT_TITLE)
			cancelTitle = it.getString(CANCEL_TITLE)
		}
	}
	
	override fun onResume() {
		super.onResume()
		dialog?.window?.let {
			Utils.setTransparentBackgroundDialog(it)
			Utils.setAutomaticSizeHeightDialog(requireActivity(),it) }
	}
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_login_dialog,container,false)
		mBinding.titleDialog = title
		mBinding.hintUsername = hintUsername
		mBinding.hintPassword = hintPassword
		mBinding.submitTitle = submitTitle
		mBinding.cancelTitle = cancelTitle
		mBinding.username = username
		mBinding.password = password
		mBinding.lifecycleOwner = this
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		mBinding.submitBtn.setOnClickListener {
			if (username.get().isNullOrBlank()){
				return@setOnClickListener
			}
			if (password.get().isNullOrBlank()){
				return@setOnClickListener
			}
			if (username.get().toString().length != 11 ||
				!username.get().toString().startsWith("09")){
				shortToast("شماره تلفن صحیح نمیباشد")
				return@setOnClickListener
			}
			username.get().toString().forEach {
				if (!it.isDigit()){
					shortToast("شماره تلفن صحیح نمیباشد")
					return@setOnClickListener
				}
			}
			callBack?.success(username.get().toString(),password.get().toString())
			
		}
		mBinding.cancelBtn.setOnClickListener {
			callBack?.cancel()
		}
		mBinding.toolbar.closeBtn.setOnClickListener {
			dismissAllowingStateLoss()
		}
	}

	fun onActionsListener(callback: CallBack){
		this.callBack = callback
	}

	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment LoginDialogFragment.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance(title: String,hintUsername: String,hintPassword: String,submitTitle: String,cancelTitle: String) =
			LoginDialogFragment().apply {
				arguments = Bundle().apply {
					putString(TITLE,title)
					putString(HINT_USERNAME,hintUsername)
					putString(HINT_PASSWORD,hintPassword)
					putString(SUBMIT_TITLE,submitTitle)
					putString(CANCEL_TITLE,cancelTitle)
				}
			}
	}
	
	interface CallBack {
		fun success(param1:String,param2:String)
		fun cancel()
	}
}