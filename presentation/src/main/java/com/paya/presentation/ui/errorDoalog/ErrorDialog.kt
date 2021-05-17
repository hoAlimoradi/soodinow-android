package com.paya.presentation.ui.errorDoalog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.paya.presentation.R
import com.paya.presentation.utils.setWidthPercent
import kotlinx.android.synthetic.main.dialog_error.*

class ErrorDialog : DialogFragment() {

    private var message = "عملیات شما با خطا مواجه شد"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_error, container, false)
    }

    override fun onResume() {
        super.onResume()
        setWidthPercent(80)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorTextView.text = message
    }

    fun setMessage(error:String) : ErrorDialog{
        message = error
        errorTextView?.let {
            it.text = message
        }
        return this
    }
}