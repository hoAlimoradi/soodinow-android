package com.paya.presentation.ui.riskAssessment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.remote.RiskAssessmentAnswers
import com.paya.domain.models.remote.RiskAssessmentQuestionRemoteModel
import com.paya.presentation.R
import com.paya.presentation.utils.loge
import kotlinx.android.synthetic.main.row_risk_assessment_question.view.*

class RiskAssessmentQuestionFragmentRecycleViewAdapter(val list: List<RiskAssessmentQuestionRemoteModel>) :
    RecyclerView.Adapter<RiskAssessmentQuestionFragmentRecycleViewAdapter.RiskAssessmentQuestionsFragmentRecycleViewHolder>() {

    //lateinit var riskAssessmentQuestionParentLinearLayout: LinearLayout

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RiskAssessmentQuestionsFragmentRecycleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_risk_assessment_question, parent, false)
        //riskAssessmentQuestionParentLinearLayout = view.
        return RiskAssessmentQuestionsFragmentRecycleViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(
        holder: RiskAssessmentQuestionsFragmentRecycleViewHolder,
        position: Int
    ) {
        val item = list[position]
        with(holder.itemView) {
            riskAssessmentQuestionTitle.text = item.title
            when (item.type) {
                "single_check" -> {
                    //spinner createSpinner
                    createRadioGroup(
                        linearLayout = riskAssessmentQuestionParentLinearLayout,
                        answers = item.answers
                    )
                }
                "multiple_check" -> {
                    //checkboxs
                    item.answers.forEach {
                        createCheckBox(
                            linearLayout = riskAssessmentQuestionParentLinearLayout,
                            message = it.title,
                            id = it.id
                        )
                    }

                }
                "single_value" -> {
                    //seekbar
                    createSeekBae(riskAssessmentQuestionParentLinearLayout)
                }
                "multiple_value" -> {

                }

            }


        }
    }

    @SuppressLint("ResourceType")
    private fun createRadioGroup(linearLayout: LinearLayout, answers: List<RiskAssessmentAnswers>) {

        var radioButtonArray: ArrayList<RadioButton> = ArrayList()
        //var radioButtonArray: List<RadioButton>
        // Create RadioButton Dynamically

        // Create RadioButton Dynamically

        answers.forEach {
            radioButtonArray.add(createRadioButton(context = linearLayout.context, message = it.title, id = it.id ))
        }

     /*   val radioButton1 = RadioButton(linearLayout.context)
        radioButton1.layoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        radioButton1.layoutDirection = View.LAYOUT_DIRECTION_RTL
        radioButton1.text = "آن دورست که من دارم"
        radioButton1.id = 0

        val radioButton2 = RadioButton(linearLayout.context)
        radioButton2.layoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        radioButton2.text = "سلاام چطوری"
        radioButton2.id = 2

        radioButtonArray.add(radioButton1)
        radioButtonArray.add(radioButton2)*/
        // Create RadioGroup Dynamically
        val radioGroup = RadioGroup(linearLayout.context)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.RIGHT

        params.setMargins(40, 40, 40, 40)
        radioGroup.layoutParams = params

        if (linearLayout != null) {
            radioButtonArray.forEach {
                radioGroup.addView(it)
            }
            /* radioGroup.addView(radioButton1)
             radioGroup.addView(radioButton2)
             */
            linearLayout.addView(radioGroup)
            radioGroup.setOnCheckedChangeListener { group, checkedId ->
                /*var text = "you_selected"
                text += " " + if (checkedId == 0) radioButton1.text else radioButton2.text
                Toast.makeText(linearLayout.context, text, Toast.LENGTH_SHORT).show()*/
            }
        }
    }

    private fun createRadioButton(
        context: Context,
        message: String,
        id: Int
    ): RadioButton {

        val radioButton = RadioButton(context)
        radioButton.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        radioButton.text = message
        radioButton.id = id
        //Typeface
        /* radioButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.text_medium)) //<dimen name="text_medium">14sp</dimen>
         radioButton.setTextColor(ContextCompat.getColorStateList(context, R.color.radio_grey_text_selector))
         radioButton.typeface = Typeface.create("roboto", Typeface.NORMAL)
 */
        radioButton.layoutDirection = View.LAYOUT_DIRECTION_RTL
        //drawable
        /*radioButton.setButtonDrawable(0) // for removing default drawable
        radioButton.compoundDrawablePadding = 10
        radioButton.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.gostyle_radio_button_selector, 0) // for adding drawable on the right
        radioButton.gravity = Gravity.CENTER_VERTICAL*/

        return radioButton

    }


    fun createSpinner(linearLayout: LinearLayout, answers: List<RiskAssessmentAnswers>) {

        val spinner = Spinner(linearLayout.context)
        spinner.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        spinner.layoutDirection = View.LAYOUT_DIRECTION_RTL
        //var titleAnswers = arrayListOf<String>()
        var titleAnswers: ArrayList<String> = arrayListOf()
        loge("    titleAnswers ")
        for (i in 0..answers.size - 1) {
            loge("     " + i + "       ")
            loge(answers[i].title)
            titleAnswers.add(answers[i].title)
        }

        //val names = arrayOf("سلااام چطوری", "السلام علیک", "یا فاطمه زهرا", "یا امام غریب", "دلم برای زیارت ", "یا حسین", "یا علی")
        val arrayAdapter =
            ArrayAdapter(linearLayout.context, android.R.layout.simple_spinner_item, titleAnswers)
        spinner.adapter = arrayAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                //Toast.makeText(linearLayout.context, "selected_item" + " " + titleAnswers[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        // Add Spinner to LinearLayout
        linearLayout?.addView(spinner)

    }

    fun createCheckBox(
        linearLayout: LinearLayout,
        message: String,
        id: Int
    ) {

        // Create Checkbox Dynamically
        val checkBox = CheckedTextView(linearLayout.context)
        checkBox.layoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        checkBox.text = message
        checkBox.isChecked = false
        checkBox.id = id

        checkBox.setCheckMarkDrawable(android.R.drawable.checkbox_off_background)

        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(30, 30, 30, 30)
        checkBox.layoutParams = layoutParams

        checkBox.setOnClickListener(View.OnClickListener {
            checkBox.isChecked = !checkBox.isChecked
            checkBox.setCheckMarkDrawable(if (checkBox.isChecked) android.R.drawable.checkbox_on_background else android.R.drawable.checkbox_off_background)
            val msg =
                "pre_msg" + if (checkBox.isChecked) "checked" else "unchecked"

            // Toast.makeText(linearLayout.context, msg, Toast.LENGTH_SHORT).show()
        })


        // Add Checkbox to LinearLayout
        linearLayout.addView(checkBox)

    }

    private fun createSeekBae(linearLayout: LinearLayout) {
        val seekBar = SeekBar(linearLayout.context)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(30, 30, 30, 30)
        seekBar.layoutParams = layoutParams

        // Add SeekBar to LinearLayout
        linearLayout?.addView(seekBar)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Write code to perform some action when progress is changed.
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is started.
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // Write code to perform some action when touch is stopped.
                //Toast.makeText( linearLayout.context,    "Progress is " + seekBar.progress + "%",  Toast.LENGTH_SHORT ).show()
            }
        })
    }

    class RiskAssessmentQuestionsFragmentRecycleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)
}