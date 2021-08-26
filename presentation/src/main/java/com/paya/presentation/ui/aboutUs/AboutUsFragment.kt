package com.paya.presentation.ui.aboutUs

import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        changeStatusBarColorToGreen()

        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                backPressed()
            }
        })

        backButton.setOnClickListener {
            backPressed()
        }

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
        adapter = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun backPressed() {
        activity?.let {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            it.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            it.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            it.window.statusBarColor = ContextCompat.getColor(it.baseContext, R.color.white)
        }

        findNavController().popBackStack()
    }

    private fun changeStatusBarColorToGreen() {
        activity?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_IMMERSIVE
            }
            it.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            it.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            it.window.statusBarColor = ContextCompat.getColor(it.baseContext, R.color.green)
        }
    }

    override val baseViewModel: BaseViewModel
        get() = viewModel

}
