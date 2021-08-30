package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData
import java.util.*

object NoteDataConvertor {
    fun documentToNoteData(id: String?, doc: Map<String?, Any?>): NoteData {
        val noteBuilder = NoteData.Builder()
            .setId(id)
            .setTitle(doc[Fields.TITLE] as String?)
            .setNoteContent(doc[Fields.NOTE_CONTENT] as String?)
            .setIsFavorite(doc[Fields.IS_FAVORITE] as Boolean)
           //.setDateOfCreation(doc[Fields.DATE_OF_CREATION] as Date)
            //.setDateOfCreation(doc[Fields.DATE_OF_EDIT] as Date)

        return noteBuilder.build()
    }

    fun noteDataToDocument(note: NoteData?): Map<String, Any?> {
        val doc: MutableMap<String, Any?> = HashMap()
        if(note!=null) {
            doc[Fields.TITLE] = note.title
            doc[Fields.NOTE_CONTENT] = note.noteContent
            doc[Fields.DATE_OF_CREATION] = note.dateOfCreation
            doc[Fields.DATE_OF_EDIT] = note.dateOfEdit
            doc[Fields.IS_FAVORITE] = note.isFavorite
        }
        return doc
    }

    object Fields {
        const val TITLE = "title"
        const val NOTE_CONTENT = "note content"
        const val DATE_OF_CREATION = "date of creation"
        const val DATE_OF_EDIT = "date of edit"
        const val IS_FAVORITE = "is favorite"
    }
}