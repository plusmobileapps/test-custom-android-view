package com.plusmobileapps.plusmobileappsui

import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import org.junit.Assert.assertEquals
import org.robolectric.Robolectric
import org.robolectric.Shadows.shadowOf
import org.robolectric.android.AttributeSetBuilder

fun buildAttributeSet(attrs: AttributeSetBuilder.() -> Unit): AttributeSet {
    return with(Robolectric.buildAttributeSet()) {
        attrs()
        build()
    }
}

fun View.assertBackground(@DrawableRes expected: Int) {
    assertEquals(expected, shadowOf(this.background).createdFromResId)
}

fun ImageView.assertDrawableResource(@DrawableRes expected: Int) {
    assertEquals(expected, shadowOf(this.drawable).createdFromResId)
}

fun TextView.assertText(expected: String) {
    assertEquals(expected, this.text)
}