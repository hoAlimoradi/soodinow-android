package com.paya.presentation.utils

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.annotation.ColorInt


object BulletTextUtil {
    /**
     * Returns a CharSequence containing a bulleted and properly indented list.
     *
     * @param leadingMargin In pixels, the space between the left edge of the bullet and the left edge of the text.
     * @param context
     * @param stringArrayResId A resource id pointing to a string array. Each string will be a separate line/bullet-point.
     * @return
     */
    fun makeBulletListFromStringArrayResource(
        leadingMargin: Int,
        context: Context,
        @ColorInt color: Int,
        stringArray: Array<String>,

        ): CharSequence {
        return makeBulletList(
            leadingMargin,
            color,
            stringArray,
        )
    }

    /**
     * Returns a CharSequence containing a bulleted and properly indented list.
     *
     * @param leadingMargin In pixels, the space between the left edge of the bullet and the left edge of the text.
     * @param context
     * @param linesResIds An array of string resource ids. Each string will be a separate line/bullet-point.
     * @return
     */
    /*fun makeBulletListFromStringResources(
        leadingMargin: Int,
        context: Context,
        @ColorInt color: Int,
        vararg linesResIds: Int,

    ): CharSequence {
        val len = linesResIds.size
        val cslines = arrayOfNulls<CharSequence>(len)
        for (i in 0 until len) {
            cslines[i] = context.getString(linesResIds[i])
        }
        return makeBulletList(leadingMargin, color, cslines)
    }*/

    /**
     * Returns a CharSequence containing a bulleted and properly indented list.
     *
     * @param leadingMargin In pixels, the space between the left edge of the bullet and the left edge of the text.
     * @param lines An array of CharSequences. Each CharSequences will be a separate line/bullet-point.
     * @return
     */
    fun makeBulletList(
        leadingMargin: Int,
        @ColorInt color: Int,
        vararg lines: Array<String>
    ): CharSequence {
        val sb = SpannableStringBuilder()
        for (i in lines[0].indices) {
            val line: CharSequence =
                lines[0][i] + if (i < lines[0].size - 1) "\n" else ""
            val spannable: Spannable = SpannableString(line)
            spannable.setSpan(
                BulletSpan(leadingMargin, color),
                0,
                spannable.length,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            sb.append(spannable)
        }
        return sb
    }
}