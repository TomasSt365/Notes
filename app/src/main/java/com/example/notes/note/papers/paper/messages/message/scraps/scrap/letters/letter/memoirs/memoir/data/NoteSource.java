package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data;

public interface NoteSource {
    int size();
    NoteData getNoteData(int position);
    void addNote(NoteData data);
    void editNote(int position, NoteData data);
    void deleteNote(int position);
    void clearAllNote();
    NoteSource getFavoriteData();
}
