package com.mrebollob.leitnerbox.presentation.main.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.exception.Failure
import com.mrebollob.leitnerbox.domain.extension.toast
import com.mrebollob.leitnerbox.presentation.about.AboutActivity
import com.mrebollob.leitnerbox.presentation.platform.BaseFragment
import com.mrebollob.leitnerbox.presentation.settings.SettingsActivity

class ProfileFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.profile_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            R.id.navigate_to_settings -> {
                SettingsActivity.open(requireContext())
                true
            }
            R.id.navigate_to_about -> {
                AboutActivity.open(requireContext())
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

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
