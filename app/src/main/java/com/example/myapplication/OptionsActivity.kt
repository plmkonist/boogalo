package com.example.myapplication

import com.example.myapplication.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)
        var songSelected = findViewById<TextView>(R.id.songTitle)
        songSelected.text = SongConstants.songArray[intent.getIntExtra("song",0)].name
        val songList = findViewById<LinearLayout>(R.id.test)
        val songArr: Array<Song> = SongConstants.songArray
        val back = Intent(this, DifficultyActivity::class.java)
        for (i in songArr.indices) {
            val songsLayout: View = layoutInflater.inflate(
                R.layout.chunk_song,
                songList, false
            )
            val songSelectBTN =
                songsLayout.findViewById<Button>(R.id.chooseSongButton)
            songSelectBTN.setText(songArr[i].name)
            songSelectBTN.setOnClickListener {
                back.removeExtra("song")
                back.putExtra("song", i)
                songSelected.setText(songArr[i].name)
            }
            songList.addView(songsLayout)
        }
        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener { startActivity(back) }
    }
}