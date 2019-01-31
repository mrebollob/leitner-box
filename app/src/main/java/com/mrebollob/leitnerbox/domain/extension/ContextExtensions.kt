package com.mrebollob.leitnerbox.domain.extension

import android.content.Context
import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.mrebollob.leitnerbox.R

fun Context.toast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

fun Context.loadCustomTabs(uri: Uri) = with(this) {
    val builder = CustomTabsIntent.Builder()
    builder.setToolbarColor(ContextCompat.getColor(this, R.color.primary_dark))
    builder.build().launchUrl(this, uri)
}
