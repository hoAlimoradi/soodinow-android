package com.paya.presentation.ui.createPersonalAccount

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.models.repo.ProvinceRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentFirstInformationBinding
import com.paya.presentation.utils.Utils
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
	private lateinit var mBinding: FragmentFirstInformationBinding
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
			DataBindingUtil.inflate(inflater, R.layout.fragment_first_information, container, false)
		mBinding.viewModel = mViewModel
		mBinding.lifecycleOwner = this
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observe(mViewModel.statusUpdate, ::onReadyUpdateProfile)
		observe(mViewModel.statusCity, ::onReadyCity)
		observe(mViewModel.statusProfile, ::onReadyProfile)
		setDate(PersianCalendar())
		mBinding.birthDate.layout.setOnClickListener {
			showDatePicker()
		}
		mBinding.birthDate.inputContentEditText.setOnClickListener {
			showDatePicker()
		}
		mBinding.gender.layout.setOnClickListener {
			fragmentManager?.let { it1 ->
				PickerViewDialog()
					.selectionIndex(
						if (mViewModel.gender.get() == "m" || mViewModel.gender.get()
								.isNullOrEmpty()
						) 0 else 1
					)
					.setItems(mutableListOf("مرد", "زن"))
					.setTitle(getString(R.string.gender))
					.setListenerSelection { item, index ->
						mBinding.gender.inputContentEditText.setText(item)
						mViewModel.gender.set(if (index == 0) "m" else "f")
					}.show(it1, "gender")
			}
		}
		mBinding.city.layout.setOnClickListener {
			if (mViewModel.cityList.size > 0)
				fragmentManager?.let { it1 ->
					PickerViewDialog()
						.selectionIndex(mViewModel.citySelection)
						.setItems(mViewModel.cityList[mViewModel.provinceSelection].cities.map { it.name })
						.setTitle(getString(R.string.city))
						.setListenerSelection { item, index ->
							mViewModel.citySelection = index
							mViewModel.city.set(item)
						}.show(it1, "city")
				}
		}
		mBinding.province.layout.setOnClickListener {
			if (mViewModel.cityList.size > 0)
				fragmentManager?.let { it1 ->
					PickerViewDialog()
						.selectionIndex(mViewModel.provinceSelection)
						.setItems(mViewModel.cityList.map { it.name })
						.setTitle(getString(R.string.province))
						.setListenerSelection { item, index ->
							mViewModel.provinceSelection = index
							mViewModel.province.set(item)
						}.show(it1, "province")
				}
		}
		mBinding.insertBtn.setOnClickListener {
			updateProfile()
		}
	}

	private fun updateProfile() {
		var isError = false
		val name = mViewModel.name.get()
		val nationalCode = mViewModel.nationalCode.get()
		val birthDay = mViewModel.birthDay.get()?.let { Utils.convertToDate(it) }
		val bban = mViewModel.bban.get()
		val gender = mViewModel.gender.get()
		val city = mViewModel.city.get()
		val province = mViewModel.province.get()
		val address = mViewModel.address.get()

		if (name.isNullOrBlank()) {
			mBinding.name.layout.setError("لطفا نام را وارد کنید")
			isError = true

		}

		if (nationalCode.isNullOrBlank()) {
			mBinding.national.layout.setError("لطفا کد ملی را وارد کنید")
			isError = true
		}

		if (nationalCode?.length != 10) {
			mBinding.national.layout.setError("کد ملی وارد شده اشتباه است")
			isError = true
		}

		if (birthDay.isNullOrBlank()) {
			mBinding.birthDate.layout.setError("لطفا تاربخ تولد را وارد کنید")
			isError = true
		}

		if (bban.isNullOrBlank()) {
			mBinding.shaba.layout.setError("لطفا شماره شبا را وارد کنید")
			isError = true

		}
		if (gender.isNullOrBlank()) {
			mBinding.gender.layout.setError("لطفا جنسیت را انتخاب کنید")
			isError = true
		}
		if (city.isNullOrBlank()) {
			mBinding.city.layout.setError("لطفا شهر را انتخاب کنید")
			isError = true

		}
		if (province.isNullOrBlank()) {
			mBinding.province.layout.setError("لطفا استان را انتخاب کنید")
			isError = true
		}
		if (address.isNullOrBlank()) {
			mBinding.address.layout.setError("لطفا آدرس را وارد کنید")
			isError = true
		}
		if (isError)
			return
		mViewModel.updateProfile(
			name!!,
			nationalCode!!,
			birthDay!!,
			bban!!,
			gender!!,
			city!!,
			province!!,
			address!!
		)
	}

	private fun showDatePicker() {
		picker = PersianDatePickerDialog(requireContext())
			.setPositiveButtonString("باشه")
			.setNegativeButton("بیخیال")
			.setTodayButton("امروز")
			.setTodayButtonVisible(true)
			.setInitDate(if (mViewModel.birthDay.get() == null) PersianCalendar() else mViewModel.birthDay.get())
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
				if (isNextPage)
					findNavController().navigate(
						FirstInformationFragmentDirections.navigateToCreateWithoutRiskAccountFragment()
					)
				else
					context.let {
						Toast.makeText(it, "پروفایل با موفقیت آپدیت شد", Toast.LENGTH_SHORT)
							.show()
					}

			}
			Status.ERROR -> {
				context.let {
					Toast.makeText(it, resource.message, Toast.LENGTH_SHORT)
						.show()
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
			Status.ERROR -> {
				context.let {
					Toast.makeText(it, resource.message, Toast.LENGTH_SHORT)
						.show()
				}

			}
			else -> return
		}

	}

	private fun onReadyProfile(resource: Resource<ProfileRepoModel>) {
		if (resource.status == Status.SUCCESS) {
			if (resource.data?.complete!!) {
				citySelection()
				mBinding.gender.inputContentEditText.setText(if (mViewModel.gender.get() == "m") "مرد" else if (mViewModel.gender.get() == "f") "زن" else "")
				val date = mViewModel.birthDay.get()
				if (date != null) {
					mBinding.birthDate.inputContentEditText.setText(" ${date.persianYear}/${date.persianMonth}/${date.persianDay} ")
				}
			}
		} else if (resource.status == Status.ERROR) {
			Toast.makeText(
				requireContext(), resource.message, Toast.LENGTH_SHORT
			).show()
		}
	}

	private fun citySelection() {
		if (!mViewModel.province.get().isNullOrEmpty() && mViewModel.cityList.isNotEmpty())
			mViewModel.cityList.forEachIndexed { index, provinceRepoModel ->
				if (mViewModel.province.get() == provinceRepoModel.name) {
					mViewModel.provinceSelection = index
					provinceRepoModel.cities.forEachIndexed { index, city ->
						if (mViewModel.city.get() == city.name) {
							mViewModel.citySelection = index
							return@forEachIndexed
						}
					}
					return@forEachIndexed
				}
			}
	}

	private fun setDate(persianCalendar: PersianCalendar) {
		mViewModel.birthDay.set(persianCalendar)
		mBinding.birthDate.inputContentEditText.setText(persianCalendar.persianShortDate)

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