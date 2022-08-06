package com.paya.presentation.ui.wallet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.mikephil.charting.utils.Utils
import com.paya.domain.models.repo.InvestingInfoDataRepoModel
import com.paya.domain.models.repo.InvestingInfoRepoModel
import com.paya.domain.models.repo.PortalBankRepoModel
import com.paya.presentation.R
import com.paya.presentation.databinding.ItemPortalBankBinding
import com.paya.presentation.databinding.ItemWalletBinding

class WalletInvestAdapter(private var list: List<InvestingInfoDataRepoModel>) :
    RecyclerView.Adapter<WalletInvestAdapter.WalletInvestViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalletInvestViewHolder {
        val binding =
            ItemWalletBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WalletInvestViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: WalletInvestViewHolder, position: Int) {
        with(holder) {
            with(list[position]) {
                binding.value.text = com.paya.presentation.utils.Utils.separatorAmount(totalBalance) + itemView.context.resources.getString(
                    R.string.toman)
                binding.title.text = hostName
            }
        }
    }



    class WalletInvestViewHolder(val binding: ItemWalletBinding) :
        RecyclerView.ViewHolder(binding.root)
}