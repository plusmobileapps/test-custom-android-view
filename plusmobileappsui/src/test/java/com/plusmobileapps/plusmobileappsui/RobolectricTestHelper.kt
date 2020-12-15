package com.plusmobileapps.plusmobileappsui

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.DrawableRes
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.robolectric.Shadows.shadowOf

fun View.assertBackground(@DrawableRes expected: Int) {
    assertEquals(expected, shadowOf(this.background).createdFromResId)
}

fun ImageButton.assertDrawableResource(@DrawableRes expected: Int) {
    assertEquals(expected, shadowOf(this.drawable).createdFromResId)
}

fun TextView.assertText(expected: String) {
    assertEquals(expected, this.text)
}