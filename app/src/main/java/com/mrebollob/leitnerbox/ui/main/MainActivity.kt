package com.mrebollob.leitnerbox.ui.main

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.model.Status
import com.mrebollob.leitnerbox.ui.BaseActivity
import com.mrebollob.leitnerbox.ui.main.adapter.LevelsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    val mainViewModel: MainViewModel by viewModel()
    private val levelsAdapter = LevelsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()

        mainViewModel.getLevels().observe(this, Observer { result ->
            when (result?.status) {
                Status.SUCCESS -> result.data?.let { levelsAdapter.levels = it }
                Status.ERROR -> {
                }
                Status.LOADING -> {
                }
            }
        })
    }

    private fun initUI() {
//        setSupportActionBar(toolbar)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        levelsListView.adapter = levelsAdapter
    }
}
