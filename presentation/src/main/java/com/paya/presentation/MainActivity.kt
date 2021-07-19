package com.paya.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.paya.presentation.base.BaseActivity
import com.paya.presentation.base.BaseViewModel
import com.paya.presentation.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

const val IS_LOGIN: String = "is_login"

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActivityViewModel>() {
    private val mViewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeColorStatusBar()
        setContentView(R.layout.activity_main)

        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment)
        val inflater = navHostFragment.navController.navInflater
        val graph = inflater.inflate(R.navigation.nav_graph)
        if (intent==null || !intent.getBooleanExtra(IS_LOGIN,false)) {
            graph.startDestination = R.id.hintFragment
        } else {
            graph.startDestination = R.id.mainFragment
        }
        navHostFragment.navController.graph = graph
    }
    
    private fun changeColorStatusBar() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.statusBarColor = Color.WHITE;

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent==null || !intent.getBooleanExtra(IS_LOGIN,false)) {
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.hintFragment
            )
        } else {
            findNavController(R.id.nav_host_fragment).navigate(
                R.id.mainFragment
            )
        }
    }

    override val baseViewModel: BaseViewModel
        get() = mViewModel

    companion object {

        @JvmStatic
        fun createIntent(context: Context, isLogin: Boolean): Intent {
            return Intent(context, MainActivity::class.java).putExtra(IS_LOGIN, isLogin)
        }

    }


}