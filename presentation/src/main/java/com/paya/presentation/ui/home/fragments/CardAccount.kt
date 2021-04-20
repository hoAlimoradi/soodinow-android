package com.paya.presentation.ui.hint.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.paya.domain.models.repo.ActiveBoxRepo
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.AccountCardBinding
import com.paya.presentation.ui.cashManager.CashManagerFragment
import com.paya.presentation.utils.Utils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_ACTIVE_BOX = "active_box"
private const val ARG_HAVE_BUTTON = "have_button"


/**
 * A simple [Fragment] subclass.
 * Use the [OpenAccount.newInstance] factory method to
 * create an instance of this fragment.
 */
class CardAccount : BaseFragment<BaseViewModel>() {
    private lateinit var mBinding: AccountCardBinding
    var activeBoxRepo: ActiveBoxRepo? = null
    var haveButton: Boolean = true
    private val viewModel: BaseViewModel by viewModels()
    var isDataSet = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            activeBoxRepo = it.getSerializable(ARG_ACTIVE_BOX) as ActiveBoxRepo
            haveButton = it.getBoolean(ARG_HAVE_BUTTON, true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.account_card, container, false)
        mBinding.lifecycleOwner = this
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
        mBinding.managementAccountBtn.visibility = if (haveButton) View.VISIBLE else View.INVISIBLE
        mBinding.managementAccountBtn.setOnClickListener {
            getFindViewController()?.navigate(R.id.cashManager,
                activeBoxRepo?.id?.let { it1 -> CashManagerFragment.newBundle(it1) })
        }
    }

    private fun setData() {
        if (isDataSet || activeBoxRepo == null)
            return
        isDataSet = true
        activeBoxRepo?.let {
            mBinding.accountUserName.text = it.userName
            mBinding.wealthValue.text = Utils.separatorAmount(it.price.toString())
            val persianDate = Utils.convertStringToPersianCalender(it.createAt)
            persianDate?.let { date ->
                mBinding.dateTitle.text =
                    "${date.persianYear}/${date.persianMonth}/${date.persianDay}"
            }
        }


    }

    companion object {
        @JvmStatic
        fun newInstance(activeBoxRepo: ActiveBoxRepo, haveButton: Boolean = true) =
            CardAccount().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_ACTIVE_BOX, activeBoxRepo)
                    putBoolean(ARG_HAVE_BUTTON, haveButton)
                }
            }
    }

    override val baseViewModel: BaseViewModel
        get() = viewModel
}