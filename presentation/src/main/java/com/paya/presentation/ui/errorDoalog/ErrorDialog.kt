package com.paya.presentation.ui.errorDoalog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.paya.presentation.databinding.DialogErrorBinding
import com.paya.presentation.utils.setWidthPercent
import kotlinx.android.synthetic.main.dialog_error.*

const val TAG = "errorDialog"
class ErrorDialog : DialogFragment() {

    private var message = "عملیات شما با خطا مواجه شد"
    private var mBinding: DialogErrorBinding? = null
    var onDismiss: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        mBinding = DialogErrorBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onResume() {
        super.onResume()
        setWidthPercent(80)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorTextView.text = message
    }

    fun setMessage(error: String): ErrorDialog {
        message = error
        errorTextView?.let {
            it.text = message
        }
        return this
    }

    fun isShowing(): Boolean {
        return dialog?.isShowing ?: false
    }

    override fun dismiss() {
        super.dismiss()
        onDismiss.invoke()
    }

    override fun onDestroyView() {
        onDismiss.invoke()
        mBinding = null
        super.onDestroyView()
    }
}