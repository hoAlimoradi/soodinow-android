package com.paya.presentation.ui.invesmtWallet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paya.domain.models.repo.PortalBankRepoModel
import com.paya.presentation.databinding.ItemPortalBankBinding

class BankPortalAdapter(private var list: List<PortalBankRepoModel>,private val onItemClicked: (name: String) -> Unit) :
    RecyclerView.Adapter<BankPortalAdapter.BankPortalViewHolder>() {

    var selectionItem = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BankPortalViewHolder {
        val binding =
            ItemPortalBankBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BankPortalViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BankPortalViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                Glide.with(itemView.context).load(imgUrl).into(binding.icon)
                if (position==0)
                    selectionItem = name
                itemView.setOnClickListener {
                    selectionItem = name
                    onItemClicked.invoke(name)
                }
            }
        }
    }



    class BankPortalViewHolder(val binding: ItemPortalBankBinding) :
        RecyclerView.ViewHolder(binding.root)
}