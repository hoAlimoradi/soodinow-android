package com.paya.presentation.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentChatBinding
import com.paya.presentation.ui.chat.adapter.ExpertAdapter
import com.paya.presentation.utils.OverlapDecoration


class ChatFragment : Fragment() {
	
	private lateinit var mBinding: FragmentChatBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
	}
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		mBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_chat,container,false)
		mBinding.lifecycleOwner = this
		return mBinding.root
	}
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		mBinding.pulsator.start()
		setupExpertRecyclerView()
	}
	
	private fun setupExpertRecyclerView() {
		val adapter = ExpertAdapter()
		val manager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
		mBinding.expertRecyclerView.adapter =adapter
		mBinding.expertRecyclerView.layoutManager = manager
		mBinding.expertRecyclerView.addItemDecoration(OverlapDecoration())
	}
	
	
}