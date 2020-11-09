package com.paya.presentation.ui.question

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paya.domain.models.repo.QuestionsRepoModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.databinding.FragmentQuestionBinding
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.QuestionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionFragment : Fragment() {
	
	private lateinit var mBinding: FragmentQuestionBinding
	private val mViewModel: QuestionViewModel by viewModels()
	
	override fun onCreateView(
		inflater: LayoutInflater,container: ViewGroup?,
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
	
	override fun onViewCreated(view: View,savedInstanceState: Bundle?) {
		super.onViewCreated(view,savedInstanceState)
		mBinding.submitBtn.setOnClickListener {
			findNavController().navigate(
				QuestionFragmentDirections.navigateToInvestment()
			)
		}
		mViewModel.getAllQuestions()
		observe(mViewModel.questions, ::onQuestionsProvided)
	}
	
	private fun onQuestionsProvided(questionsResource: Resource<List<QuestionsRepoModel>>){
		if (questionsResource.status == Status.SUCCESS){
			val questions = questionsResource.data ?: return
			
			val manager = LinearLayoutManager(requireContext())
			val adapter = QuestionAdapter(questions)
			mBinding.questionRecycler.layoutManager = manager
			mBinding.questionRecycler.adapter = adapter
		}
	}
	
}