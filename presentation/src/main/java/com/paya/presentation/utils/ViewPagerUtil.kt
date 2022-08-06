package com.paya.presentation.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.paya.presentation.R
import kotlin.math.abs


class ViewPagerUtil {
	
	companion object {
		@JvmStatic
		fun getTransformer(resources: Resources): ViewPager2.PageTransformer {
			val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
			val currentItemHorizontalMarginPx =
				resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
			val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
			return ViewPager2.PageTransformer { page: View,position: Float ->
				page.translationX = -pageTranslationX * position
				// Next line scales the item's height. You can remove it if you don't want this effect
				page.scaleY = 1 - (0.25f * abs(position))
				// If you want a fading effect uncomment the next line:
				// page.alpha = 0.25f + (1 - abs(position))
			}
		}
		
		@JvmStatic
		fun getItemDecoration(context: Context): RecyclerView.ItemDecoration {
			return HorizontalMarginItemDecoration(
				context,
				R.dimen.viewpager_current_item_horizontal_margin
			)
			
		}
	}
	
	class HorizontalMarginItemDecoration(context: Context,@DimenRes horizontalMarginInDp: Int) :
		RecyclerView.ItemDecoration() {
		
		private val horizontalMarginInPx: Int =
			context.resources.getDimension(horizontalMarginInDp).toInt()
		
		override fun getItemOffsets(
			outRect: Rect,view: View,parent: RecyclerView,state: RecyclerView.State
		) {
			outRect.right = horizontalMarginInPx
			outRect.left = horizontalMarginInPx
		}
		
	}
}