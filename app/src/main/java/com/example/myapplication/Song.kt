package com.example.myapplication

import java.util.*
import kotlin.collections.ArrayList

class Song internal constructor(val name: String, val iD: Int, beats: String) {
    val noteTimes = ArrayList<Long>()

    fun processNoteTimes(s: String) {
        try {
            val arr = s.split(" ").toTypedArray()
            for (i in arr.indices) {
                noteTimes.add(java.lang.Long.valueOf(arr[i]))
            }
        } catch (e: Exception) {
        }
    }

    init {
        processNoteTimes(beats)
    }
}