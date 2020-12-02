package com.paya.presentation.ui.hint.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.paya.domain.models.repo.LinearChartRepoModel
import com.paya.presentation.R
import com.paya.presentation.databinding.AccountCardBinding
import com.paya.presentation.utils.BindingAdapters
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.shared.Point

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OpenAccount.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardAccount : Fragment() {
	// TODO: Rename and change types of parameters
	private var param1: String? = null
	private var param2: String? = null
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		arguments?.let {
			param1 = it.getString(ARG_PARAM1)
			param2 = it.getString(ARG_PARAM2)
		}
	}
	
	
	private lateinit var mBinding: AccountCardBinding
	var isDataSet = false
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(inflater,R.layout.account_card,container,false)
		mBinding.lifecycleOwner = this
		return mBinding.root
	}
	
	fun setData(linearChartRepoModel: LinearChartRepoModel,buyValue: Long,percent: Double, name: String) {
		if (isDataSet)
			return
		isDataSet = true
		mBinding.accountUserName.text = name
		mBinding.wealthValue.text = Utils.separatorAmount(buyValue.toString())
		
		val points = mutableListOf<Point>()
		linearChartRepoModel.data.forEachIndexed { index,value ->
			points.add(Point(index.toFloat(),value.toFloat(),percent.toFloat()))
		}
		BindingAdapters.setLineAccountChartData(mBinding.chart,points)
		
		val persianDate = Utils.convertStringToPersianCalender(linearChartRepoModel.startDate)
		persianDate?.let {
			val date = "${persianDate.persianYear}/${persianDate.persianMonth}/${persianDate.persianDay}"
			mBinding.dateValue.text = date
		}
	}
	
	companion object {
		/**
		 * Use this factory method to create a new instance of
		 * this fragment using the provided parameters.
		 *
		 * @param param1 Parameter 1.
		 * @param param2 Parameter 2.
		 * @return A new instance of fragment OpenAccount.
		 */
		// TODO: Rename and change types and number of parameters
		@JvmStatic
		fun newInstance(param1: String,param2: String) =
			OpenAccount().apply {
				arguments = Bundle().apply {
					putString(ARG_PARAM1,param1)
					putString(ARG_PARAM2,param2)
				}
			}
	}
}