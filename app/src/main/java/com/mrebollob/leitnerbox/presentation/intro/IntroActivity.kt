package com.mrebollob.leitnerbox.presentation.intro

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.AppIntro2
import com.github.paolorotolo.appintro.AppIntroFragment
import com.github.paolorotolo.appintro.model.SliderPage
import com.mrebollob.leitnerbox.R
import com.mrebollob.leitnerbox.presentation.main.MainActivity


class IntroActivity : AppIntro2() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val firstSlide = SliderPage()
        firstSlide.title = getString(R.string.intro_title_1)
        firstSlide.description = getString(R.string.intro_descripcion_1)
        firstSlide.imageDrawable = R.drawable.ic_about
        firstSlide.bgColor = ContextCompat.getColor(this, R.color.level_1)

        val secondSlide = SliderPage()
        secondSlide.title = getString(R.string.intro_title_2)
        secondSlide.description = getString(R.string.intro_descripcion_2)
        secondSlide.imageDrawable = R.drawable.ic_settings
        secondSlide.bgColor = ContextCompat.getColor(this, R.color.level_2)

        setColorTransitionsEnabled(true)
        addSlide(AppIntroFragment.newInstance(firstSlide))
        addSlide(AppIntroFragment.newInstance(secondSlide))

        showSkipButton(false)
        isProgressButtonEnabled = true
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)

        loadMainActivity()
        finish()
    }

    private fun loadMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    companion object Navigator {

        fun open(context: Context) {
            val intent = Intent(context, IntroActivity::class.java)
            context.startActivity(intent)
        }
    }
}
