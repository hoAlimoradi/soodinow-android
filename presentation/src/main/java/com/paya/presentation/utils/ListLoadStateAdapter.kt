package com.paya.presentation.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import com.paya.presentation.databinding.LoadStateItemBinding
import com.paya.presentation.databinding.LoadingStateBinding

class ListLoadStateAdapter() : LoadStateAdapter<ListLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.load_state_item, parent, false)
    ) {
        val binding = LoadStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        private val progress = binding.progressBar
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                Toast.makeText(itemView.context,loadState.error.localizedMessage,Toast.LENGTH_SHORT).show()
            }
            progress.visibility = if(loadState is LoadState.Loading) View.VISIBLE else View.GONE

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ) = LoadStateViewHolder(parent)

    override fun onBindViewHolder(
        holder: LoadStateViewHolder,
        loadState: LoadState
    ) = holder.bind(loadState)
}