package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.notesSources

import android.content.res.Resources
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.Const
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.R
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData
import java.util.*

class NotesSourceLocalImpl(
    private var resources: Resources?) : NotesSource {
    private var notes: MutableList<NoteData?> = ArrayList()

    override fun size(): Int {
        return notes.size
    }

    override fun getNoteData(position: Int): NoteData? {
        return notes[position]
    }

    override fun addNote(note: NoteData?) {
        notes.add(Const.NULL_POSITION, note)
    }

    override fun editNote(position: Int, note: NoteData?) {
        notes[position] = note
    }

    override fun deleteNote(position: Int) {
        notes.removeAt(position)
    }

    override fun clearAllNote() {
        notes.clear()
    }

    override fun init(notesSourceResponse: NotesSourceResponse?): NotesSource {
        val noteTitles = resources!!.getStringArray(R.array.noteTitles)
        val notesContent = resources!!.getStringArray(R.array.noteContent)
        for (i in noteTitles.indices) {
            val noteBuilder = NoteData.Builder()
                .setNoteContent(notesContent[i])
                .setTitle(noteTitles[i])
            notes.add(noteBuilder.build())
        }
        notesSourceResponse?.initialized(this)
        return this
    }

    private constructor(resources: Resources?, notes: MutableList<NoteData?>) : this(resources = resources){
        this.notes = notes
    }

    override fun copy(): NotesSource {
        return NotesSourceLocalImpl(resources, notes)
    }
}