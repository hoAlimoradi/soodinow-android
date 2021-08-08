package com.paya.presentation.ui.aboutUs

import com.paya.presentation.ui.whySoodinow.WhySoodinowAdapter

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.paya.domain.models.repo.AboutUsModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.AboutUsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_about_us.*
import kotlinx.android.synthetic.main.fragment_why_soodinow.*
import kotlinx.android.synthetic.main.fragment_why_soodinow.backButton

@AndroidEntryPoint
class AboutUsFragment : BaseFragment<AboutUsViewModel>() {

    private var adapter: AboutUsAdapter? = null
    private val viewModel: AboutUsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about_us, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            it.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            it.window.statusBarColor = resources.getColor(R.color.green)
        }

        backButton.setOnClickListener { findNavController().popBackStack() }
        observe(viewModel.aboutUSListMutableLiveData, ::onDataReady)

        val manager = LinearLayoutManager(context)
        adapter = AboutUsAdapter()
        adapter?.let {
            aboutUsRecycleView.adapter = it
            aboutUsRecycleView.layoutManager = manager
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWhySoodinows()
    }

    private fun onDataReady(resource: Resource<List<AboutUsModel>>){
        when (resource.status) {
            Status.SUCCESS -> resource.data?.let { aboutUsModelList ->
                val aboutUsModelArrayList  = ArrayList<AboutUsModel>()
                aboutUsModelList.forEach {
                    aboutUsModelArrayList.add(it)
                }
                adapter?.let {
                    it.clear()
                    it.addAllData(aboutUsModelArrayList)
                }
            }
            else -> return
        }
    }

    override fun onDestroyView() {
        activity?.let {
            it.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            it.window.statusBarColor = resources.getColor(R.color.white)
        }
        adapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override val baseViewModel: BaseViewModel
        get() = viewModel


}
