package com.plusmobileapps.plusmobileappsui

import android.app.Activity
import android.os.Build
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.O_MR1])
class MyCustomViewTest {

    private lateinit var myCustomView: MyCustomView
    private lateinit var rootView: ConstraintLayout
    private lateinit var lockButton: ImageButton
    private lateinit var lockDescription: TextView

    private val lockedListener: (Boolean) -> Unit = mockk()

    private val expectedUnlockText = "some unlock text"
    private val expectedLockText = "some locked text"

    private fun setUp(isLocked: Boolean) {
        val activityController = Robolectric.buildActivity(Activity::class.java)
        val activity = activityController.get()
        val attributeSet = buildAttributeSet {
            addAttribute(R.attr.unlockLabel, expectedUnlockText)
            addAttribute(R.attr.lockLabel, expectedLockText)
            addAttribute(R.attr.isLocked, isLocked.toString())
        }
        every { lockedListener(any()) } returns Unit
        myCustomView = MyCustomView(activity, attributeSet)
        rootView = myCustomView.findViewById(R.id.custom_view_root)
        lockButton = myCustomView.findViewById(R.id.lock_button)
        lockDescription = myCustomView.findViewById(R.id.lock_status_description)
        myCustomView.onLockListener = lockedListener
    }

    @Test
    fun `toggle lock - should be locked`() {
        setUp(isLocked = false)
        myCustomView.toggleLock()
        lockDescription.assertText(expectedLockText)
        lockButton.assertDrawableResource(R.drawable.ic_lock_24px)
    }

    @Test
    fun `toggle lock - should be unlocked`() {
        setUp(isLocked = true)
        myCustomView.toggleLock()
        lockDescription.assertText(expectedUnlockText)
        lockButton.assertDrawableResource(R.drawable.ic_lock_open_24px)
    }

    @Test
    fun `root shouldn't have ripple when locked and only unlock with image button`() {
        setUp(isLocked = true)

        myCustomView.performClick()
        lockButton.assertDrawableResource(R.drawable.ic_lock_24px)
        rootView.assertBackground(android.R.color.white)
        verify(exactly = 0) { lockedListener(any()) }

        lockButton.performClick()
        rootView.assertBackground(R.drawable.my_custom_ripple)
        lockButton.assertDrawableResource(R.drawable.ic_lock_open_24px)
        verify { lockedListener(false) }

        myCustomView.performClick()
        rootView.assertBackground(android.R.color.white)
        lockButton.assertDrawableResource(R.drawable.ic_lock_24px)
        verify { lockedListener(true) }
    }

    @Test
    fun `lock listener invoked - initial false then toggled to true`() {
        setUp(isLocked = false)

        myCustomView.toggleLock()

        verify { lockedListener(true) }
    }

    @Test
    fun `lock listener invoked - initial true then toggled to false`() {
        setUp(isLocked = true)

        myCustomView.toggleLock()

        verify { lockedListener(false) }
    }

    @Test
    fun `content description`() {
        setUp(isLocked = true)
        assertEquals(expectedLockText, myCustomView.contentDescription)
        myCustomView.toggleLock()
        assertEquals(expectedUnlockText, myCustomView.contentDescription)
    }

}