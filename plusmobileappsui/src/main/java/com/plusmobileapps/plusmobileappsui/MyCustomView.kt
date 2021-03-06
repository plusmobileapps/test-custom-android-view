package com.plusmobileapps.plusmobileappsui

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import kotlin.RuntimeException

class MyCustomView : CardView {

    private lateinit var rootView: ConstraintLayout
    private lateinit var lockButton: ImageButton
    private lateinit var lockDescription: TextView

    private var lockedIcon: Drawable? = null
    private var unlockedIcon: Drawable? = null

    private var lockedDescription: String = "The locked description was not set through xml attributes"
    private var unlockedDescription: String = "The unlocked description was not set through xml attributes"

    private var isLocked: Boolean = false

    var onLockListener: (Boolean) -> Unit = {}

    constructor(context: Context): super(context) {
        setup(context, null)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        setup(context, attrs)
    }

    private fun setup(context: Context, attrs: AttributeSet?) {
        View.inflate(context, R.layout.my_custom_view_layout, this)
        rootView = findViewById(R.id.custom_view_root)
        lockButton = findViewById(R.id.lock_button)
        lockDescription = findViewById(R.id.lock_status_description)
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.MyCustomView, 0, 0)
        isLocked = attributes.getBoolean(R.styleable.MyCustomView_isLocked, false)
        attributes.getString(R.styleable.MyCustomView_lockLabel)?.let {
            lockedDescription = it
        }
        attributes.getString(R.styleable.MyCustomView_unlockLabel)?.let {
            unlockedDescription = it
        }
        lockedIcon = attributes.getDrawable(R.styleable.MyCustomView_lockedIcon)
        unlockedIcon = attributes.getDrawable(R.styleable.MyCustomView_unlockedIcon)
        if (lockedIcon == null) throw RuntimeException("No lockedIcon set in xml attributes")
        if (unlockedIcon == null) throw RuntimeException("No unlockedIcon set in xml attributes")
        attributes.recycle()
        updateState()
        setOnClickListener { if(!isLocked) toggleLock() }
        lockButton.setOnClickListener { toggleLock() }
    }

    fun toggleLock() {
        isLocked = !isLocked
        updateState()
    }

    private fun updateState() {
        if (isLocked) {
            rootView.setBackgroundResource(android.R.color.white)
            lockButton.setImageDrawable(lockedIcon)
            lockButton.setImageResource(R.drawable.ic_lock_24px)
            lockDescription.text = lockedDescription
            contentDescription = lockedDescription
        } else {
            rootView.setBackgroundResource(R.drawable.my_custom_ripple)
            lockButton.setImageDrawable(unlockedIcon)
            lockDescription.text = unlockedDescription
            contentDescription = unlockedDescription
        }
        onLockListener(isLocked)
    }

}