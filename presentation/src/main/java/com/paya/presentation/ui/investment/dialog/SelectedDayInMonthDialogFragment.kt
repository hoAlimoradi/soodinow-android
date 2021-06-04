package com.paya.presentation.ui.investment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentMonthDialogBinding
import com.paya.presentation.ui.investment.adapter.SelectedDayInMonth
import com.paya.presentation.utils.BaseAdapter
import com.paya.presentation.utils.Utils
import kotlinx.android.synthetic.main.fragment_month_dialog.*
import kotlinx.android.synthetic.main.toolbar_dialog.*


class SelectedDayInMonthDialogFragment(
	var onSelectedDay: OnSelectedDay
) : DialogFragment() {
	
	private lateinit var mBinding : FragmentMonthDialogBinding
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(
			inflater,
			R.layout.fragment_month_dialog,
			container,
			false
		)
		
		return mBinding.root
	}
	override fun onResume() {
		super.onResume()
		dialog?.window?.let { Utils.setSizeDialog(requireActivity(),it) }
		
	}
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		setupRecyclerView()
		closeBtn.setOnClickListener {
			dismissAllowingStateLoss()
		}
	}
	
	private fun setupRecyclerView() {
		dayRecyclerView.layoutManager = LinearLayoutManager(requireContext())
		val adapter = SelectedDayInMonth()
		val dividerItemDecoration = DividerItemDecoration(context, LinearLayout.VERTICAL)
		context?.let { it ->
			ContextCompat.getDrawable(it,R.drawable.divider_line)?.let {
				dividerItemDecoration.setDrawable(
					it
				)
			}
		}
		dayRecyclerView.addItemDecoration(dividerItemDecoration)
		adapter.addOnClickListenerItem(object : BaseAdapter.OnClickListenerItem<String> {
			override fun onClickListenerItem(
				adapter: BaseAdapter<*,String>,
				item: String,
				position: Int
			) {
				onSelectedDay.day(item)
				dismissAllowingStateLoss()
			}
			
		})
		dayRecyclerView.adapter = adapter
	}
	override fun onDestroy() {
		super.onDestroy()
		mBinding.unbind()
	}
	interface OnSelectedDay {
		fun day(day: String)
	}
}