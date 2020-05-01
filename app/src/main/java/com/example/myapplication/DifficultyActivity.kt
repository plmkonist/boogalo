package com.example.myapplication

import com.example.myapplication.R
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DifficultyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_difficulty)
        val hardButton = findViewById<Button>(R.id.HardButton)
        val mediumButton = findViewById<Button>(R.id.MediumButton)
        val easyButton = findViewById<Button>(R.id.EasyButton)
        val gameIntent = Intent(this, GameActivity::class.java)
        gameIntent.putExtra("song", intent.getIntExtra("song", 0))
        hardButton.setOnClickListener {
            gameIntent.putExtra("difficulty", 2)
            startActivity(gameIntent)
            finish()
        }
        mediumButton.setOnClickListener {
            gameIntent.putExtra("difficulty", 1)
            startActivity(gameIntent)
            finish()
        }
        easyButton.setOnClickListener {
            gameIntent.putExtra("difficulty", 0)
            startActivity(gameIntent)
            finish()
        }
    }
}