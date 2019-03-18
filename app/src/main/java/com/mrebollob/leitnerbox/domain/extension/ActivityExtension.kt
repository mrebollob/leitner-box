package com.mrebollob.leitnerbox.domain.extension

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.presentation.platform.BaseActivity
import com.mrebollob.leitnerbox.presentation.platform.BaseFragment


inline fun <reified T : ViewModel> BaseActivity.viewModel(
    factory: ViewModelProvider.Factory,
    body: T.() -> Unit
): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}

fun BaseActivity.navigate(baseFragment: BaseFragment) {
    supportFragmentManager.beginTransaction()
        .replace(R.id.container, baseFragment)
        .commit()
}