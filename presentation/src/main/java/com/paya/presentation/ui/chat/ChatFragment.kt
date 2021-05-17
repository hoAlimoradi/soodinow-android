package com.paya.presentation.ui.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.repo.ChatRepoModel
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentChatBinding
import com.paya.presentation.ui.chat.adapter.ChatAdapter
import com.paya.presentation.ui.chat.adapter.ExpertAdapter
import com.paya.presentation.utils.OverlapDecoration


class ChatFragment : Fragment() {
	
	private var adapterChat: ChatAdapter = ChatAdapter(mutableListOf())
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

		setupChatRecyclerView()
		mBinding.sendBtn.setOnClickListener {
			val chat = ChatRepoModel(
				mBinding.input.text.toString(),
				if (adapterChat.itemCount % 2 == 0) 0 else 1
			)
			adapterChat.addItem(chat)
			mBinding.chatRecyclerView.scrollToPosition(adapterChat.itemCount -1)
		}
		mBinding.toolbar.backButton.setOnClickListener {
			findNavController().popBackStack()
		}
	}
	

	
	private fun setupChatRecyclerView() {
		val manager = LinearLayoutManager(requireContext(),RecyclerView.VERTICAL,false)
		mBinding.chatRecyclerView.adapter = adapterChat
		mBinding.chatRecyclerView.layoutManager = manager
	}
	
	
}