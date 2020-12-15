package com.plusmobileapps.customviewtesting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.plusmobileapps.plusmobileappsui.MyCustomView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<MyCustomView>(R.id.my_custom_view).toggleLock()
    }
}