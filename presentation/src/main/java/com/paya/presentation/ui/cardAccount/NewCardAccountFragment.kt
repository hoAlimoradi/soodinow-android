package com.paya.presentation.ui.cardAccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.paya.domain.models.repo.ProfileRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.ui.createPersonalAccount.FirstInformationFragment
import com.paya.presentation.ui.home.HomeFragment
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.CalculateProfitCapitalViewModel
import com.paya.presentation.viewmodel.NewCardAccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_new_card_account.*


@AndroidEntryPoint
class NewCardAccountFragment : BaseFragment<NewCardAccountViewModel>() {
    private val mViewModel: CalculateProfitCapitalViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_card_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe(mViewModel.statusProfile,::checkProfile)
        createAccountBtn.setOnClickListener {
            progress_bar.visibility = View.VISIBLE
            createAccountBtn.visibility = View.GONE
           mViewModel.getProfile()
        }
    }
    private fun checkProfile(resource: Resource<ProfileRepoModel>) {
        progress_bar.visibility = View.GONE
        createAccountBtn.visibility = View.VISIBLE
        if (resource.status == Status.SUCCESS) {
            if (!resource.data!!.complete) {
                getFindViewController()?.navigate(
                    R.id.firstInformation,FirstInformationFragment.newBundle(true)
                )
            } else {
                getFindViewController()?.navigate(
                    R.id.createLowRiskAccount
                )
            }
        } else if (resource.status == Status.ERROR) {
            Toast.makeText(
                requireContext(),resource.message, Toast.LENGTH_SHORT
            ).show()
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel
}