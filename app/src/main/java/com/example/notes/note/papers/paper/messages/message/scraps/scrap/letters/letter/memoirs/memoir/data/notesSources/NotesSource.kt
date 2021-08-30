package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.notesSources

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData

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