package com.paya.presentation.ui.createLowRiskAccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.paya.presentation.R
import com.paya.presentation.utils.openUrl
import com.paya.presentation.utils.setArrayStringText
import kotlinx.android.synthetic.main.fragment_wallet_farabi.*

class FarabiWalletFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_wallet_farabi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        image.setImageResource(R.drawable.ic_farabi)

        txtDescBrokerage.setOnClickListener {
            openUrl("https://reg.irfarabi.com/reg/?j=1&is=1&ref=10112")
        }
        context?.let { context ->

            txtDescBrokerage.setArrayStringText(
                context.resources.getStringArray(R.array.brokerage_desc_bottom),
                ContextCompat.getColor(context, R.color.green)
            )

        }
        txtDescBrokerage.visibility = View.VISIBLE

        context?.let { context ->
            txtDesc.setArrayStringText(
                context.resources.getStringArray(
                    R.array.brokerage_desc
                ),
                ContextCompat.getColor(context, R.color.green)
            )

        }
    }
}