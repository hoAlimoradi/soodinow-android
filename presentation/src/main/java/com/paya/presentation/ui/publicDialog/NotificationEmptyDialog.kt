package com.paya.presentation.ui.publicDialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.paya.domain.models.repo.BoxHistoryRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseDialogFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.DialogChartProfileBinding
import com.paya.presentation.databinding.DialogNotificationEmptyBinding
import com.paya.presentation.ui.profile.enum.FilterProfile
import com.paya.presentation.utils.BindingAdapters
import com.paya.presentation.utils.observe
import com.paya.presentation.utils.setWidthPercent
import com.paya.presentation.viewmodel.ChartProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_chart_profile.*

@AndroidEntryPoint
class NotificationEmptyDialog : DialogFragment() {
    private lateinit var mBinding: DialogNotificationEmptyBinding


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
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.dialog_notification_empty,
            container,
            false
        )

        mBinding.lifecycleOwner = this


        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.closeBtn.setOnClickListener {
            dismissAllowingStateLoss()
        }
    }
}