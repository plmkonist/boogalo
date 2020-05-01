package com.example.myapplication

import android.view.View
import android.widget.Button

class ScoreTracker internal constructor(var button: Button?, var time: Long, var clicked: Boolean = false, var ready: Boolean = false) {
    fun reset(b: Button?, t: Long) {
        clicked = false
        button = b
        time = t
    }

    fun clicked() {
        clicked = true
    }

}