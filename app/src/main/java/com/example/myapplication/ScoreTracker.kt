package com.example.myapplication

import android.widget.ImageButton

class ScoreTracker internal constructor(var button: ImageButton?, var time: Long, var clicked: Boolean = true) {
    fun reset(b: ImageButton?, t: Long) {
        clicked = false
        button = b
        time = t
    }

    fun clicked() {
        clicked = true
    }

}