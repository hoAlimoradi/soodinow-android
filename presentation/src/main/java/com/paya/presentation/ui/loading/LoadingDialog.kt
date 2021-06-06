package com.paya.presentation.ui.loading

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.paya.presentation.R
import com.paya.presentation.databinding.LoadingBinding
import com.paya.presentation.utils.setFullScreen

class LoadingDialog : DialogFragment() {

    private var mBinding: LoadingBinding? = null

    var onDismiss: () -> Unit = {}
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        context?.let { context ->
            dialog?.apply {
                window?.apply {
                    setBackgroundDrawable(
                        ColorDrawable(
                            ContextCompat.getColor(
                                context,
                                R.color.loading_dialog_bg
                            )
                        )
                    )
                }
            }
        }
        mBinding = LoadingBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        isCancelable = false
        setFullScreen()
    }

    fun isShowing(): Boolean {
        return dialog?.isShowing ?: false
    }

    override fun dismiss() {
        super.dismiss()
    }

    override fun onDestroyView() {
        onDismiss.invoke()
        mBinding = null
        super.onDestroyView()
    }
}