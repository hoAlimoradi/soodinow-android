package com.paya.presentation.utils.picker

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.paya.presentation.R
import com.paya.presentation.utils.setWidthPercent
import kotlinx.android.synthetic.main.dialog_picker_view.*

class PickerViewDialog : DialogFragment() {

    private val list = mutableListOf<String>()
    private var title = ""
    private var selectionIndex = 0
    private var item: ((String, Int) -> Unit?)? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_picker_view, container, false)
    }

    override fun onResume() {
        super.onResume()
        setWidthPercent(80)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wheelView.setItems(list)
        wheelView.setSelection(selectionIndex)

        titlePicker.text = title
        cancelBtn.setOnClickListener {
            dismissAllowingStateLoss();
        }
        selectBtn.setOnClickListener {
            item?.let { it1 -> it1(wheelView.getSelectedItem,wheelView.getSelectedIndex) }
            dismissAllowingStateLoss();
        }
    }

    fun selectionIndex(index:Int) :PickerViewDialog {
        this.selectionIndex = index
        return this
    }

    fun setTitle(title: String): PickerViewDialog {
        this.title = title
        return this
    }

    fun setItems(list: List<String>): PickerViewDialog {
        this.list.addAll(list)
        return this
    }

    fun setListenerSelection(item: (item:String,index:Int) -> Unit): PickerViewDialog {
        this.item = item
        return this
    }
}