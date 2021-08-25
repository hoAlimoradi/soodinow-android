package com.paya.presentation.ui.settings

import android.Manifest
import android.app.KeyguardManager
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.security.keystore.KeyGenParameterSpec
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
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentSettingsBinding
import com.paya.presentation.ui.createPersonalAccount.FirstInformationFragment
import com.paya.presentation.ui.custom.LoginDialogFragment
import com.paya.presentation.utils.UrlLinks.frequentlyAskedQuestionsUrl
import com.paya.presentation.utils.UrlLinks.regulationUrl
import com.paya.presentation.utils.longToast
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.shortToast
import com.paya.presentation.viewmodel.SettingViewModel
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
import kotlin.system.exitProcess


@AndroidEntryPoint
class SettingsFragment : BaseFragment<SettingViewModel>() {
	
	private val mViewModel: SettingViewModel by viewModels()
	private var mBinding: FragmentSettingsBinding? = null
	private var password: String? = null

	private val KEY_NAME = "SOODINOW_KEY"
	private lateinit var cipher: Cipher
	private lateinit var keyStore: KeyStore
	private lateinit var keyGenerator: KeyGenerator
	private lateinit var cryptoObject: BiometricPrompt.CryptoObject
	private lateinit var fingerprintManager: FingerprintManager
	private lateinit var keyguardManager: KeyguardManager
	private lateinit var key: SecretKey

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = FragmentSettingsBinding.inflate(inflater,  container, false)
		return mBinding?.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observe(mViewModel.status, ::ready)
		observe(mViewModel.loginResource, ::checkLoginStatus)
		observe(mViewModel.statusLogout, ::logout)
		observe(mViewModel.mobile, ::readyMobile)
		initViews()
		mViewModel.getMobile()
	}

	private fun logout(param:Unit) {
		exitProcess(-1)
	}
	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
	}
	private fun initViews() {

		mBinding?.apply {
			if (mViewModel.getPassword() != null){
				switchCompat.isChecked = true
			}
			switchCompat.setOnCheckedChangeListener { _, isChecked ->
				if (!isChecked) {
					mViewModel.setPassword(null)
					return@setOnCheckedChangeListener
				}
//			initFingerprint()
				val dialog = LoginDialogFragment.newInstance(
					"ابتدا نام کاربری و رمز عبور خود را وارد نمایید",
					getString(R.string.username_or_phone),
					getString(R.string.password),
					getString(R.string.submit_and_continue),
					"انصراف"
				)
				dialog.onActionsListener(
					object : LoginDialogFragment.CallBack {
						override fun success(param1: String, param2: String) {
							password = param2
							dialog.dismiss()
							mViewModel.login(param1, param2)
						}

						override fun cancel() {
							switchCompat.isChecked = false
						}

					}
				)
				dialog.show(parentFragmentManager, "login")
			}
			changePassword.setOnClickListener {
				getFindViewController()?.navigate(
					R.id.changePhoneNumberFragment
				)
			}
			myProfile.setOnClickListener {
				getFindViewController()?.navigate(
					R.id.firstInformation, FirstInformationFragment.newBundle(false)
				)
			}
			share.setOnClickListener {
				getFindViewController()?.navigate(
					R.id.introduceFriendsFragment
				)
			}
			support.setOnClickListener {
				getFindViewController()?.navigate(
					R.id.support
				)
			}
			financial.setOnClickListener {
				getFindViewController()?.navigate(
					R.id.financialReportFragment
				)
			}

			exitApp.setOnClickListener {
				mViewModel.logout()
			}

			termsConditions.setOnClickListener {
				startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(regulationUrl)))
			}

			askedQuestion.setOnClickListener {
				startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(frequentlyAskedQuestionsUrl)))
			}
		}
	}

	private fun checkLoginStatus(resource: Resource<Any>){
		if (resource.status == Status.SUCCESS){
			initFingerprint()
		}
	}

	private fun initFingerprint() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			//Get an instance of KeyguardManager and FingerprintManager//
			keyguardManager = requireActivity().getSystemService(AppCompatActivity.KEYGUARD_SERVICE) as KeyguardManager
			fingerprintManager = requireActivity().getSystemService(AppCompatActivity.FINGERPRINT_SERVICE) as FingerprintManager

			//Check whether the device has a fingerprint sensor//
			if (!fingerprintManager.isHardwareDetected) {
				// If a fingerprint sensor isn’t available, then inform the user that they’ll be unable to use your app’s fingerprint functionality//
				shortToast(getString(R.string.error_device_does_not_support_fingerprint))
				mBinding?.apply{switchCompat.isChecked = false}
			}
			//Check whether the user has granted your app the USE_FINGERPRINT permission//
			if (ActivityCompat.checkSelfPermission(
					requireContext(),
					Manifest.permission.USE_FINGERPRINT
				) != PackageManager.PERMISSION_GRANTED
			) {
				// If your app doesn't have this permission, then display the following text//
				shortToast(getString(R.string.error_fingerprint_permission))
				mBinding?.apply{switchCompat.isChecked = false}
			}

			//Check that the user has registered at least one fingerprint//
			if (!fingerprintManager.hasEnrolledFingerprints()) {
				// If the user hasn’t configured any fingerprints, then display the following message//
				longToast(getString(R.string.error_fingerprint_not_configure))
				mBinding?.apply{switchCompat.isChecked = false}
			}

			//Check that the lockscreen is secured//
			if (!keyguardManager.isKeyguardSecure) {
				// If the user hasn’t secured their lockscreen with a PIN password or pattern, then display the following text//
				longToast(getString(R.string.error_lock_screen_not_configure))
				mBinding?.apply{switchCompat.isChecked = false}
			} else {
				try {
					generateKey()
				} catch (e: FingerprintException) {
					e.printStackTrace()
				}
				if (initCipher()) {
					//If the cipher is initialized successfully, then create a CryptoObject instance//
					cryptoObject = BiometricPrompt.CryptoObject(cipher)
					val executor = ContextCompat.getMainExecutor(requireContext())
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

			//Generate the key//
			keyGenerator =
				KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")

			//Initialize an empty KeyStore//
			keyStore.load(null)

			//Initialize the KeyGenerator//
			keyGenerator.init(
				KeyGenParameterSpec.Builder(
					KEY_NAME,
					KeyProperties.PURPOSE_ENCRYPT or
							KeyProperties.PURPOSE_DECRYPT
				)
					.setBlockModes(KeyProperties.BLOCK_MODE_CBC) //Configure this key so that the user has to confirm their identity with a fingerprint each time they want to use it//
					.setUserAuthenticationRequired(true)
					.setEncryptionPaddings(
						KeyProperties.ENCRYPTION_PADDING_PKCS7
					)
					.build()
			)

			//Generate the key//
			keyGenerator.generateKey()
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
			key = keyStore.getKey(
				KEY_NAME,
				null
			) as SecretKey
			cipher.init(Cipher.ENCRYPT_MODE, key)
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
			.setDescription(getString(R.string.setting_authentication_description))
			.setNegativeButtonText(getString(R.string.cancel))
			.build()

		val biometricPrompt = BiometricPrompt(this, executor,
			object : BiometricPrompt.AuthenticationCallback() {
				@RequiresApi(Build.VERSION_CODES.M)
				override fun onAuthenticationSucceeded(
					result: BiometricPrompt.AuthenticationResult
				) {
					super.onAuthenticationSucceeded(result)
					shortToast(getString(R.string.authentication_successful))
					val encrypted = encrypt()
					mViewModel.setPassword(encrypted)
				}

				override fun onAuthenticationError(
					errorCode: Int, errString: CharSequence
				) {
					super.onAuthenticationError(errorCode, errString)
					longToast(getString(R.string.error_msg_auth_error, errString))
					mBinding?.apply{switchCompat.isChecked = false}
				}

				override fun onAuthenticationFailed() {
					super.onAuthenticationFailed()
					longToast(getString(R.string.error_msg_auth_failed))
					mBinding?.apply{switchCompat.isChecked = false}
				}
			})

		biometricPrompt.authenticate(promptInfo, crypto)
	}

	private class FingerprintException(e: Exception?) : Exception(e)

	@RequiresApi(Build.VERSION_CODES.M)
	fun encrypt(): String {
		try {
			val encodedBytes = cipher.doFinal(
				Base64.encode(password?.toByteArray(), Base64.DEFAULT)
			)
			val cipherIv = cipher.iv
			val encodeCipherIv = Base64.encodeToString(cipherIv, Base64.DEFAULT)
			mViewModel.setIv(encodeCipherIv)
			return Base64.encodeToString(encodedBytes, Base64.DEFAULT)
		} catch (e: Exception) {
			throw RuntimeException("Failed to encrypt password", e)
		}
	}

	fun decrypt(encrypted: String): String {
		try {
			val r = SecureRandom()
			val ivBytes = ByteArray(16)
			r.nextBytes(ivBytes)
			cipher.init(Cipher.DECRYPT_MODE, key, IvParameterSpec(ivBytes))
			val decrypted = cipher.doFinal(encrypted.toByteArray())
			return Base64.encodeToString(decrypted, Base64.DEFAULT)
		} catch (e: Exception) {
			throw RuntimeException("Failed to encrypt password", e)
		}
	}

	private fun ready(resource: Resource<ProfileRepoModel>) {
	}
	private fun readyMobile(mobile: String) {
		mBinding?.phoneTxt?.apply {
			text = mobile.replaceFirst("+98","0")
		}
	}

	override val baseViewModel: BaseViewModel
		get() = mViewModel
	
}