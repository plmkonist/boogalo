package com.example.myapplication

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.util.*


class GameActivity : AppCompatActivity() {
    private val ROW_SIZE = 4
    private val  COLUMN_SIZE = 4
    private var score = 0
    private var gameStartTime: Long = 0
    private var noteTimes: ArrayList<Long>? = null
    private val REVEAL_TIME = 1500L
    private var song2: MediaPlayer? = null
    //private boolean[] correctButtonClicked;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        val time = System.currentTimeMillis()
        val song = SongConstants.songArray[intent.getIntExtra("song",0)]
        noteTimes = song.noteTimes
        setupUI(song)
    }

    private fun setupUI(song: Song) {
        val gameRowsAndColumns = findViewById<LinearLayout>(R.id.rowsAndColumns)
        val scoreText = findViewById<TextView>(R.id.sdf)
        scoreText.text = "Current song " + song.name
        val difficultyConst = intent.getIntExtra("difficulty", 1)
        val buttonArray: Array<ImageButton?> = arrayOfNulls(ROW_SIZE * COLUMN_SIZE)
        val buttonToClick: Array<ScoreTracker?> = setUpButtonToClick(difficultyConst)
        for (i in 0 until COLUMN_SIZE) {
            val gameBtnRow: View = layoutInflater.inflate(
                R.layout.game_row_chunk,
                gameRowsAndColumns, false
            )
            for (j in 0 until ROW_SIZE) {
                val gameRow = gameBtnRow.findViewById<LinearLayout>(R.id.gameRow)
                val gameBtns: View = layoutInflater.inflate(
                    R.layout.game_btn_chunk,
                    gameRow, false
                )
                val gameButton = gameBtns.findViewById<ImageButton>(R.id.gameButton)
                buttonArray[i * ROW_SIZE + j] = gameButton
                //revealArray[(i * ROW_SIZE) + j] = new ButtonFadeThread(gameButton);
                gameButton.setOnClickListener {
                    for (i in buttonToClick.indices) {
                        if (gameButton == buttonToClick[i]?.button) {
                            score++
                            scoreText.text = "score: $score"
                            gameButton.visibility = View.INVISIBLE
                            buttonToClick[i]?.clicked()
                        }
                    }
                }
                gameRow.addView(gameBtns)
            }
            gameRowsAndColumns.addView(gameBtnRow)
        }
        GlobalScope.launch {
            startGame(buttonArray, buttonToClick,song)
        }
    }

    private fun setUpButtonToClick(difficultyConstant: Int): Array<ScoreTracker?> {
        return when (difficultyConstant) {
            0 -> {
                arrayOfNulls<ScoreTracker?>(1)
            }
            1 -> {
                arrayOfNulls(2)
            }
            else -> {
                arrayOfNulls(3)
            }
        }
    }

    fun startGame(buttonArray: Array<ImageButton?>, buttonToClick: Array<ScoreTracker?>, song: Song) {
        var gameIsRunning = true
        var gen = Random()
        val randomButtonLocations = IntArray(buttonToClick.size)
        var x = gen.nextInt(ROW_SIZE * COLUMN_SIZE)
        randomButtonLocations[0] = x
        var index = 0
        var i = 1
        while (i < randomButtonLocations.size) {
            val y = gen.nextInt(ROW_SIZE * COLUMN_SIZE)
            if (y == x) {
                continue
            }
            x = y
            randomButtonLocations[i] = x
            i++
        }
        runOnUiThread {
            song2 = MediaPlayer.create(this@GameActivity, song.iD)
            song2?.start()
        }
        gameStartTime = System.currentTimeMillis()
        while (gameIsRunning) {
            runBlocking {
                for (g in buttonToClick.indices) {
                    if (buttonToClick[g]!!.clicked) {
                        continue
                    }
                    launch(Dispatchers.Default) {
                        val coIndex = g
                            if (buttonToClick[coIndex]!!.clicked || buttonToClick[i]!!.time + REVEAL_TIME <= System.currentTimeMillis()) {
                                x = genInt(gen, randomButtonLocations);
                                randomButtonLocations[index] = x;
                                //x = gen.nextInt(ROW_SIZE * COLUMN_SIZE)
                                gen = Random(x.toLong())
                                buttonToClick[coIndex]?.reset(buttonArray[x], gameStartTime + noteTimes!![index++])
                                if (System.currentTimeMillis() <= buttonToClick[coIndex]!!.time) {
                                    launch {
                                        try {
                                            Thread.sleep(
                                                buttonToClick[g]!!.time - System.currentTimeMillis()
                                            )
                                            buttonClickedCheck(buttonToClick, coIndex)
                                        } catch (e: java.lang.Exception) {
                                            buttonClickedCheck(buttonToClick, coIndex)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            if (index == noteTimes!!.size) {
                gameIsRunning = false
            }

        }
        runOnUiThread { endGame() }
    }

    private fun buttonClickedCheck(buttonToClick: Array<ScoreTracker?>, i: Int) {
        val score = Thread(Runnable {
            runOnUiThread { buttonToClick[i]!!.button!!.visibility = View.VISIBLE }
            val startTime = System.currentTimeMillis()
            while (System.currentTimeMillis() <= startTime + REVEAL_TIME) {
                if (buttonToClick[i]!!.clicked) {
                    break
                }
            }
            runOnUiThread { buttonToClick[i]!!.button!!.visibility = View.INVISIBLE }
        })
        score.start()
    }

    private fun endGame() {
        val gameOver = Intent(this, GameOver::class.java)
        gameOver.putExtra("finalScore", score)
        startActivity(gameOver)
        finish()
    }

    private fun genInt(gen: Random, arr: IntArray): Int {
        val x = gen.nextInt(ROW_SIZE * COLUMN_SIZE)
        for (i in arr.indices) {
            if (x == arr[i]) {
                return genInt(gen, arr)
            }
        }
        return x
    }

    override fun onDestroy() {
        song2?.stop()
        super.onDestroy()
    }
}