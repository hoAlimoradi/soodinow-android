package com.paya.presentation.ui.riskAssessment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.remote.AnswersQuestion
import com.paya.domain.models.remote.RiskAssessmentAnswers
import com.paya.domain.models.remote.RiskAssessmentQuestionRemoteModel
import com.paya.domain.models.remote.RiskAssessmentRequestAnswer
import com.paya.domain.models.repo.RiskAssessmentQuestionRepoModel
import com.paya.presentation.R
import com.paya.presentation.utils.customRadio.CustomRadioButton
import com.paya.presentation.utils.customRadio.CustomRadioGroup
import com.paya.presentation.utils.getIranSans
import com.paya.presentation.utils.loge
import kotlinx.android.synthetic.main.row_risk_assessment_question.view.*

class RiskAssessmentQuestionFragmentRecycleViewAdapter(val questionCallback: QuestionCallback, val list: List<RiskAssessmentQuestionRepoModel>) :
    RecyclerView.Adapter<RiskAssessmentQuestionFragmentRecycleViewAdapter.RiskAssessmentQuestionsFragmentRecycleViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RiskAssessmentQuestionsFragmentRecycleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.row_risk_assessment_question, parent, false)
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
                        answers = item.answers,
                        questionId = item.id
                    )
                }

                "multiple_check" -> {
                    //checkboxs
                    item.answers.forEach {
                        createCheckBox(
                            linearLayout = riskAssessmentQuestionParentLinearLayout,
                            message = it.title,
                            id = it.id,
                            questionId = item.id
                        )
                    }
                }

                "single_value" -> {
                    //seekbar
                    createSeekBae(riskAssessmentQuestionParentLinearLayout ,
                        id = item.id,
                        questionId = item.id)
                }

            }


        }
    }

    @SuppressLint("ResourceType")
    private fun createRadioGroup(linearLayout: LinearLayout,
                                 answers: List<RiskAssessmentAnswers>,
                                 questionId: Int) {

        var radioButtonArray: ArrayList<CustomRadioButton> = ArrayList()
        //var radioButtonArray: ArrayList<RadioButton> = ArrayList()


        // Create RadioButton Dynamically  CustomRadioButton
        answers.forEach {
            radioButtonArray.add(createCustomRadioButton(context = linearLayout.context, message = it.title, id = it.id ))
        }


        // Create RadioGroup Dynamically

        val radioGroup = CustomRadioGroup(linearLayout.context)
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
       // params.gravity = Gravity.RIGHT

        params.setMargins(40, 40, 40, 40)
        radioGroup.layoutParams = params
        radioGroup.layoutDirection = View.LAYOUT_DIRECTION_RTL

        radioButtonArray.forEach {
            radioGroup.addView(it)
        }

        linearLayout.addView(radioGroup)

       /* val onCheckedChangeListener =
            RadioGroup.OnCheckedChangeListener { group, checkedId ->
                if (checkedId != -1) {
                    radioGroup.setOnCheckedChangeListener(null)
                    radioGroup.clearCheck()
                    radioGroup.setOnCheckedChangeListener(onCheckedChangeListener)
                }
            }*/


        //
        radioGroup.setOnCheckedChangeListener(object: CustomRadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(
                radioGroup: View,
                radioButton: View?,
                isChecked: Boolean,
                checkedId: Int
            ) {
                val question = AnswersQuestion(id = questionId , type = "single_check")
                //val answer = AnswersQuestion(id = checkedId , type = "$checkedId")
                val answer = AnswersQuestion(id = checkedId , type = "id")
                val riskAssessmentRequestAnswer =  RiskAssessmentRequestAnswer(question = question, answers = listOf(answer))

                questionCallback.onQuestionClicked(riskAssessmentRequestAnswer)
            }
        })



    }


    private fun createCustomRadioButton(
        context: Context,
        message: String,
        id: Int
    ): CustomRadioButton {

        val radioButton = CustomRadioButton(context)
        radioButton.layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        radioButton.textView.text = message
        radioButton.id = id
        radioButton.imageView.setImageResource(R.drawable.ic_baseline_panorama_fish_eye_24)

        return radioButton

    }

    private fun createCheckBox(
        linearLayout: LinearLayout,
        message: String,
        id: Int,
        questionId: Int
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
        //checkBox.setCheckMarkDrawable(android.R.drawable.checkbox_off_background)
        checkBox.setCheckMarkDrawable(R.drawable.ic_baseline_check_box_outline_blank_24)

        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(60, 30, 60, 30)
        checkBox.layoutParams = layoutParams

        checkBox.setOnClickListener(View.OnClickListener {
            checkBox.isChecked = !checkBox.isChecked
            //checkBox.setCheckMarkDrawable(if (checkBox.isChecked) android.R.drawable.checkbox_on_background else android.R.drawable.checkbox_off_background)
            checkBox.setCheckMarkDrawable(if (checkBox.isChecked) R.drawable.ic_baseline_check_box_24 else R.drawable.ic_baseline_check_box_outline_blank_24)

            if (checkBox.isChecked) {
                val question = AnswersQuestion(id = questionId , type = "multiple_check")
                Log.e("", "${checkBox.id}")
                //val answer = AnswersQuestion(id = checkBox.id , type = "${checkBox.id}")
                val answer = AnswersQuestion(id = checkBox.id , type = "id")
                val riskAssessmentRequestAnswer =  RiskAssessmentRequestAnswer(question = question, answers = listOf(answer))
                questionCallback.onQuestionClicked(riskAssessmentRequestAnswer)
            }

        })

        // Add Checkbox to LinearLayout
        linearLayout.addView(checkBox)

    }

    private fun createSeekBae(linearLayout: LinearLayout,
                              id: Int,
                              questionId: Int) {
        val seekBar = SeekBar(linearLayout.context)
        val layoutParams = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(30, 30, 30, 30)
        seekBar.layoutParams = layoutParams

        // Add SeekBar to LinearLayout
        linearLayout.addView(seekBar)

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
                val question = AnswersQuestion(id = questionId , type = "single_value")
                //val answer = AnswersQuestion(id = id , type = "${seekBar.progress}" )
                val answer = AnswersQuestion(id = id , type = "id" )
                val riskAssessmentRequestAnswer =  RiskAssessmentRequestAnswer(question = question, answers = listOf(answer))
                questionCallback.onQuestionClicked(riskAssessmentRequestAnswer)
            }
        })
    }

    class RiskAssessmentQuestionsFragmentRecycleViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    interface QuestionCallback {
        fun onQuestionClicked(riskAssessmentRequestAnswer: RiskAssessmentRequestAnswer)
    }
}