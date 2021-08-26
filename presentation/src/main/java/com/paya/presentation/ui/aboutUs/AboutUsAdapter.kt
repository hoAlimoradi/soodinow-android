package com.paya.presentation.ui.aboutUs

import com.paya.domain.models.repo.AboutUsModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.paya.presentation.R
import com.paya.presentation.utils.BaseAdapter
import kotlinx.android.synthetic.main.row_about_us.view.*
import kotlinx.android.synthetic.main.row_why_soodinow.view.*
import kotlinx.android.synthetic.main.row_why_soodinow.view.whySoodinowDesc

class AboutUsAdapter() : BaseAdapter<AboutUsAdapter.MarketViewHolder, AboutUsModel>() {

    override fun onCreateHolder(parent: ViewGroup,viewType: Int): MarketViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val view = inflate.inflate(R.layout.row_about_us,parent,false)
        return MarketViewHolder(view)
    }

    override fun onBindHolder(holder: MarketViewHolder,model: AboutUsModel, position: Int) {
        val params = holder.itemView.layoutParams as RecyclerView.LayoutParams
        params.bottomMargin = if (data.lastIndex == position) holder.itemView.resources.getDimension(R.dimen.space_bottom_navigation).toInt() else 0
        holder.itemView.layoutParams = params
        with(holder.itemView) {

            if (model.hasBox) {
                constraintLayoutWithBox.visibility = View.VISIBLE
                aboutUsTitleWithBox.text = model.title
                aboutUsDescWithBox.text = model.description

                constraintLayoutWithoutBox.visibility = View.GONE
                aboutUsTitleWithoutBox.text = ""
                aboutUsDescWithoutBox.text = ""
            } else {
                constraintLayoutWithBox.visibility = View.GONE
                aboutUsTitleWithBox.text = ""
                aboutUsDescWithBox.text = ""

                constraintLayoutWithoutBox.visibility = View.VISIBLE
                aboutUsTitleWithoutBox.text = model.title
                aboutUsDescWithoutBox.text = model.description
            }

        }
    }


    class MarketViewHolder(itemView: View) : ViewHolder(itemView) {

    }
}

