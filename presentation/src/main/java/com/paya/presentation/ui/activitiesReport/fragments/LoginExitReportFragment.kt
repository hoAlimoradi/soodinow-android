package com.paya.presentation.ui.activitiesReport.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.paya.presentation.R
import com.paya.presentation.ui.activitiesReport.adapter.LoginExitReportAdapter
import kotlinx.android.synthetic.main.fragment_login_exit_report.*


class LoginExitReportFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login_exit_report, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginExitRecyclerView.layoutManager = LinearLayoutManager(context)
        loginExitRecyclerView.adapter = LoginExitReportAdapter()
    }


}