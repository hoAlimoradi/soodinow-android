package com.paya.presentation.ui.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.repo.ChatRepoModel
import com.paya.presentation.R
import kotlinx.android.synthetic.main.row_support_chat.view.*
import kotlinx.android.synthetic.main.row_user_chat.view.*

class ChatAdapter(private val chats: MutableList<ChatRepoModel>) :
	RecyclerView.Adapter<RecyclerView.ViewHolder>() {
	override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): RecyclerView.ViewHolder {
		val inflater = LayoutInflater.from(parent.context)
		val view = inflater.inflate(
			if (viewType == 0) R.layout.row_support_chat else R.layout.row_user_chat,
			parent,
			false
		)
		return if (viewType == 0) SupportViewHolder(view) else UserViewHolder(view)
	}
	
	override fun onBindViewHolder(holder: RecyclerView.ViewHolder,position: Int) {
		val item = chats[position]
		if (holder is SupportViewHolder) {
			holder.itemView.apply {
				descSupport.text = item.description
			}
			
		} else if (holder is UserViewHolder) {
			holder.itemView.apply {
				descUser.text = item.description
			}
		}
	}
	
	override fun getItemCount(): Int = chats.size
	
	override fun getItemViewType(position: Int): Int {
		return chats[position].sender
	}
	
	fun addItem(chat: ChatRepoModel) {
		chats.add(chat)
		notifyItemChanged(itemCount - 1)
	}
	
	class SupportViewHolder(view: View) : RecyclerView.ViewHolder(view) {
	
	}
	
	class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
	
	}
	
}