package com.github.tomasSt365.notes.data.notesSources

import com.github.tomasSt365.notes.data.NoteData

interface NotesSource {
    fun size(): Int
    fun getNoteData(position: Int): NoteData?
    fun addNote(note: NoteData?)
    fun editNote(position: Int, note: NoteData?)
    fun deleteNote(position: Int)
    fun clearAllNote()

    fun init(notesSourceResponse: NotesSourceResponse?): NotesSource
    fun copy(): NotesSource
}