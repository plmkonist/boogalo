package com.example.myapplication

import com.example.myapplication.R
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class GameOver : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameover)
        val score = intent.getIntExtra("finalScore", 0)
        val finals = findViewById<TextView>(R.id.scoreText)
        finals.text = score.toString()
        val playAgain = findViewById<Button>(R.id.playAgain)
        val main = Intent(this, Main2Activity::class.java)
        playAgain.setOnClickListener { startActivity(main) }
    }
}