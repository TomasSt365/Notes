package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.notesSources;

import android.content.res.Resources;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.R;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData;

import java.util.ArrayList;
import java.util.List;

public class NotesSourceLocalImpl implements NotesSource {
    private List<NoteData> notes;
    private Resources resources;

    public NotesSourceLocalImpl(Resources resources) {
        notes = new ArrayList<>();
        this.resources = resources;
    }

    public NotesSourceLocalImpl(List<NoteData> notes) {
        this.notes = notes;
    }

    @Override
    public int size() {
        return notes.size();
    }

    @Override
    public NoteData getNoteData(int position) {
        return notes.get(position);
    }

    @Override
    public void addNote(NoteData note) {
        notes.add(NULL_POSITION, note);
    }

    @Override
    public void editNote(int position, NoteData note) {
        notes.set(position, note);
    }

    @Override
    public void deleteNote(int position) {
        notes.remove(position);
    }

    @Override
    public void clearAllNote() {
        notes.clear();
    }

    @Override
    public NotesSource getFavoriteData() {
        List<NoteData> favoriteData = new ArrayList<>();
        for (int i = 0; i < notes.size(); i++) {
            if (this.getNoteData(i).isFavorite()) {
                favoriteData.add(this.getNoteData(i));
            }
        }
        return new NotesSourceLocalImpl(favoriteData);
    }

    @Override
    public NotesSource init(NotesSourceResponse notesSourceResponse) {
        String[] noteTitles = resources.getStringArray(R.array.noteTitles);
        String[] notesContent = resources.getStringArray(R.array.noteContent);
        //boolean[] notesIsFavorite = resources.getBoolean(R.array.);
        for (int i = 0; i < noteTitles.length; i++) {
            NoteData.Builder noteBuilder = new NoteData.Builder()
                    .setNoteContent(notesContent[i])
                    .setTitle(noteTitles[i]);
            //.setIsFavorite();

            notes.add(noteBuilder.build());
        }
        //TODO: FavoriteList пока не может работать, нет знаний по базе данных

        if (notesSourceResponse != null) {
            notesSourceResponse.initialized(this);
        }

        return this;
    }

}
