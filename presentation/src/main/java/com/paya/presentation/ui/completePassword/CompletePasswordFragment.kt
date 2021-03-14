package com.paya.presentation.ui.completePassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentCompletePasswordBinding
import com.paya.presentation.viewmodel.CompletePasswordViewModel
import com.paya.presentation.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CompletePasswordFragment : BaseFragment<CompletePasswordViewModel>() {

    private lateinit var mBinding: FragmentCompletePasswordBinding
    private val mViewModel: CompletePasswordViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_complete_password, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.submitButton.setOnClickListener {
            findNavController().navigate(
                R.id.navigateToLoginFragment
            )
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel
}