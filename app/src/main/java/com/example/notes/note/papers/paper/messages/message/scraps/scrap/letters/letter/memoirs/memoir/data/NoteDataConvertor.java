package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data;

import com.google.firebase.Timestamp;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NoteDataConvertor {
    public static class Fields {
        public final static String TITLE = "title";
        public final static String NOTE_CONTENT = "note content";
        public final static String DATE_OF_CREATION = "date of creation";
        public final static String DATE_OF_EDIT = "date of edit";
        public final static String IS_FAVORITE = "is favorite";

    }

    public static NoteData documentToNoteData(String id, Map<String, Object> doc) {
        NoteData.Builder noteBuilder = new NoteData
                .Builder()
                .setId(id)
                .setTitle((String) doc.get(Fields.TITLE))
                .setNoteContent((String) doc.get(Fields.NOTE_CONTENT))
                .setIsFavorite((boolean) doc.get(Fields.IS_FAVORITE));
                //.setDateOfCreation(((Timestamp) (doc.get(Fields.DATE_OF_CREATION))).toDate())
                //.setDateOfEdit(((Timestamp) (doc.get(Fields.DATE_OF_EDIT))).toDate());

        return noteBuilder.build();
    }

     public static Map<String, Object> noteDataToDocument( NoteData note) {
        Map<String, Object> doc = new HashMap<>();
        doc.put(Fields.TITLE, note.getTitle());
        doc.put(Fields.NOTE_CONTENT, note.getNoteContent());
        doc.put(Fields.DATE_OF_CREATION, note.getDateOfCreation());
        doc.put(Fields.DATE_OF_EDIT, note.getDateOfEdit());
        doc.put(Fields.IS_FAVORITE, note.isFavorite());

        return doc;
    }
}
