package com.paya.presentation.ui.cardAccount

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.viewmodel.NewCardAccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_new_card_account.*


@AndroidEntryPoint
class NewCardAccountFragment : BaseFragment<NewCardAccountViewModel>() {
    private val mViewModel: NewCardAccountViewModel by viewModels()
    private var onItemClick: ((Int) -> Unit)? = null
    private var position: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_card_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createAccountBtn.setOnClickListener {
            onItemClick?.let {
                it.invoke(position ?: 0)
            }
        }
    }

    fun setClickListener(onItemClick: (Int) -> Unit, position: Int) {
        this.onItemClick = onItemClick
        this.position = position
    }


    override val baseViewModel: BaseViewModel
        get() = mViewModel
}