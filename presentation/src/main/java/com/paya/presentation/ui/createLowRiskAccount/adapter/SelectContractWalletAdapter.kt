package com.paya.presentation.ui.createLowRiskAccount.adapter

import android.animation.ObjectAnimator
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.paya.presentation.R
import com.paya.presentation.utils.getColorByResId
import com.paya.presentation.utils.getDrawableByResId
import com.paya.presentation.utils.gone
import com.paya.presentation.utils.visible
import kotlinx.android.synthetic.main.row_wallet_select_contrac_contecnt.view.*
import kotlinx.android.synthetic.main.row_wallet_select_contrac_first.view.*


const val VIEW_TYPE_SECTION = 1
const val VIEW_TYPE_ITEM = 2

class SelectContractWalletAdapter(val startInvestClickListener: StartInvestClickListener, val data: List<SelectContractWalletRecyclerViewItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return VIEW_TYPE_SECTION
        }
        return VIEW_TYPE_ITEM

    }
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE_SECTION) {
            return FirstSectionSelectContractWalletAdapterViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.row_wallet_select_contrac_first, parent, false)
            )
        }
        return SecondSectionSoodinowWalletAdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_wallet_select_contrac_contecnt, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        if (holder is FirstSectionSelectContractWalletAdapterViewHolder && item is SectionItem) {
            holder.bind(item)

        }
        if (holder is SecondSectionSoodinowWalletAdapterViewHolder && item is SelectContractWalletItem) {
            holder.bind(item)
        }
    }

    internal inner class FirstSectionSelectContractWalletAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: SectionItem) {
            itemView.firstSectionSelectContractWalletTitleInCard.text = item.title
            itemView.firstSectionSelectContractWalletPointDescription.text = item.description
            itemView.firstSectionSelectContractWalletTitleImageView.run {
                if(item.isFarabi) {
                    this.setImageDrawable(this.context.getDrawableByResId(R.drawable.ic_farabi))
                } else {
                    this.setImageDrawable(this.context.getDrawableByResId(R.drawable.ic_logo_soodinow))
                }
            }

        }
    }


    internal inner class SecondSectionSoodinowWalletAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: SelectContractWalletItem) {

            itemView.addInvestButton.setOnClickListener {
                startInvestClickListener.onPositionClicked(position = 0, isFarabi = false)
            }
            itemView.imageView4.setImageDrawable(
                item.image ?: ContextCompat.getDrawable(
                    itemView.context,
                    R.drawable.ic_image_wallet
                )
            )

            itemView.soodinowPointTitleInCard.text = item.pointTitle
            itemView.soodinowPointName.text = item.name
            itemView.soodinowPointDescription.text = item.description

            itemView.customSeekbar.currentValue = 43f
            /**
            weekly
            */
            itemView.weeklyPercentValue.run {
                if (item.weeklyValue < 0 ) {
                    this.setTextColor(this.context.getColorByResId(R.color.red))
                } else {
                    this.setTextColor(this.context.getColorByResId(R.color.green))
                }
                this.text = item.weeklyValue.toString()
            }
            itemView.weeklyPercentImage.run {
                if (item.weeklyValue < 0 ) {
                    this.background = this.context.getDrawableByResId(R.drawable.bg_icon_15_size_corner_3_red)
                } else {
                    this.background = this.context.getDrawableByResId(R.drawable.bg_icon_15_size_corner_3_green)
                }
            }

            /**
            monthly
             */
            itemView.monthlyPercentValue.run {
                if (item.monthlyValue < 0 ) {
                    this.setTextColor(this.context.getColorByResId(R.color.red))
                } else {
                    this.setTextColor(this.context.getColorByResId(R.color.green))
                }
                this.text = item.monthlyValue.toString()
            }
            itemView.monthlyPercentImage.run {
                if (item.monthlyValue < 0 ) {
                    this.background = this.context.getDrawableByResId(R.drawable.bg_icon_15_size_corner_3_red)
                } else {
                    this.background = this.context.getDrawableByResId(R.drawable.bg_icon_15_size_corner_3_green)
                }
            }

            /**
            trimester
             */
            itemView.trimesterPercentValue.run {
                if (item.trimesterValue < 0 ) {
                    this.setTextColor(this.context.getColorByResId(R.color.red))
                } else {
                    this.setTextColor(this.context.getColorByResId(R.color.green))
                }
                this.text = item.trimesterValue.toString()
            }
            itemView.trimesterPercentImage.run {
                if (item.trimesterValue < 0 ) {
                    this.background = this.context.getDrawableByResId(R.drawable.bg_icon_15_size_corner_3_red)
                } else {
                    this.background = this.context.getDrawableByResId(R.drawable.bg_icon_15_size_corner_3_green)
                }
            }

            itemView.extendImageConstraintLayout.setOnClickListener {

                collapseExpandTextView()

            }


        }

        private fun slideDown(view: View) {
            val height = view.height
            ObjectAnimator.ofFloat(view, "translationY", 0.toFloat(), height.toFloat()).apply {
                duration = 1000
                start()
            }

        }

        private fun slideUp(view: View) {
            val height = view.height
            ObjectAnimator.ofFloat(view, "translationY", height.toFloat(),0.toFloat()).apply {
                duration = 1000
                start()
            }
        }


        private fun collapseExpandTextView() {
            if (itemView.hidableConstraintLayout.visibility == View.GONE) {
                slideUp(itemView.hidableConstraintLayout)
                itemView.hidableConstraintLayout.visible()
                itemView.extendImage.setImageResource(R.drawable.ic_more_up)


            } else {
                // it's expanded - collapse it
                slideDown(itemView.hidableConstraintLayout)
                itemView.hidableConstraintLayout.gone()
                itemView.extendImage.setImageResource(R.drawable.ic_arrow_bottom_gray)
            }

        }
    }
}

interface StartInvestClickListener {
    fun onPositionClicked(position: Int, isFarabi: Boolean)

}

open class SelectContractWalletRecyclerViewItem
class SectionItem(val isFarabi: Boolean, val title: String, val description: String) :
    SelectContractWalletRecyclerViewItem()

class SelectContractWalletItem(
    val pointTitle: String,
    val name: String,
    val description: String,
    val trimesterValue: Int,
    val monthlyValue: Int,
    val weeklyValue: Int,
    val image: Drawable?
) : SelectContractWalletRecyclerViewItem()