package com.paya.presentation.utils
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.text.TextUtils
import com.paya.presentation.utils.FormatterUtil.getSeparatedValue
import com.paya.presentation.utils.FormatterUtil.getSeparatedValueAsInt
import com.paya.presentation.utils.FormatterUtil.toPersianNumbers
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

fun String.startWithCountryCodeMobile(): String {
    if (startsWith("0", false)) {
        val newMobile = substring(1, length)
        return "+98$newMobile"
    }
    return this
}

val String.space: String
    get() = " "

/*fun String.startWithToman(): String {
    val title = toPersianNumbers(getSeparatedValue(this.toInt()))
    FormatterUtil.toPersianNumbers(FormatterUtil.getSeparatedValue(value))
    return "$title تومان "
}*/
fun Double.toPersianSeparatedValue(): String {
    val title = toPersianNumbers(getSeparatedValue(this))
    return "$title تومان "
}

fun Int.toPersianSeparatedValue(): String {
    val title = toPersianNumbers(getSeparatedValueAsInt(this))
    return "$title تومان "
}

object FormatterUtil {
    /**
     * persian numbers codes
     */
    private val persianNumbers = charArrayOf('\u06f0', '\u06f1', '\u06f2', '\u06f3', '\u06f4', '\u06f5', '\u06f6', '\u06f7', '\u06f8', '\u06f9')
    private val arabicNumbers = charArrayOf('٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩')
    private val englishNumbers = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
    private const val ibanPrefix = "IR"

    // const values to detect deposit number in a text
    private const val depositNumberExtractionPattern = "(\\d{3,4}(\\n|\\t|-|\\s){1,4}\\d{2,3}(\\n|\\t|-|\\s){1,4}\\d{1,8}(\\n|\\t|-|\\s){1,4}\\d{1,3})"
    private const val depositNumberSplitterPattern = "(\\n|\\t|-|\\s){1,4}"
    private const val depositDelimiter = "-"

    private const val panNumberExtractionPattern = "((\\d{16})|(\\d{4}(\\n|\\t|-|\\s){1,4}\\d{4}(\\n|\\t|-|\\s){1,4}\\d{4}(\\n|\\t|-|\\s){1,4}\\d{4}))"

    private const val billIdAndPayIdExtractionPattern = "(\\d{8,11})([1-9]{1})(\\d{1})([^\\d]+)(\\d{5,13})"
    private const val billIdAndPayIdSplitterPattern = "([^\\d]+)"

    /**
     * converts english numbers of a string into persian numbers

     * @param string the string that its numbers will be converted to persian
     * *
     * @return the converted string
     */
    fun toPersianNumbers(string: String): String {
        var str = string
        persianNumbers.indices.forEach { i -> str = str.replace(englishNumbers[i], persianNumbers[i]) }
        return str
    }

    fun toEnglishNumbers(string: String): String {
        var str = string
        persianNumbers.indices.forEach { i -> str = str.replace(persianNumbers[i], englishNumbers[i]) }
        arabicNumbers.indices.forEach { i -> str = str.replace(arabicNumbers[i], englishNumbers[i]) }
        return str
    }

    fun getSeparatedValue(amount: Double): String {
        val numberFormat = NumberFormat.getInstance(Locale.ENGLISH)
        val formatter = (numberFormat as DecimalFormat).apply {
            applyPattern("#,###.##")
        }
        return formatter.format(amount)
    }

    fun getSeparatedValueAsInt(amount: Int): String {
        val numberFormat = NumberFormat.getInstance(Locale.ENGLISH)
        val formatter = (numberFormat as DecimalFormat).apply {
            applyPattern("#,###.##")
        }
        return formatter.format(amount)
    }

    fun getSplitString(string: String?, space: Int): String? = when (string) {
        null -> null
        else -> {
            val typeString = ArrayList<String>()
            var cardNumber = ""
            var tempSpace = ""
            (0 until space).forEach { _ -> tempSpace = tempSpace.plus(" ") }
            var index = 0
            while (index < string.length) {
                typeString.add(string.substring(index, Math.min(index + 4, string.length)))
                index += 4
            }
            for (i in typeString.indices) cardNumber = when (i) {
                0 -> typeString[i]
                else -> cardNumber.plus(tempSpace.plus(typeString[i]))
            }
            cardNumber
        }
    }

    fun getIbanFormattedString(iban: String): String {
        val middle = iban.removePrefix(ibanPrefix)
        val start = middle.take(2)
        val end = middle.takeLast(2)
        return ibanPrefix.plus(" - " + start + " " + getSplitString(middle.substring(2, middle.length - 2), 1) + " " + end)
    }

    fun getPriceFormatNumber(amount: Double, currency: String): String = getSeparatedValue(amount).plus(" ").plus(currency)

    fun getRawAmount(formattedAmount: String): String = formattedAmount.replace(",".toRegex(), "")

    fun getPhoneFormat(phoneNumber: String): String {
        var finalNumber = phoneNumber.replace("\\D+".toRegex(), "")
        finalNumber = finalNumber.replace("^(\\+989|989|00989)".toRegex(), "09")
        return finalNumber
    }


    fun getDepositNumberArrayFromText(text: String): List<String> {
        val matches = depositNumberExtractionPattern.toRegex().findAll(text, 0)
        return if (matches.count() > 0) {
            matches.first().value.replace(depositNumberSplitterPattern.toRegex(),
                depositDelimiter).split(depositDelimiter).map { toEnglishNumbers(it) }
        } else
            ArrayList()
    }

    fun getBillIdAndPayIdFromText(text: String): List<String> {
        val matches = billIdAndPayIdExtractionPattern.toRegex().findAll(toEnglishNumbers(text), 0)
        return if (matches.count() > 0)
            matches.first().value.split(billIdAndPayIdSplitterPattern.toRegex())
        else
            ArrayList()
    }



    fun joinToString(arr: Array<String?>, separator: String): String {
        if (arr.isEmpty()) return ""
        val sb = StringBuilder(256)
        if (arr[0] != null)
            sb.append(arr[0])

        for (i in 1 until arr.size) {
            if (arr[i] != null)
                sb.append(separator).append(arr[i])
        }
        return sb.toString()
    }
}