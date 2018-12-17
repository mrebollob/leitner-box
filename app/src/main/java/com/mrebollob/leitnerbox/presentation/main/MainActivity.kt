package com.mrebollob.leitnerbox.presentation.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.presentation.BaseActivity
import com.mrebollob.leitnerbox.presentation.about.AboutActivity
import com.mrebollob.leitnerbox.presentation.intro.IntroActivity
import com.mrebollob.leitnerbox.presentation.leitnerbox.LeitnerBoxFragment
import com.mrebollob.leitnerbox.presentation.leitnerbox.adapter.LevelsAdapter
import com.mrebollob.leitnerbox.presentation.settings.SettingsActivity
import com.mrebollob.leitnerbox.util.extensions.replaceFragment
import org.koin.android.ext.android.inject


class MainActivity : BaseActivity(), MainView, LeitnerBoxFragment.LeitnerBoxFragmentListener {

    val presenter: MainPresenter by inject()
    private val levelsAdapter = LevelsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.attachView(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.navigate_to_settings -> {
                presenter.onSettingsClick()
                true
            }
            R.id.navigate_to_about -> {
                presenter.onAboutClick()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun showLeitnerView() {
        val fragment = LeitnerBoxFragment.newInstance()
        replaceFragment(fragment, R.id.fragment_container)
    }

    override fun onDoneClick() {

    }

    override fun goToIntroScreen() {
        IntroActivity.open(this)
        finish()
    }

    override fun goToSettingsScreen() {
        SettingsActivity.open(this)
    }

    override fun goToAboutScreen() {
        AboutActivity.open(this)
    }
}
