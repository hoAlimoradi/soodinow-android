package com.paya.presentation.ui.updateApp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.paya.presentation.R
import com.paya.presentation.utils.*
import kotlinx.android.synthetic.main.dialog_update_app.*

class UpdateAppDialog : DialogFragment() {

    private var isForce = false
    private var link = ""
    var cancelDialog: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        return inflater.inflate(R.layout.dialog_update_app, container, false)
    }

    override fun onResume() {
        super.onResume()
        setFullScreen()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        closeBtn.visibility = if (isForce) View.GONE else View.VISIBLE
        cancelBtn.visibility = if (isForce) View.GONE else View.VISIBLE
        isCancelable = !isForce
        closeBtn.setOnClickListener {
            cancelDialog()
            dismissAllowingStateLoss()
        }
        cancelBtn.setOnClickListener {
            cancelDialog()
            dismissAllowingStateLoss()
        }
        updateBtn.setOnClickListener {
            openUrl(link)
        }
        versionTxt.text = "  نسخه  ${context?.let { getVersionName(it) }}"
    }

    fun setForce(force: Boolean) : UpdateAppDialog {
        isForce = force
        return this
    }

    fun setLink(link:String) : UpdateAppDialog{
        this.link = link
        return this
    }
}