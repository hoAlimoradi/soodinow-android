package com.paya.presentation.ui.changePhoneNumber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentCompleteResetPhoneBinding
import com.paya.presentation.viewmodel.CompletePasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompleteResetPhoneFragment : BaseFragment<CompletePasswordViewModel>() {

    private var mBinding: FragmentCompleteResetPhoneBinding? = null
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
            FragmentCompleteResetPhoneBinding.inflate(inflater, container, false)
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            delay(1000)
            findNavController().popBackStack(R.id.mainFragment, true)
        }
        mBinding?.apply {
            submitButton.setOnClickListener {
                findNavController().popBackStack(R.id.mainFragment, true)
            }
        }
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel
}