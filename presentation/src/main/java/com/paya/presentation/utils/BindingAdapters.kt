package com.paya.presentation.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import at.grabner.circleprogress.CircleProgressView
import com.alimuzaffar.lib.pin.PinEntryEditText
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.paya.presentation.R
import com.paya.presentation.ui.custom.MyMarkerView
import com.paya.presentation.ui.custom.MyMarkerViewSmall
import com.paya.presentation.utils.shared.EntryPoint
import com.paya.presentation.utils.shared.Point


object BindingAdapters {

    @JvmStatic
    @BindingAdapter("setText")
    fun setText(view: TextView, value: Int) {
        view.text = value.toString()
    }

    @JvmStatic
    @BindingAdapter("isSelected")
    fun setSelected(view: View, value: Boolean) {
        view.isSelected = value
    }

    @JvmStatic
    @BindingAdapter("iconSet")
    fun setImageDrawable(view: ImageView, drawable: Drawable?) {
        view.setImageDrawable(drawable)
    }

    @JvmStatic
    @BindingAdapter("percentText")
    fun setPercentText(textView: TextView, value: String?) {
        value ?: return
        val percentText = "% $value"
        textView.text = percentText
    }

    @JvmStatic
    @BindingAdapter("progressValue")
    fun setProgressValue(circleProgressView: CircleProgressView, value: Float?) {
        value ?: return
        circleProgressView.setValue(value)
    }

    @JvmStatic
    @BindingAdapter("enableDisable")
    fun setTime(textView: TextView, value: Int?) {
        value ?: return
        if (value != 0)
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.gray))
        else
            textView.setTextColor(ContextCompat.getColor(textView.context, R.color.green))
    }

    @JvmStatic
    @BindingAdapter("changeBgRoundPurple")
    fun changeBgRoundPurple(view: Button, value: Boolean?) {
        value ?: return
        if (value) {
            view.setBackgroundResource(R.drawable.round_corner_layout_purple)
            view.setTextColor(ContextCompat.getColor(view.context, R.color.white))
        } else {
            view.setBackgroundColor(Color.TRANSPARENT)
            view.setTextColor(ContextCompat.getColor(view.context, R.color.gray))
        }
    }


    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("visibleInVisible")
    fun showInVisible(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

    @JvmStatic
    @BindingAdapter("focusTarget")
    fun setFocusTarget(view: View, viewId: Int?) {
        viewId ?: return
        view.setOnClickListener {
            val target = view.findViewById<EditText>(viewId)
            target.requestFocus()
            val imm: InputMethodManager? =
                target.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(target, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    @JvmStatic
    @BindingAdapter("enableView")
    fun setEnableView(view: View, viewId: Int?) {
        viewId ?: return
        view.setOnClickListener {
            val target = (view.parent as View).findViewById<EditText>(viewId)
            if (target.isEnabled) {
                target.isEnabled = !target.isEnabled
                target.clearFocus()
                val imm: InputMethodManager? =
                    target.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm?.hideSoftInputFromWindow(target.windowToken, 0)
            } else {
                target.isEnabled = !target.isEnabled
                target.requestFocus()
                val imm: InputMethodManager? =
                    target.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                imm?.showSoftInput(target, InputMethodManager.SHOW_IMPLICIT)
                target.setSelection(target.text.length)
            }


        }
    }

    @JvmStatic
    @BindingAdapter("verificationImage")
    fun setVerificationImage(editText: EditText, viewId: Int?) {
        viewId ?: return

        val parent = editText.parent as View
        val imageView = parent.findViewById<ImageView>(viewId)
        setTintColor(imageView, editText.context, R.color.gray)

        editText.doAfterTextChanged {
            val text = it.toString()
            val colorId = if (text.length != 9) R.color.gray else R.color.green
            setTintColor(imageView, editText.context, colorId)
        }
    }

    @JvmStatic
    @BindingAdapter("verificationPinImage")
    fun setVerificationPinImage(editText: PinEntryEditText, viewId: Int?) {
        viewId ?: return

        val parent = editText.parent as View
        val imageView = parent.findViewById<ImageView>(viewId)
        setTintColor(imageView, editText.context, R.color.gray)

        editText.doAfterTextChanged {
            val text = it.toString()
            val colorId = if (text.length != 5) R.color.gray else R.color.green
            setTintColor(imageView, editText.context, colorId)
        }
    }

    @JvmStatic
    @BindingAdapter("hintLabel")
    fun setHintLabel(editText: EditText, value: String?) {
        value ?: return
        editText.hint = value
    }

    @JvmStatic
    @BindingAdapter("spannableText", "spannableColor")
    fun setSpannableRules(textView: TextView, value: String?, color: Int = Color.BLUE) {
        val spannable = SpannableString(textView.text)
        val start = value?.let { textView.text.indexOf(it) }
        val end = value?.let { it.length }
        if (start != null && end != null) {
            spannable.setSpan(
                ForegroundColorSpan(color),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        textView.setText(spannable, TextView.BufferType.SPANNABLE);
    }


    @JvmStatic
    @BindingAdapter("calling")
    fun calling(view: View, phone: String?) {
        phone ?: return
        view.setOnClickListener {
            val uri: Uri = Uri.parse("tel:" + phone.trim())
            val intent = Intent(Intent.ACTION_DIAL, uri)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            view.context.startActivity(intent)
        }
    }

    @JvmStatic
    @BindingAdapter("chartData")
    fun setLineChartData(chart: LineChart, data: List<Point>?) {
        data ?: return
        chart.setViewPortOffsets(0f, 0f, 0f, 0f)
        chart.setBackgroundColor(Color.WHITE)

        // no description text
        chart.description.isEnabled = false

        // enable touch gestures
        chart.setTouchEnabled(true)

        // enable scaling and dragging
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false)

        chart.setDrawGridBackground(false)
        chart.maxHighlightDistance = 300f

        chart.xAxis.isEnabled = false

        chart.axisLeft.isEnabled = false

        chart.axisRight.isEnabled = false

        chart.marker = MyMarkerView(chart.context, chart)

        setData(chart, data)

        chart.legend.isEnabled = false

        chart.animateXY(1000, 1000)

        // don't forget to refresh the drawing
        chart.invalidate()


        val index = chart.data.dataSets[0].entryCount - 1

        val x = chart.data.dataSets[0].getEntryForIndex(index).x

        val y = chart.data.dataSets[0].getEntryForIndex(index).y
        chart.highlightValue(x, y, 0)
        chart.marker.refreshContent(
            chart.data.dataSets[0].getEntryForIndex(index),
            chart.highlighted[0]
        )
    }

    private fun setData(chart: LineChart, points: List<Point>) {
        val set1: LineDataSet

        val entries = points.map { EntryPoint(it.x, it.y, it.percent, it.price) }

        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = entries
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(entries, "DataSet 1")
            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            set1.cubicIntensity = 0.2f
            set1.setDrawFilled(true)
            set1.setDrawCircles(true)
            set1.lineWidth = 3f
            set1.circleRadius = 1f
            set1.setCircleColor(ContextCompat.getColor(chart.context, R.color.green))
            set1.highLightColor = Color.RED
            set1.color = ContextCompat.getColor(chart.context, R.color.green)
            set1.fillColor = ContextCompat.getColor(chart.context, R.color.green)
            set1.fillAlpha = 30
            set1.setDrawHorizontalHighlightIndicator(true)
            set1.fillFormatter =
                IFillFormatter { _, _ -> chart.axisLeft.axisMinimum }

            // create a data object with the data sets
            val data = LineData(set1)
            data.setValueTextSize(9f)
            data.setDrawValues(false)

            // set data
            chart.data = data
        }
    }

    @JvmStatic
    @BindingAdapter("chartWithoutData")
    fun setLineChartWithoutData(chart: LineChart, axisMaximum: Float) {

        chart.description.isEnabled = false
        chart.setTouchEnabled(false)
        chart.isDragEnabled = false
        chart.setScaleEnabled(true)
        chart.setDrawGridBackground(false)
        chart.setPinchZoom(false)
        chart.setBackgroundColor(Color.WHITE)
        val data = LineData()
        data.setValueTextColor(Color.GREEN)
        chart.data = data

        val xl = chart.xAxis
        xl.textColor = ContextCompat.getColor(chart.context, R.color.purple_gray)
        xl.setDrawGridLines(false)
        xl.setAvoidFirstLastClipping(true)
        xl.isEnabled = true

        val leftAxis = chart.axisLeft
        leftAxis.textColor = ContextCompat.getColor(chart.context, R.color.purple_gray)
        leftAxis.axisLineColor = ContextCompat.getColor(chart.context, R.color.purple_gray)
        leftAxis.gridColor = ContextCompat.getColor(chart.context, R.color.purple_gray)
        leftAxis.axisMaximum = axisMaximum
        leftAxis.axisMinimum = 0f
        leftAxis.setDrawZeroLine(true)
        leftAxis.setDrawGridLines(true)

        val rightAxis = chart.axisRight
        rightAxis.isEnabled = false
    }


    @JvmStatic
    @BindingAdapter(
        "accountChartData",
        "chartColor",
        "markerColor",
        "markerTitleColor",
        "markerType",
        "chartAlpha",
        "touchEnabled",
        "xList",
    )
    fun setLineAccountChartData(
        chart: LineChart,
        data: List<Point>?,
        chartColor: Int = Color.WHITE,
        markerColor: Int = Color.WHITE,
        markerTitleColor: Int = Color.BLACK,
        markerType: Int = 1,
        chartAlpha: Int = 0,
        touchEnabled: Boolean = false,
        xList: List<String> = mutableListOf()
    ) {
        data ?: return
        // chart.setViewPortOffsets(0f, 0f, 0f, 0f)
        chart.setBackgroundColor(Color.TRANSPARENT)

        // no description text
        chart.description.isEnabled = false

        // enable touch gestures
        chart.setTouchEnabled(true)

        // enable scaling and dragging
        chart.isDragEnabled = false
        chart.setScaleEnabled(false)

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false)


        chart.setDrawGridBackground(false)
        chart.maxHighlightDistance = 300f
        chart.xAxis.isEnabled = xList.isNotEmpty()
        if (xList.isNotEmpty())
            chart.xAxis.apply {
                isEnabled = true
                setDrawGridLines(false)
                setLabelCount(xList.size, true)
                axisMinimum = 0f
                textSize = 8f
                labelRotationAngle = 90f
                textColor = Color.LTGRAY
                axisMaximum = xList.size.toFloat() - 1
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter =
                    IndexAxisValueFormatter(xList)

            }


        chart.axisLeft.apply {
            isEnabled = true
            setLabelCount(6, true)
            textColor = Color.LTGRAY
            setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
            setDrawGridLines(true)
            if (data.size == 1)
                axisMinimum = 0f
            textSize = 8f
            zeroLineColor = ContextCompat.getColor(chart.context, R.color.white_smoke)
            axisLineColor = ContextCompat.getColor(chart.context, R.color.white_smoke)
            gridColor = ContextCompat.getColor(chart.context, R.color.white_smoke)

        }




        chart.axisRight.isEnabled = false

        chart.marker = MyMarkerViewSmall(
            chart.context,
            chart,
            markerColor,
            markerTitleColor,
            markerType
        )


        setAccountData(chart, data, chartColor, chartAlpha)

        chart.legend.isEnabled = false

        chart.animateXY(1000, 1000)

        // don't forget to refresh the drawing
        chart.invalidate()

        val index = chart.data.dataSets[0].entryCount - 1

        val x = chart.data.dataSets[0].getEntryForIndex(index).x

        val y = chart.data.dataSets[0].getEntryForIndex(index).y

        chart.highlightValue(x, y, 0)
        chart.marker.refreshContent(
            chart.data.dataSets[0].getEntryForIndex(index),
            chart.getHighlightByTouchPoint(x, y)
        )
    }

    private fun setAccountData(
        chart: LineChart,
        points: List<Point>,
        chartColor: Int = Color.WHITE,
        chartAlpha: Int = 0
    ) {
        val set1: LineDataSet

        val entries = points.map { EntryPoint(it.x, it.y, it.percent, it.price) }

        if (chart.data != null &&
            chart.data.dataSetCount > 0
        ) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = entries
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(entries, "DataSet 1")
//            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
//            set1.cubicIntensity = 0.2f
            set1.setDrawFilled(true)
            set1.setDrawCircles(false)
            set1.lineWidth = 3f
            set1.circleRadius = 0f
            set1.setCircleColor(Color.TRANSPARENT)
            set1.color = chartColor
            set1.fillColor = chartColor
            set1.fillAlpha = chartAlpha
            set1.setDrawHorizontalHighlightIndicator(true)
            set1.fillFormatter =
                IFillFormatter { _, _ -> chart.axisLeft.axisMinimum }
            // create a data object with the data sets
            val data = LineData(set1)
            data.setValueTextSize(9f)
            data.setDrawValues(false)

            // set data
            chart.data = data
        }
    }

    private fun setTintColor(
        imageView: ImageView,
        context: Context,
        colorId: Int
    ) {
        imageView.setColorFilter(
            ContextCompat.getColor(context, colorId),
            PorterDuff.Mode.SRC_IN
        )
    }

    @JvmStatic
    @BindingAdapter("setPrice")
    fun setPrice(view: TextView, labelText: Long?) {
        view.text = Utils.separatorAmount(labelText.toString())
    }

    @JvmStatic
    @BindingAdapter( "arrayStringText", "colorArrayText")
    fun setArrayStringText(
        view: TextView,
        stringArray: Array<String>,
        colorArrayText: Int
    ) {
        view.text = BulletTextUtil.makeBulletListFromStringArrayResource(
            view.context.resources.getDimension(R.dimen.margin_s).toInt(),
            view.context,
            colorArrayText,
            stringArray
        )
    }


}