package com.mrebollob.leitnerbox.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.domain.extension.navigate
import com.mrebollob.leitnerbox.presentation.main.home.CountdownFragment
import com.mrebollob.leitnerbox.presentation.main.journal.JournalFragment
import com.mrebollob.leitnerbox.presentation.main.profile.ProfileFragment
import com.mrebollob.leitnerbox.presentation.platform.BaseActivity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun layoutId(): Int = R.layout.activity_main
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navigate(CountdownFragment.newInstance())
        initNavigation()
    }

    private fun initNavigation() {
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    navigate(CountdownFragment.newInstance())
                    true
                }
                R.id.navigation_journal -> {
                    navigate(JournalFragment.newInstance())
                    true
                }
                R.id.navigation_profile -> {
                    navigate(ProfileFragment.newInstance())
                    true
                }
            }
            false
        }
    }

    companion object Navigator {

        fun open(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }
}
