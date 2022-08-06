package com.paya.presentation.ui.whySoodinow

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.paya.domain.models.repo.WhySoodinowModel
import com.paya.domain.tools.Resource
import com.paya.domain.tools.Status
import com.paya.presentation.R
import com.paya.presentation.base.BaseFragment
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.utils.observe
import com.paya.presentation.viewmodel.WhySoodinowViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_why_soodinow.*

@AndroidEntryPoint
class WhySoodinowFragment : BaseFragment<WhySoodinowViewModel>() {

    private var adapter: WhySoodinowAdapter? = null
    private val viewModel: WhySoodinowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_why_soodinow, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
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
        observe(viewModel.whySoodinowListMutableLiveData, ::onDataReady)

        val manager = LinearLayoutManager(context)
        adapter = WhySoodinowAdapter()
        adapter?.let {
            whySoodinowsRecycleView.adapter = it
            whySoodinowsRecycleView.layoutManager = manager
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getWhySoodinows()
    }

    private fun onDataReady(resource: Resource<List<WhySoodinowModel>>){
        when (resource.status) {
            Status.SUCCESS -> resource.data?.let { whySoodinows ->
                val whySoodinowModelArrayList  = ArrayList<WhySoodinowModel>()
                whySoodinows.forEach {
                    whySoodinowModelArrayList.add(it)
                }
                adapter?.let {
                    it.clear()
                    Log.e("", whySoodinows.toString())
                    it.addAllData(whySoodinowModelArrayList)
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

    private fun backPressed() {
        activity?.let {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
            it.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            it.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
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