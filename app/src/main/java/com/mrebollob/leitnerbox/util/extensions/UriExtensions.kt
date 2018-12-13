package com.mrebollob.leitnerbox.util.extensions

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import com.mrebollob.leitnerbox.R

fun Uri.loadCustomTabs(context: Context) = with(this) {
    val builder = CustomTabsIntent.Builder()
    builder.setToolbarColor(ContextCompat.getColor(context, R.color.primary_dark))
    builder.build().launchUrl(context, this)
}
