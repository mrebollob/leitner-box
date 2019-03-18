package com.mrebollob.leitnerbox.domain.extension

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.presentation.platform.BaseFragment

/*
    From https://medium.com/thoughts-overflow/how-to-add-a-fragment-in-kotlin-way-73203c5a450b
 */

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

inline fun FragmentManager.inTransactionWithAnimation(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().setCustomAnimations(
        R.animator.card_flip_right_in,
        R.animator.card_flip_right_out,
        R.animator.card_flip_left_in,
        R.animator.card_flip_left_out
    ).func().commit()
}

fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { add(frameId, fragment) }
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction { replace(frameId, fragment) }
}

fun AppCompatActivity.replaceFragmentWithAnimation(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransactionWithAnimation { replace(frameId, fragment) }
}

inline fun <reified T : ViewModel> BaseFragment.viewModel(
    factory: ViewModelProvider.Factory,
    body: T.() -> Unit
): T {
    val vm = ViewModelProviders.of(this, factory)[T::class.java]
    vm.body()
    return vm
}