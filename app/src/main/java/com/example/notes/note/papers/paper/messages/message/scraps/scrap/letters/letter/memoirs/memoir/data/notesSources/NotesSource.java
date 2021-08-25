package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.notesSources;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData;

public interface NotesSource {
    int NULL_POSITION = 0;
    int size();
    NoteData getNoteData(int position);
    void addNote(NoteData note);
    void editNote(int position, NoteData note);
    void deleteNote(int position);
    void clearAllNote();
    NotesSource getFavoriteData();

    NotesSource init(NotesSourceResponse notesSourceResponse);
}
