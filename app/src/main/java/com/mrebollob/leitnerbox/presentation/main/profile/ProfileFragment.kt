package com.mrebollob.leitnerbox.presentation.main.profile

import android.os.Bundle
import android.view.View
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.extension.toast
import com.mrebollob.leitnerbox.presentation.platform.BaseFragment

class ProfileFragment : BaseFragment() {

    override fun layoutId(): Int = R.layout.fragment_profile
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun handleError(failure: Failure?) {
        context?.toast(getString(R.string.generic_error))
    }

    companion object {

        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
