package com.paya.presentation.ui.login

import android.Manifest
import android.app.KeyguardManager
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyPermanentlyInvalidatedException
import android.security.keystore.KeyProperties
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentLoginBinding
import com.paya.presentation.utils.*
import com.paya.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.security.*
import java.util.concurrent.Executor
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.NoSuchPaddingException
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec
import javax.security.cert.CertificateException
import kotlin.jvm.Throws

@AndroidEntryPoint
class LoginFragment : BaseFragment<LoginViewModel>() {

	private var biometricPrompt: BiometricPrompt? = null
	private var mBinding: FragmentLoginBinding? = null
	private val mViewModel: LoginViewModel by viewModels()

	private val KEY_NAME = "SOODINOW_KEY"
	private lateinit var cipher: Cipher
	private lateinit var keyStore: KeyStore
	private lateinit var keyGenerator: KeyGenerator
	private lateinit var cryptoObject: BiometricPrompt.CryptoObject
	private lateinit var fingerprintManager: FingerprintManager
	private lateinit var keyguardManager: KeyguardManager
	private lateinit var key: SecretKey

	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = FragmentLoginBinding.inflate(
			inflater,
			container,
			false
		)
		return mBinding?.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observe(mViewModel.loginResource, ::checkLoginStatus)
		observe(mViewModel.mobile, ::setMobile)
		mBinding?.apply {
			fingerprint.setOnClickListener {
				initFingerprint()
			}
			forgetPassword.setOnClickListener {
				findNavController().navigate(
					LoginFragmentDirections.actionLoginFragmentToForgotPasswordFragment()
				)
			}

			submitButton.setOnClickListener {
				login()
			}
		}

	}

	private fun login() {
		mBinding?.apply {
			phoneNumber.setError("")
			passwordLayout.setError("")
			if (phoneNumber.getText().isEmpty()) {
				phoneNumber.setError(getString(R.string.empty_mobile))
				return
			}
			if (!phoneNumber.getText().isMobile()) {
				phoneNumber.setError(getString(R.string.error_mobile))
				return
			}
			if (passwordLayout.getText().isEmpty()) {
				passwordLayout.setError(getString(R.string.empty_password))
				return
			}
			if (!passwordLayout.getText().isSecretPassword()) {
				passwordLayout.setError(getString(R.string.secret_password_error))
				return
			}
			mViewModel.login(phoneNumber.getText(), passwordLayout.getText())
		}
	}
	private fun setMobile(mobile: String) {
		mBinding?.apply {
			phoneNumber.setText(mobile)
		}
	}

	private fun checkLoginStatus(resource: Resource<Any>) {
		if (resource.status == Status.SUCCESS) {
			findNavController().navigate(
				R.id.navigateToHomeFragment
			)
		}
	}

	override fun onDestroyView() {
		mBinding = null
		biometricPrompt = null
		super.onDestroyView()
	}

	override val baseViewModel: BaseViewModel
		get() = mViewModel

	private fun initFingerprint() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			//Get an instance of KeyguardManager and FingerprintManager//
			keyguardManager =
				requireActivity().getSystemService(AppCompatActivity.KEYGUARD_SERVICE) as KeyguardManager
			fingerprintManager =
				requireActivity().getSystemService(AppCompatActivity.FINGERPRINT_SERVICE) as FingerprintManager

			//Check whether the device has a fingerprint sensor//
			if (!fingerprintManager.isHardwareDetected) {
				// If a fingerprint sensor isn’t available, then inform the user that they’ll be unable to use your app’s fingerprint functionality//
				shortToast(getString(R.string.error_device_does_not_support_fingerprint))
			}
			//Check whether the user has granted your app the USE_FINGERPRINT permission//
			if (ActivityCompat.checkSelfPermission(
					requireContext(),
					Manifest.permission.USE_FINGERPRINT
				) != PackageManager.PERMISSION_GRANTED
			) {
				// If your app doesn't have this permission, then display the following text//
				shortToast(getString(R.string.error_fingerprint_permission))
			}

			//Check that the user has registered at least one fingerprint//
			if (!fingerprintManager.hasEnrolledFingerprints()) {
				// If the user hasn’t configured any fingerprints, then display the following message//
				longToast(getString(R.string.error_fingerprint_not_configure))
			}

			//Check that the lockscreen is secured//
			if (!keyguardManager.isKeyguardSecure) {
				// If the user hasn’t secured their lockscreen with a PIN password or pattern, then display the following text//
				longToast(getString(R.string.error_lock_screen_not_configure))
			} else {
				try {
					generateKey()
				} catch (e: FingerprintException) {
					e.printStackTrace()
				}
				if (initCipher()) {
					//If the cipher is initialized successfully, then create a CryptoObject instance//
					cryptoObject = BiometricPrompt.CryptoObject(cipher)
					val executor = ContextCompat.getMainExecutor(requireActivity().applicationContext)
					authUser(executor, cryptoObject)
				}
			}
		}
	}

	//Create the generateKey method that we’ll use to gain access to the Android keystore and generate the encryption key//
	@RequiresApi(Build.VERSION_CODES.M)
	@Throws(FingerprintException::class)
	private fun generateKey() {
		try {
			// Obtain a reference to the Keystore using the standard Android keystore container identifier (“AndroidKeystore”)//
			keyStore = KeyStore.getInstance("AndroidKeyStore")
			keyStore.load(null)

		} catch (exc: KeyStoreException) {
			exc.printStackTrace()
			throw FingerprintException(exc)
		} catch (exc: NoSuchAlgorithmException) {
			exc.printStackTrace()
			throw FingerprintException(exc)
		} catch (exc: NoSuchProviderException) {
			exc.printStackTrace()
			throw FingerprintException(exc)
		} catch (exc: InvalidAlgorithmParameterException) {
			exc.printStackTrace()
			throw FingerprintException(exc)
		} catch (exc: CertificateException) {
			exc.printStackTrace()
			throw FingerprintException(exc)
		} catch (exc: IOException) {
			exc.printStackTrace()
			throw FingerprintException(exc)
		}
	}

	@RequiresApi(Build.VERSION_CODES.M)
	fun initCipher(): Boolean {
		cipher = try {
			//Obtain a cipher instance and configure it with the properties required for fingerprint authentication//
			Cipher.getInstance(
				KeyProperties.KEY_ALGORITHM_AES + "/"
						+ KeyProperties.BLOCK_MODE_CBC + "/"
						+ KeyProperties.ENCRYPTION_PADDING_PKCS7
			)
		} catch (e: NoSuchAlgorithmException) {
			throw RuntimeException("Failed to get Cipher", e)
		} catch (e: NoSuchPaddingException) {
			throw RuntimeException("Failed to get Cipher", e)
		}
		return try {
			keyStore.load(null)
			if (!keyStore.containsAlias(KEY_NAME))
				return false
			if (mViewModel.getPassword() == null)
				return false
			key = keyStore.getKey(
				KEY_NAME,
				null
			) as SecretKey
			val encodedIv = mViewModel.getIv()!!
			val ivBytes = Base64.decode(encodedIv, Base64.DEFAULT)
			cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(ivBytes))
			//Return true if the cipher has been initialized successfully//
			true
		} catch (e: KeyPermanentlyInvalidatedException) {
			//Return false if cipher initialization failed//
			false
		} catch (e: KeyStoreException) {
			throw RuntimeException("Failed to init Cipher", e)
		} catch (e: CertificateException) {
			throw RuntimeException("Failed to init Cipher", e)
		} catch (e: UnrecoverableKeyException) {
			throw RuntimeException("Failed to init Cipher", e)
		} catch (e: IOException) {
			throw RuntimeException("Failed to init Cipher", e)
		} catch (e: NoSuchAlgorithmException) {
			throw RuntimeException("Failed to init Cipher", e)
		} catch (e: InvalidKeyException) {
			throw RuntimeException("Failed to init Cipher", e)
		}
	}

	private fun authUser(executor: Executor, crypto: BiometricPrompt.CryptoObject) {
		val promptInfo = BiometricPrompt.PromptInfo.Builder()
			.setTitle(getString(R.string.authentication_title))
			.setSubtitle(getString(R.string.authentication_subtitle))
			.setDescription(getString(R.string.login_authentication_description))
			.setNegativeButtonText(getString(R.string.cancel))
			.build()

		 biometricPrompt = BiometricPrompt(this, executor,
			object : BiometricPrompt.AuthenticationCallback() {
				@RequiresApi(Build.VERSION_CODES.M)
				override fun onAuthenticationSucceeded(
					result: BiometricPrompt.AuthenticationResult
				) {
					super.onAuthenticationSucceeded(result)
					biometricPrompt?.apply { cancelAuthentication()}
					shortToast(getString(R.string.authentication_successful))
					cipher = result.cryptoObject!!.cipher!!
					val p = mViewModel.getPassword()
					val decrypted = p?.let { decrypt(p) }
 					decrypted?.let {
						mBinding?.apply {
							passwordLayout.setText(it)
							login()
						}
					}

				}

				override fun onAuthenticationError(
					errorCode: Int, errString: CharSequence
				) {
					super.onAuthenticationError(errorCode, errString)
					biometricPrompt?.apply { cancelAuthentication()}
					longToast(getString(R.string.error_msg_auth_error, errString))
				}

				override fun onAuthenticationFailed() {
					super.onAuthenticationFailed()
					biometricPrompt?.apply { cancelAuthentication()}
					longToast(getString(R.string.error_msg_auth_failed))
				}
			})

		biometricPrompt?.apply{authenticate(promptInfo, cryptoObject)}
	}

	private class FingerprintException(e: Exception?) : Exception(e)

	@RequiresApi(Build.VERSION_CODES.M)
	fun encrypt(): String {
		try {
			val encodedBytes = cipher.doFinal("kiiiir".toByteArray())
			return Base64.encodeToString(encodedBytes, Base64.DEFAULT)
		} catch (e: Exception) {
			throw RuntimeException("Failed to encrypt password", e)
		}
	}

	fun decrypt(encrypted: String): String? {
		return try {
			val decrypted = cipher.doFinal(
				Base64.decode(encrypted.toByteArray(), Base64.DEFAULT)
			)
			String(Base64.decode(decrypted, Base64.DEFAULT))
		} catch (e: Exception) {
			shortToast(getString(R.string.default_error_msg))
			null
		}
	}

}