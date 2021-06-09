package com.paya.presentation.ui.custom

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentLoginDialogBinding
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.isSecretPassword
import com.paya.presentation.utils.setWidthPercent
import kotlinx.android.synthetic.main.fragment_login_dialog.*

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
	private var username: String? = null
	private var password: String? = null

	private var mBinding: FragmentLoginDialogBinding? = null
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
		}
		setWidthPercent(90)
	}
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = FragmentLoginDialogBinding.inflate(inflater, container, false)
		return mBinding?.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		mBinding?.let { mBinding ->
			title?.let { mBinding.toolbar.setTitle(it) }
			hintUsername?.let {
				mBinding.usernameEditText.setLabelText(it)
			}
			hintPassword?.let {
				mBinding.passwordEdiText.setLabelText(it)
			}
			submitTitle?.let { mBinding.submitBtn.text = it }
			cancelTitle?.let { mBinding.cancelBtn.text = it }
			username?.let { mBinding.usernameEditText.setText(it) }
			password?.let { mBinding.passwordEdiText.setText(it) }
			mBinding.submitBtn.setOnClickListener {
				mBinding.passwordEdiText.setError("")
				mBinding.usernameEditText.setError("")
				username = mBinding.usernameEditText.getText()
				password = mBinding.passwordEdiText.getText()
				if (mBinding.usernameEditText.getText().isEmpty()) {
					mBinding.usernameEditText.setError(getString(R.string.empty_mobile))
					return@setOnClickListener
				}
				if (mBinding.usernameEditText.getText().length != 11||!mBinding.usernameEditText.getText().startsWith("09")) {
					mBinding.usernameEditText.setError(getString(R.string.error_mobile))
					return@setOnClickListener
				}
				if ( mBinding.passwordEdiText.getText().isEmpty()) {
					mBinding.passwordEdiText.setError(getString(R.string.empty_password))
					return@setOnClickListener
				}
				if (! mBinding.passwordEdiText.getText().isSecretPassword()) {
					mBinding.passwordEdiText.setError(getString(R.string.secret_password_error))
					return@setOnClickListener
				}
				username?.let { username ->
					password?.let { password ->
						username.forEach {
							if (!it.isDigit()) {
								usernameEditText.setError(getString(R.string.error_phone))
								return@setOnClickListener
							}
						}

						callBack?.success(username, password)
					}
				}

			}
			mBinding.cancelBtn.setOnClickListener {
				callBack?.cancel()
				dismissAllowingStateLoss()
			}
			mBinding.toolbar.backClick = {
				dismissAllowingStateLoss()
			}
		}
	}

	fun onActionsListener(callback: CallBack) {
		this.callBack = callback
	}

	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
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