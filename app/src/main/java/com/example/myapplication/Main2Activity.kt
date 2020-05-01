package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R


class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val gameButton = findViewById<Button>(R.id.Game)
        val songButton = findViewById<Button>(R.id.Options)
        val opAct = Intent(this, OptionsActivity::class.java)
        val diffAct = Intent(this, DifficultyActivity::class.java)
        diffAct.putExtra("song", 0)
        gameButton.setOnClickListener { startActivity(opAct) }
        songButton.setOnClickListener {startActivity(diffAct)}
    }
}
