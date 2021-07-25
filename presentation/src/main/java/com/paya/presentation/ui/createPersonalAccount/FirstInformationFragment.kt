package com.paya.presentation.ui.createPersonalAccount

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.ProfileExtraRepoModel
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.models.repo.ProvinceRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentFirstInformationBinding
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.isValidEmail
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.picker.PickerViewDialog
import com.paya.presentation.viewmodel.FirstInformationViewModel
import dagger.hilt.android.AndroidEntryPoint
import ir.hamsaa.persiandatepicker.Listener
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.util.PersianCalendar

private const val ARG_IS_NEXT_PAGE: String = "next_page"
@AndroidEntryPoint
class FirstInformationFragment : BaseFragment<FirstInformationViewModel>() {
	private val mViewModel: FirstInformationViewModel by viewModels()
	private var mBinding: FragmentFirstInformationBinding? = null
	private var picker: PersianDatePickerDialog? = null
	private var isNextPage: Boolean = false
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			isNextPage = it.getBoolean(ARG_IS_NEXT_PAGE, false)
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding =
			FragmentFirstInformationBinding.inflate(inflater, container, false)

		return mBinding?.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		mBinding?.apply {
			toolbar.backClick = {
				findNavController().popBackStack()
			}
			birthDate.setOnClickListener {
				showDatePicker()
			}

			gender.setOnClickListener {
				fragmentManager?.let { it1 ->
					PickerViewDialog()
						.selectionIndex(
							if (gender.getText() == "مرد" || gender.getText()
									.isNullOrEmpty()
							) 0 else 1
						)
						.setItems(mutableListOf("مرد", "زن"))
						.setTitle(getString(R.string.gender))
						.setListenerSelection { item, index ->
							gender.setText(item)
						}.show(it1, "gender")
				}
			}

			city.setOnClickListener {
				if (mViewModel.cityList.size > 0)
					fragmentManager?.let { it1 ->
						PickerViewDialog()
							.selectionIndex(mViewModel.citySelection)
							.setItems(mViewModel.cityList[mViewModel.provinceSelection].cities.map { it.name })
							.setTitle(getString(R.string.city))
							.setListenerSelection { item, index ->
								mViewModel.citySelection = index
								city.setText(item)
							}.show(it1, "city")
					}
			}

			province.setOnClickListener {
				if (mViewModel.cityList.size > 0)
					fragmentManager?.let { it1 ->
						PickerViewDialog()
							.selectionIndex(mViewModel.provinceSelection)
							.setItems(mViewModel.cityList.map { it.name })
							.setTitle(getString(R.string.province))
							.setListenerSelection { item, index ->
								mViewModel.provinceSelection = index
								province.setText(item)
							}.show(it1, "province")
					}
			}
			insertBtn.setOnClickListener {
				updateProfile()
			}
		}
		observe(mViewModel.statusUpdate, ::onReadyUpdateProfile)
		observe(mViewModel.statusCity, ::onReadyCity)
		observe(mViewModel.statusProfile, ::onReadyProfile)
		setDate(PersianCalendar())
	}

	private fun updateProfile() {
		mBinding?.let { mBinding ->
			var isError = false
			mBinding.firstName.setError("")
			mBinding.lastName.setError("")
			mBinding.phone.setError("")
			mBinding.email.setError("")
			mBinding.birthDate.setError("")
			mBinding.shaba.setError("")
			mBinding.gender.setError("")
			mBinding.city.setError("")
			mBinding.province.setError("")
			mBinding.address.setError("")


			val firstName = mBinding.firstName.getText()
			val lastName = mBinding.lastName.getText()
			val phone = mBinding.phone.getText()
			val email = mBinding.email.getText()
			val birthDay = mViewModel.birthDay.let { Utils.convertToDate(it) }
			val bban = mBinding.shaba.getText()
			val gender =
				if (mBinding.gender.getText() == "مرد" || mBinding.gender.getText()
						.isEmpty()
				) "m" else "f"
			val city = mBinding.city.getText()
			val province = mBinding.province.getText()
			val address = mBinding.address.getText()

			if (firstName.isEmpty()) {
				mBinding.firstName.setError("لطفا نام را وارد کنید")
				isError = true

			}
			if (lastName.isEmpty()) {
				mBinding.lastName.setError("لطفا نام‌ و خانوادگی را وارد کنید")
				isError = true

			}
			if (phone.isEmpty()) {
				mBinding.phone.setError("لطفا تلفن را وارد کنید")
				isError = true

			}
			if (email.isEmpty()) {
				mBinding.email.setError("لطفا ایمیل را وارد کنید")
				isError = true

			}
			if (email.isNotEmpty() && !isValidEmail(email)) {
				mBinding.email.setError("ایمیل وارد شده اشتباه است")
				isError = true

			}

			if (birthDay.isEmpty()) {
				mBinding.birthDate.setError("لطفا تاربخ تولد را وارد کنید")
				isError = true
			}

			if (bban.isEmpty()) {
				mBinding.shaba.setError("لطفا شماره شبا را وارد کنید")
				isError = true

			}
			if (gender.isEmpty()) {
				mBinding.gender.setError("لطفا جنسیت را انتخاب کنید")
				isError = true
			}
			if (city.isEmpty()) {
				mBinding.city.setError("لطفا شهر را انتخاب کنید")
				isError = true

			}
			if (province.isEmpty()) {
				mBinding.province.setError("لطفا استان را انتخاب کنید")
				isError = true
			}
			if (address.isEmpty()) {
				mBinding.address.setError("لطفا آدرس را وارد کنید")
				isError = true
			}
			if (isError)
				return
			mViewModel.updateProfile(
				firstName,
				lastName,
				phone,
				email,
				birthDay,
				bban,
				gender,
				city,
				province,
				address
			)
		}
	}

	private fun showDatePicker() {
		picker = PersianDatePickerDialog(requireContext())
			.setPositiveButtonString("باشه")
			.setNegativeButton("بیخیال")
			.setTodayButton("امروز")
			.setTodayButtonVisible(true)
			.setInitDate(if (mViewModel.birthDay == null) PersianCalendar() else mViewModel.birthDay)
			.setMaxYear(PersianDatePickerDialog.THIS_YEAR)
			.setMinYear(1300)
			.setActionTextColor(Color.GRAY)
			.setListener(object : Listener {
				override fun onDateSelected(persianCalendar: PersianCalendar) {
					setDate(persianCalendar)
				}

				override fun onDismissed() {}
			})
		picker!!.show()
	}
	
	private fun onReadyUpdateProfile(resource: Resource<ProfileRepoModel>) {
		when (resource.status) {
			Status.SUCCESS -> {
				context.let {
					Toast.makeText(it, "پروفایل با موفقیت آپدیت شد", Toast.LENGTH_SHORT)
						.show()
				}
				findNavController().popBackStack()

			}
			Status.ERROR -> {
				mBinding?.let { mBinding ->
				resource.extra?.let {
					if (it is ProfileExtraRepoModel) {
						mBinding.firstName.setError(it.firstName)
						mBinding.lastName.setError(it.lastName)
						mBinding.phone.setError(it.phone)
						mBinding.email.setError(it.email)
						mBinding.birthDate.setError(it.birthDay)
						mBinding.shaba.setError(it.bban)
						mBinding.gender.setError(it.gender)
						mBinding.city.setError(it.city)
						mBinding.province.setError(it.state)
						mBinding.address.setError(it.address)
					}
				}

			}
		}

			else -> return
		}

	}

	private fun onReadyCity(resource: Resource<List<ProvinceRepoModel>>) {
		when (resource.status) {
			Status.SUCCESS -> {
				citySelection()
			}

			else -> return
		}

	}

	private fun onReadyProfile(resource: Resource<ProfileRepoModel>) {
		mBinding?.apply {
			if (resource.status == Status.SUCCESS) {
				resource.data?.let { data ->
						firstName.setText(data.firstName)
						lastName.setText(data.lastName)
						phone.setText(data.phone.replaceFirst("+98","0"))
						email.setText(data.email)
						shaba.setText(data.bban?.replace("IR",""))
						national.setText(data.personalCode)
						province.setText(data.state)
						city.setText(data.city)
						address.setText(data.address)
						companyTextView.visibility = if (data.company.isEmpty()) View.GONE else View.VISIBLE
						companyTextView.text = "نماینده‌ای شرکت ${data.company}"
						citySelection()
						gender.setText(if (data.gender == "m") "مرد" else if (data.gender == "f") "زن" else "")
						val date = mViewModel.birthDay
						birthDate.setText(" ${date.persianYear}/${date.persianMonth}/${date.persianDay} ")
				}
			}
		}
	}

	private fun citySelection() {
		mBinding?.apply {
			if (province.getText().isNotEmpty() && mViewModel.cityList.isNotEmpty())
				mViewModel.cityList.forEachIndexed { index, provinceRepoModel ->
					if (province.getText() == provinceRepoModel.name) {
						mViewModel.provinceSelection = index
						provinceRepoModel.cities.forEachIndexed { index, city ->
							if (this.city.getText() == city.name) {
								mViewModel.citySelection = index
								return@forEachIndexed
							}
						}
						return@forEachIndexed
					}
				}
		}
	}

	private fun setDate(persianCalendar: PersianCalendar) {
		mViewModel.birthDay = persianCalendar
		mBinding?.apply { birthDate.setText(persianCalendar.persianShortDate) }

	}

	override fun onDestroyView() {
		mBinding = null
		super.onDestroyView()
	}

	override val baseViewModel: BaseViewModel
		get() = mViewModel

	companion object {
		@JvmStatic
		fun newBundle(isNextPage: Boolean) =
			Bundle().apply {
				putSerializable(ARG_IS_NEXT_PAGE, isNextPage)
			}
	}

}