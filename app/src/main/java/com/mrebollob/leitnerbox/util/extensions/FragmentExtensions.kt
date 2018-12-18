package com.mrebollob.leitnerbox.util.extensions

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.mrebollob.leitnerbox.R

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