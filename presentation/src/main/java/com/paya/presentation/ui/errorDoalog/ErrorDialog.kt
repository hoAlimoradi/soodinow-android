package com.paya.presentation.ui.errorDoalog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.paya.domain.tools.NoObfuscate
import com.paya.presentation.R
import com.paya.presentation.databinding.DialogErrorBinding
import com.paya.presentation.utils.setWidthPercent
import kotlinx.android.synthetic.main.dialog_error.*
import kotlinx.android.synthetic.main.dialog_error.view.*

const val TAG = "errorDialog"
class ErrorDialog : DialogFragment() {
   /* lateinit var tryAgainText: AppCompatTextView
    lateinit var errorText: AppCompatTextView
    lateinit var rootView: View
*/
    private var message = "عملیات شما با خطا مواجهه شد"
    private var tryAgainMessageDefualtValue = "لطفا مجدد تلاش کنید"
    private var typeIconInErrorDialog: TypeIconInErrorDialog = TypeIconInErrorDialog.ERROR

    var onDismiss: () -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.dialog_error, container, false)

    }

    override fun onResume() {
        super.onResume()
        setWidthPercent(80)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        errorTextView.text = message
        tryAgainTextView.text = tryAgainMessageDefualtValue

        icon.apply {
            background = if (typeIconInErrorDialog == TypeIconInErrorDialog.ERROR) {
                ContextCompat.getDrawable(this.context, R.drawable.dialog_error_circle)
            } else {
                ContextCompat.getDrawable(this.context, R.drawable.dialog_warning_circle)
            }

        }
        //errorTextView.text = message
        //tryAgainTextView.text = tryAgainMessageDefualtValue
    }

    fun setMessage(error: ErrorDialogModel): ErrorDialog {
        message = error.error
        error.tryAgain?.let {
            tryAgainMessageDefualtValue = it
        }
        error.typeIcon?.let {
            typeIconInErrorDialog = it
        }
        /*message = error.error

        errorTextView?.let {
            it.text = message
        }

        error.tryAgain?.let {
            Log.e("تسسست" , it)
            tryAgainTextView.text = it
            //rootView.tryAgainTextView.text = it
            //ُtodo
            //mBinding!!.tryAgainTextView.text = tryAgainMessageDefualtValue2
        }*/
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
        //mBinding = null
        super.onDestroyView()
    }
}

data class ErrorDialogModel (
    val error: String,
    val tryAgain: String?,
    val typeIcon: TypeIconInErrorDialog?
)

enum class TypeIconInErrorDialog { ERROR, WARNING }