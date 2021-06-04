package com.paya.presentation.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.paya.presentation.R
import kotlinx.android.synthetic.main.input_price.view.*
import kotlinx.coroutines.CoroutineScope

const val PRICE_MINUS_PLUS = 100000 //rial

class InputPrice @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

     var minPrice = 0L
        set(value) {
            field = value
        }
     var maxPrice = 0L
        set(value) {
            field = value
        }

    init {
        View.inflate(context, R.layout.input_price, this)
        minusPrice.setOnClickListener {
            setPrice(false)
        }
        plusPrice.setOnClickListener {
            setPrice(true)
        }
    }

    fun setPrice(plus: Boolean) {
        var price = input.text.toString()
        if (price.isNullOrEmpty())
            price = "0"
        val changePrice = Utils.getAmount(price) ?: 0
        if ((!plus && changePrice == 0L) || (!plus && changePrice < PRICE_MINUS_PLUS))
            return
        if (minPrice > 0 && changePrice < minPrice) {
            showErrorMessage.invoke("مبلغ نمی تواند از" + Utils.separatorAmount(minPrice) + "کمتر باشد")
            return
        }
        if (maxPrice > 0 && changePrice > maxPrice) {
            showErrorMessage.invoke("مبلغ نمی تواند از" + Utils.separatorAmount(maxPrice) + "بیشنر باشد")
            return
        }
        setPrice((if (plus) changePrice + PRICE_MINUS_PLUS else changePrice - PRICE_MINUS_PLUS).toString())
    }

    fun setupWatcherPrice(lifecycleScope: CoroutineScope? = null, onTextChanged: (String) -> Unit) {
        if (lifecycleScope==null)
            return
        val watcher = NumberTextWatcher(
            input,
            ",###",
            lifecycleScope
        ) { onTextChanged(getPrice()) }

        input.addTextChangedListener(watcher)
    }


    fun setPrice(price: String) {
        input.setText(Utils.separatorAmount(price))
    }

    fun setMessage(message: String) {
        description.text = message
    }

    fun getPrice(): String {
        return input.text.toString()
    }

    fun getPriceLong(): Long {
        return Utils.getAmount(input.text.toString()) ?: 0
    }

    var showErrorMessage: (message: String) -> Unit = {}

}