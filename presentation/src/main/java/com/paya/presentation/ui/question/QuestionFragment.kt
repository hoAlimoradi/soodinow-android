package com.paya.presentation.ui.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.domain.models.repo.UserTestRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.databinding.FragmentQuestionBinding
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionFragment : BaseFragment<QuestionViewModel>() {

    private lateinit var mBinding: FragmentQuestionBinding
    private lateinit var adapter: QuestionAdapter
    private val mViewModel: QuestionViewModel by viewModels()
    private lateinit var questions: List<QuestionsRepoModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_question,
            container,
            false
        )

        mBinding.viewModel = mViewModel
        mBinding.lifecycleOwner = this

        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.submitBtn.setOnClickListener {
            val test = adapter.getUserTestResult()
            val questionSlug = test.map { it.q }
            val unAnsweredQuestions = mutableListOf<Int>()
            questions.forEachIndexed { i, q ->
                if (!questionSlug.contains(q.slug)) {
                    unAnsweredQuestions.add(i + 1)
                }
            }
            if (unAnsweredQuestions.isNotEmpty()) {
                val unAnsweredQuestionText = StringBuilder()
                unAnsweredQuestions.forEach {
                    unAnsweredQuestionText.append(it.toString())
                    unAnsweredQuestionText.append(" , ")
                }

                Toast.makeText(
                    requireContext(),
                    " لطفا به سوال های زیر پاسخ بدهید$unAnsweredQuestionText",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            mViewModel.submitTest(test)
        }
        mViewModel.getAllQuestions()
        observe(mViewModel.questions, ::onQuestionsProvided)
        observe(mViewModel.submitTestStatus, ::onTestSubmit)
    }

    private fun onTestSubmit(resource: Resource<UserTestRepoModel>) {
        if (resource.status == Status.SUCCESS) {
            findNavController().navigate(
                QuestionFragmentDirections.navigateToInvestment()
            )
        } else if (resource.status == Status.ERROR) {
            Toast.makeText(
                requireContext(),
                resource.message ?: "خطایی رخ داده است",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onQuestionsProvided(questionsResource: Resource<List<QuestionsRepoModel>>) {
        if (questionsResource.status == Status.SUCCESS) {
            questions = questionsResource.data ?: return

            val manager = LinearLayoutManager(requireContext())
            adapter = QuestionAdapter(questions)
            mBinding.questionRecycler.layoutManager = manager
            mBinding.questionRecycler.adapter = adapter
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel

}