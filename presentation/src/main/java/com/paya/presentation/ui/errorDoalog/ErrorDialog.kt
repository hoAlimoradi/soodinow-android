package com.paya.presentation.ui.errorDoalog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.paya.domain.tools.NoObfuscate
import com.paya.presentation.R
import com.paya.presentation.databinding.DialogErrorBinding
import com.paya.presentation.utils.setWidthPercent
import kotlinx.android.synthetic.main.dialog_error.*
import kotlinx.android.synthetic.main.dialog_error.view.*

const val TAG = "errorDialog"
class ErrorDialog : DialogFragment() {

    private var message = "عملیات شما با خطا مواجه شد"
    private var tryAgainMessageDefualtValue = "لطفا مجدد تلاش کنید"
    //private var tryAgainMessageDefualtValue2 = "هنگام دریافت مجوز، شما را با پیامک مطلع خواهیم ساخت."
    //هنگام دریافت مجوز، شما را با پیامک مطلع خواهیم ساخت.
    private var mBinding: DialogErrorBinding? = null
    //lateinit var rootView: View
    var onDismiss: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
/*        rootView = inflater.inflate(R.layout.dialog_error, container, false)
        return rootView*/
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
        tryAgainTextView.text = tryAgainMessageDefualtValue
    }

    fun setMessage(error: ErrorDialogModel): ErrorDialog {

        message = error.error
        errorTextView?.let {
            it.text = message
        }

        error.tryAgain?.let {
            Log.e("تسسست" , it)
            //rootView.tryAgainTextView.text = it
            //ُtodo
            //mBinding!!.tryAgainTextView.text = tryAgainMessageDefualtValue2
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

data class ErrorDialogModel (
    val error: String,
    val tryAgain: String?
)
