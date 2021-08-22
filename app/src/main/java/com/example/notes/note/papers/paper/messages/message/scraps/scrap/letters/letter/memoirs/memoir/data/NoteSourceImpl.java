package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data;

import android.content.res.Resources;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.R;

import java.util.ArrayList;
import java.util.List;

public class NoteSourceImpl implements NoteSource {
    private List<NoteData> dataSource;
    private Resources resources;

    public NoteSourceImpl(Resources resources) {
        dataSource = new ArrayList<>();
        this.resources = resources;
    }

    public NoteSourceImpl(List<NoteData> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public NoteData getNoteData(int position) {
        return dataSource.get(position);
    }

    @Override
    public void addNote(NoteData data) {
        dataSource.add(0, data);
    }

    @Override
    public void editNote(int position, NoteData data) {
        dataSource.set(position, data);
    }

    @Override
    public void deleteNote(int position) {
        dataSource.remove(position);
    }

    @Override
    public void clearAllNote() {
        dataSource.clear();
    }

    @Override
    public NoteSource getFavoriteData() {
        List<NoteData> favoriteData = new ArrayList<>();
        for (int i = 0; i < dataSource.size(); i++){
            if(this.getNoteData(i).isFavorite()){
                favoriteData.add(this.getNoteData(i));
            }
        }
        return new NoteSourceImpl(favoriteData);
    }

    public NoteSourceImpl init() {
        String[] noteTitles = resources.getStringArray(R.array.noteTitles);
        String[] notesContent = resources.getStringArray(R.array.noteContent);
        //boolean[] notesIsFavorite = resources.getBoolean(R.array.);
        for (int i = 0; i < noteTitles.length; i++) {
            NoteData.Builder noteBuilder = new NoteData.Builder()
                    .setNoteContent(notesContent[i])
                    .setTitle(noteTitles[i]);
                    //.setIsFavorite();

            dataSource.add(noteBuilder.build());
        }
        //TODO: FavoriteList пока не может работать, нет знаний по базе данных
        return this;
    }
}
