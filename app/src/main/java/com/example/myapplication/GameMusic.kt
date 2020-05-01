package com.example.myapplication

import com.example.boogaloopart2.Song
import com.example.boogaloopart2.SongConstants

class GameMusic {
    internal constructor(s: Song) {
        song = s
    }

    internal constructor() {
        song = SongConstants.DEFAULT
    }

    companion object {
        var song: Song = SongConstants.DEFAULT

    }
}