package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.notesSources

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.Const
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteDataConvertor
import com.google.firebase.firestore.*
import java.util.*
import kotlin.collections.ArrayList


class NotesSourceRemoteImpl: NotesSource {
    private var notes: MutableList<NoteData> = ArrayList()
    private val store = FirebaseFirestore.getInstance()
    private val collectionReference = store.collection(NOTES_COLLECTION)

    override fun size(): Int {
        return notes.size
    }

    override fun getNoteData(position: Int): NoteData {
        return notes[position]
    }

    override fun addNote(note: NoteData?) {
        collectionReference
            .add(NoteDataConvertor.noteDataToDocument(note))
        notes.add(Const.NULL_POSITION, note!!)
    }

    override fun editNote(position: Int, note: NoteData?) {
        collectionReference
            .document(notes[position].id as String)
            .update(NoteDataConvertor.noteDataToDocument(note))
        notes[position] = note!!
    }

    override fun deleteNote(position: Int) {
        collectionReference
            .document(notes[position].id as String)
            .delete()
        notes.removeAt(position)
    }

    override fun clearAllNote() {
        for (note in notes) {
            collectionReference
                .document(note.id as String)
                .delete()
        }
        notes.clear()
    }

    override fun init(notesSourceResponse: NotesSourceResponse?): NotesSource {
        collectionReference.orderBy(
            NoteDataConvertor.Fields.DATE_OF_EDIT,
            Query.Direction.DESCENDING
        )
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    notes = ArrayList()
                    for (docFields in task.result!!) {
                        val note =
                            NoteDataConvertor.documentToNoteData(docFields.id, docFields.data)
                        notes.add(note)
                    }
                    notesSourceResponse!!.initialized(this@NotesSourceRemoteImpl)
                }
            }
        return this
    }

    companion object {
        private const val NOTES_COLLECTION = "NOTES_COLLECTION"
    }
}