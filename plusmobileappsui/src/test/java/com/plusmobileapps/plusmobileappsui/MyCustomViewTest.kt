package com.plusmobileapps.plusmobileappsui

import android.app.Activity
import android.os.Build
import android.widget.ImageButton
import android.widget.TextView
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MyCustomViewTest {

    private lateinit var myCustomView: MyCustomView
    private lateinit var lockButton: ImageButton
    private lateinit var lockDescription: TextView

    private val expectedUnlockText = "some unlock text"
    private val expectedLockText = "some locked text"

    private fun setUp(isLocked: Boolean) {
        val activityController = Robolectric.buildActivity(Activity::class.java)
        val activity = activityController.get()
        val attributeSet = with(Robolectric.buildAttributeSet()) {
            addAttribute(R.attr.unlockLabel, expectedUnlockText)
            addAttribute(R.attr.lockLabel, expectedLockText)
            addAttribute(R.attr.isLocked, isLocked.toString())
            build()
        }
        myCustomView = MyCustomView(activity, attributeSet)
        lockButton = myCustomView.findViewById(R.id.lock_button)
        lockDescription = myCustomView.findViewById(R.id.lock_status_description)
    }

    @Test
    fun `toggle lock - should be locked`() {
        setUp(isLocked = false)
        myCustomView.toggleLock()
        assertEquals(expectedLockText, lockDescription.text)
        assertEquals(R.drawable.ic_lock_24px, shadowOf(lockButton.drawable).createdFromResId)
    }

    @Test
    fun `toggle lock - should be unlocked`() {
        setUp(isLocked = true)
        myCustomView.toggleLock()
        assertEquals(expectedUnlockText, lockDescription.text)
        assertEquals(R.drawable.ic_lock_open_24px, shadowOf(lockButton.drawable).createdFromResId)
    }

    @Test
    fun `root shouldn't have ripple when locked and only unlock with image button`() {
        TODO()
    }

    @Test
    fun `attributes set text and to locked state`() {
        TODO()
    }
}