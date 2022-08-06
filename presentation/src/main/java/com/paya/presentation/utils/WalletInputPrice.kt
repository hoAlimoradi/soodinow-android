package com.paya.presentation.utils


import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.paya.presentation.R
import kotlinx.android.synthetic.main.input_price.view.*
import kotlinx.android.synthetic.main.wallet_input_price.view.*
import kotlinx.coroutines.CoroutineScope

class WalletInputPrice @JvmOverloads constructor(
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

    //wallet_input_price
    init {
        View.inflate(context, R.layout.wallet_input_price, this)
        /*minusPriceConstraintLayout.setOnClickListener {
            setPrice(false)
        }
        plusPriceConstraintLayout.setOnClickListener {
            setPrice(true)
        }*/
    }

    /*fun setPrice(plus: Boolean) {
        var price = wealthValueEditText.text.toString()
        if (price.isNullOrEmpty())
            price = "0"
        val changePrice = Utils.getAmount(price) ?: 0
        if ((!plus && changePrice == 0L) || (!plus && changePrice < PRICE_MINUS_PLUS))
            return
        if (minPrice > 0 && changePrice < minPrice) {
            walletInputPriceDescription.text = "مبلغ نمی تواند از" + Utils.separatorAmount(minPrice) + "کمتر باشد"
            showErrorMessage.invoke("مبلغ نمی تواند از" + Utils.separatorAmount(minPrice) + "کمتر باشد")
            return
        }
        if (maxPrice > 0 && changePrice > maxPrice) {
            walletInputPriceDescription.text = "مبلغ نمی تواند از" + Utils.separatorAmount(maxPrice) + "بیشنر باشد"
            showErrorMessage.invoke("مبلغ نمی تواند از" + Utils.separatorAmount(maxPrice) + "بیشنر باشد")
            return
        }
        walletInputPriceDescription.text = getStringByResId(R.string.min_requirment_invest)
        setPrice((if (plus) changePrice + PRICE_MINUS_PLUS else changePrice - PRICE_MINUS_PLUS).toString())
    }*/

    fun setupWatcherPrice(lifecycleScope: CoroutineScope? = null, onTextChanged: (String) -> Unit) {
        if (lifecycleScope==null)
            return
        val watcher = NumberTextWatcher(
            wealthValueEditText,
            ",###",
            lifecycleScope
        ) { onTextChanged(getPrice()) }

        wealthValueEditText.addTextChangedListener(watcher)
    }


    fun setPrice(price: String) {
        wealthValueEditText.setText(Utils.separatorAmount(price))
    }



    fun getPrice(): String {
        return wealthValueEditText.text.toString()
    }

    fun getPriceLong(): Long {
        return Utils.getAmount(wealthValueEditText.text.toString()) ?: 0
    }

   /* fun setMessage(message: String) {
        walletInputPriceDescription.text = message
    }*/

    var showErrorMessage: (message: String) -> Unit = {}

}
