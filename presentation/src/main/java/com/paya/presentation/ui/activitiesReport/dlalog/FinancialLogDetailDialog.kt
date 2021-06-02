package com.paya.presentation.ui.activitiesReport.dlalog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.paya.domain.models.repo.InvestmentLogsModel
import com.paya.presentation.R
import com.paya.presentation.ui.activitiesReport.enum.StateInvestment
import com.paya.presentation.ui.activitiesReport.enum.TypeAccount
import com.paya.presentation.ui.activitiesReport.enum.TypeInvestment
import com.paya.presentation.utils.Utils
import com.paya.presentation.utils.getTimeHoursAndMinute
import com.paya.presentation.utils.setFullScreen
import kotlinx.android.synthetic.main.dialog_financial_log_detail.*
import kotlinx.android.synthetic.main.item_financial_report.view.*


class FinancialLogDetailDialog : DialogFragment() {


    var item: InvestmentLogsModel? = null
        get() = field
        set(value) {
            field = value
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_financial_log_detail, container, false)
    }

    override fun onResume() {
        super.onResume()
        setFullScreen()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item?.let {
            icon.setBackgroundResource(StateInvestment.getStateWithString(it.state).bg)
            icon.setImageResource(StateInvestment.getStateWithString(it.state).icon)
            context?.let { context ->
                titleTextView.setTextColor(
                    ContextCompat.getColor(
                        context,
                        StateInvestment.getStateWithString(it.state).color
                    )
                )
            }
            titleTextView.text =
                " ${TypeInvestment.getTypeWithString(
                    it.type
                ).title} ${StateInvestment.getStateWithString(it.state).title}"
            nameTextView.text = TypeAccount.getTypeWithString(it.investmentType).title
            priceTextView.text = Utils.separatorAmount(it.startPrice)
            finalPriceTextView.text = Utils.separatorAmount(it.finalPrice)
            finalTitlePriceTextView.text =
                " مبلغ ${TypeInvestment.getTypeWithString(it.type).title} شده "
            val datePersian = Utils.convertStringToPersianCalender(it.createdAt)
            val date = Utils.convertStringToDate(it.createdAt)

            var dateText = ""

            datePersian?.let { date ->
                dateText +=
                    "${date.persianYear}/${date.persianMonth}/${date.persianDay}"
            }
            date?.let { date ->
                dateText += " - " + date.getTimeHoursAndMinute()
            }
            dateTextView.text = dateText
            numberTextView.text = it.trackingNumber
            errorTextView.visibility =
                if (it.errorDescription.isEmpty()) View.INVISIBLE else View.VISIBLE
            errorTextView.text = it.errorDescription
            closeBtn.setOnClickListener { dismissAllowingStateLoss() }
        }
    }


}