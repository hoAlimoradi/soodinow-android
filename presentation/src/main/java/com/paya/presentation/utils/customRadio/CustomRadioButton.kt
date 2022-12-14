package com.paya.presentation.utils.customRadio

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.core.widget.ImageViewCompat
import com.paya.presentation.R
import java.util.*

class CustomRadioButton : CardView, CustomRadioCheckable {

    //immutable
    val TOP = 0
    val RIGHT = 1
    val BOTTOM = 2
    val LEFT = 3

    //views
    lateinit var textView: TextView
    lateinit var imageView: AppCompatImageView

    //mutable
    var mChecked = false
    var mOnClickListener: View.OnClickListener? = null
    var mOnTouchListener: View.OnTouchListener? = null

    //variables
    var text: String = "Title"
    private var drawableIcon: Drawable? = null
    private var textSize: Float = 14f
    private var iconSize: Float = 68f
    private var textColor: Int = Color.GRAY
    private var pressedTextColor: Int = Color.BLACK
    private var iconColor: Int = Color.GRAY
    private var pressedIconColor: Int = Color.GREEN
    private var btnBackgroundColor: Int = Color.WHITE
    private var pressedBtnBackgroundColor: Int = Color.WHITE
    private var iconPosition: Int = RIGHT

    private val mOnCheckedChangeListeners = ArrayList<CustomRadioCheckable.OnCheckedChangeListener>()

    constructor(context: Context) : super(context) {
        initRadioImageButton()
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        parseAttributes(attrs)
        initRadioImageButton()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        parseAttributes(attrs)
        initRadioImageButton()
    }

    private fun parseAttributes(attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.RadioImageButton, 0, 0)

        try {

            text = when {
                ta.getString(R.styleable.RadioImageButton_text) != null -> ta.getString(R.styleable.RadioImageButton_text)!!
                else -> ""
            }
            drawableIcon = ta.getDrawable(R.styleable.RadioImageButton_drawableIcon)

            textSize = ta.getDimension(R.styleable.RadioImageButton_textSize, 14f)
            iconSize = ta.getDimension(R.styleable.RadioImageButton_iconSize, dpToPx(24).toFloat())

            textColor = ta.getColor(R.styleable.RadioImageButton_textColor, Color.GRAY)
            pressedTextColor = ta.getColor(R.styleable.RadioImageButton_selectedTextColor, Color.BLACK)
            iconColor = ta.getColor(R.styleable.RadioImageButton_iconColor, Color.GRAY)
            pressedIconColor = ta.getColor(R.styleable.RadioImageButton_selectedIconColor, Color.BLACK)
            btnBackgroundColor = ta.getColor(R.styleable.RadioImageButton_backgroundColorRadio, Color.WHITE)
            pressedBtnBackgroundColor = ta.getColor(R.styleable.RadioImageButton_selectedBackgroundColor, Color.LTGRAY)

            iconPosition = ta.getInteger(R.styleable.RadioImageButton_iconPosition, RIGHT)

        } finally {
            ta.recycle()
        }
    }

    private fun initRadioImageButton() {
        inflateView()
        bindView()
        //setCustomTouchListener()
    }

    private fun inflateView() {

        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.custom_radio_button, this, true)

        textView = findViewById(R.id.textView)
        imageView = findViewById(R.id.imageView)
    }

    private fun bindView() {

        if (text != "") {
            textView.text = text
        } else {
            textView.visibility = View.GONE
        }

        //card view default values
        //strokeWidth = dpToPx(1)
        cardElevation = dpToPx(1).toFloat()
        //strokeColor = iconColor
        setCardBackgroundColor(btnBackgroundColor)
        setContentPadding(dpToPx(8), dpToPx(8), dpToPx(8), dpToPx(8))

        //image view default properties
        ImageViewCompat.setImageTintList(imageView, ColorStateList.valueOf(iconColor))
        imageView.layoutParams.height = iconSize.toInt()
        imageView.layoutParams.width = iconSize.toInt()
        imageView.setImageDrawable(drawableIcon)

        imageView.setOnClickListener {
            isChecked = true
        }

    }

    private fun dpToPx(dp: Int): Int {
        val displayMetric = context.resources.displayMetrics
        return dp * (displayMetric.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
    }

    /*override fun setOnClickListener(l: OnClickListener?) {
        mOnClickListener = l
    }*/

    @SuppressLint("ClickableViewAccessibility")
   /* private fun setCustomTouchListener() {
        super.setOnTouchListener(TouchListener())
    }

    private fun onTouchDown(motionEvent: MotionEvent) {
        isChecked = true
    }

    private fun onTouchUp(motionEvent: MotionEvent) {
        mOnClickListener?.onClick(this)
    }
*/
    fun setCheckedState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animateToSelected()
        }
    }

    fun setNormalState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            animateToNormal()
        }
    }



    override fun isChecked(): Boolean {
        return mChecked
    }

    override fun setChecked(checked: Boolean) {
        if (mChecked != checked) {
            mChecked = checked
            if (!mOnCheckedChangeListeners.isEmpty()) {
                for (i in mOnCheckedChangeListeners.indices) {
                    mOnCheckedChangeListeners.get(i).onCheckedChanged(this, mChecked)
                }
            }
            if (mChecked) {
                setCheckedState()
            } else {
                setNormalState()
            }
        }
    }

    override fun toggle() {
        isChecked = !mChecked
    }

    override fun addOnCheckChangeListener(onCheckedChangeListener: CustomRadioCheckable.OnCheckedChangeListener?) {
        onCheckedChangeListener?.let { mOnCheckedChangeListeners.add(it) }
    }

    override fun removeOnCheckChangeListener(onCheckedChangeListener: CustomRadioCheckable.OnCheckedChangeListener?) {
        mOnCheckedChangeListeners.remove(onCheckedChangeListener)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun animateToSelected() {

        //float animator for elevation
      /*  ValueAnimator.ofFloat(dpToPx(1).toFloat(), dpToPx(8).toFloat()).apply {
            duration = 300
            addUpdateListener { cardElevation = it.animatedValue as Float }
            start()
        }

        */

        ValueAnimator.ofArgb(iconColor, pressedIconColor).apply {
            duration = 300
            addUpdateListener {
                ImageViewCompat.setImageTintList(imageView,
                    ColorStateList.valueOf(it.animatedValue as Int))

                imageView.setImageResource(R.drawable.ic_baseline_check_circle_24)

            }
            start()
        }
        //color animator for text color
        ValueAnimator.ofArgb(textColor, pressedTextColor).apply {
            duration = 300
            addUpdateListener {
                textView.setTextColor(it.animatedValue as Int)
            }
            start()
        }

        /*ValueAnimator.ofArgb(btnBackgroundColor, pressedBtnBackgroundColor).apply {
            duration = 300
            addUpdateListener { setCardBackgroundColor(it.animatedValue as Int) }
            start()
        }*/

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun animateToNormal() {

        //float animator for elevation
        /*ValueAnimator.ofFloat(dpToPx(8).toFloat(), dpToPx(1).toFloat()).apply {
            duration = 300
            addUpdateListener { cardElevation = it.animatedValue as Float }
            start()
        }*/

        //color animator for icon color
        ValueAnimator.ofArgb(pressedIconColor, iconColor).apply {
            duration = 300
            addUpdateListener {
                ImageViewCompat.setImageTintList(imageView,
                        ColorStateList.valueOf(it.animatedValue as Int))
                imageView.setImageResource(R.drawable.ic_baseline_panorama_fish_eye_24)

            }
            start()
        }

        //color animator for text color
        ValueAnimator.ofArgb(pressedTextColor, textColor).apply {
            duration = 300
            addUpdateListener {
                textView.setTextColor(it.animatedValue as Int)
            }
            start()
        }

        /*ValueAnimator.ofArgb(pressedBtnBackgroundColor, btnBackgroundColor).apply {
            duration = 300
            addUpdateListener { setCardBackgroundColor(it.animatedValue as Int) }
            start()
        }*/

    }


    /*private inner class TouchListener : View.OnTouchListener {

        override fun onTouch(v: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> onTouchDown(event)
                MotionEvent.ACTION_UP -> onTouchUp(event)
            }
            mOnTouchListener?.onTouch(v, event)
            return true
        }

    }*/

}