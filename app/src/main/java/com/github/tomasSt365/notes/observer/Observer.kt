package com.github.tomasSt365.notes.observer

import com.github.tomasSt365.notes.data.NoteData

interface Observer {
    fun updateState(note: NoteData?)
}