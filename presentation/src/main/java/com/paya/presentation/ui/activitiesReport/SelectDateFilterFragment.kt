package com.paya.presentation.ui.activitiesReport


import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.convertDateWithoutHour
import ir.hamsaa.persiandatepicker.Listener
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog
import ir.hamsaa.persiandatepicker.util.PersianCalendar
import kotlinx.android.synthetic.main.fragment_select_date_filter.*
import java.io.Serializable

private const val PARAM_DATE_FROM = "date_from"
private const val PARAM_DATE_TO = "date_to"

class SelectDateFilterFragment : BaseFragment<BaseViewModel>() {

    private val mViewModel: BaseViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dateFrom = it.getString(PARAM_DATE_FROM) ?: ""
            dateTo = it.getString(PARAM_DATE_TO) ?: ""
        }

    }

    private var dateFrom = ""
    private var dateTo = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_date_filter, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (dateTo.isNotEmpty()) {
            dateToTextView.text =
                Utils.convertStringToPersianCalender(dateTo)?.let { it.persianShortDate }
        }
        if (dateFrom.isNotEmpty())
            dateFromTextView.text =
                Utils.convertStringToPersianCalender(dateFrom)?.let { it.persianShortDate }
        enableAndDisableButton()
        closeBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        dateFromTextView.setOnClickListener {
            showDatePicker(object : Listener {
                override fun onDismissed() {

                }

                override fun onDateSelected(p0: PersianCalendar?) {
                    dateFrom = p0?.let { it1 -> Utils.convertToDate(it1) } ?: "";
                    dateFromTextView.text = p0?.let { it.persianShortDate }
                    enableAndDisableButton()
                }
            })
        }
        dateToTextView.setOnClickListener {
            showDatePicker(object : Listener {
                override fun onDismissed() {

                }

                override fun onDateSelected(p0: PersianCalendar?) {
                    dateTo = p0?.let { it1 -> Utils.convertToDate(it1) } ?: "";
                    dateToTextView.text = p0?.let { it.persianShortDate }
                    enableAndDisableButton()
                }
            })
        }

        acceptBtn.setOnClickListener {
            if (!acceptBtn.isEnabled)
                return@setOnClickListener
            if (dateTo.isNotEmpty() && dateFrom.isNotEmpty() && convertDateWithoutHour(dateTo)?.before(convertDateWithoutHour(dateFrom))!!) {
                mViewModel.showError("تاریخ پایان نمی تواند از تاریخ شروع بیشتر باشد ")
                return@setOnClickListener
            }
            val navController = findNavController()
            FinancialReportFragment.setParamDate(navController, DateFilter(dateFrom, dateTo))
            navController.popBackStack()
        }
    }

    private fun showDatePicker(listener: Listener) {
        PersianDatePickerDialog(context)
            .setPositiveButtonString("باشه")
            .setNegativeButton("بیخیال")
            .setTodayButton("امروز")
            .setTodayButtonVisible(true)
            .setInitDate(PersianCalendar())
            .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
            .setMinYear(1300)
            .setActionTextColor(Color.GRAY)
            .setListener(listener).show()
    }

    private fun enableAndDisableButton() {
        if (dateFrom.isNotEmpty() || dateTo.isNotEmpty()) {
            acceptBtn.isEnabled = true
            acceptBtn.setBackgroundResource(R.drawable.bg_enable_button)
        } else {
            acceptBtn.isEnabled = false
            acceptBtn.setBackgroundResource(R.drawable.bg_disable_button)
        }
    }

    data class DateFilter(val dateFrom: String, val dateTo: String) : Serializable

    companion object {
        @JvmStatic
        fun newBundle(dateFrom: String, dateTo: String): Bundle {
            val bundle = Bundle()
            bundle.putString(PARAM_DATE_FROM, dateFrom)
            bundle.putString(PARAM_DATE_TO, dateTo)
            return bundle
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel
}

