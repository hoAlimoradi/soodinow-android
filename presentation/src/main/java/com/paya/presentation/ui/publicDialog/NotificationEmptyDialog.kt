package com.paya.presentation.ui.publicDialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.paya.presentation.databinding.DialogNotificationEmptyBinding
import com.paya.presentation.utils.setWidthPercent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationEmptyDialog : DialogFragment() {
    private var mBinding: DialogNotificationEmptyBinding? = null


    override fun onStart() {
        super.onStart()
        setWidthPercent(90)
        dialog?.window?.let {
            it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = DialogNotificationEmptyBinding.inflate(
            inflater,
            container,
            false
        )
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.apply {
            closeBtn.setOnClickListener {
                dismissAllowingStateLoss()
            }
        }
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}