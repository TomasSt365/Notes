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
        init();
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
            if(this.getNoteData(i).isFavorite() == NoteData.TRUE){
                favoriteData.add(this.getNoteData(i));
            }
        }
        return new NoteSourceImpl(favoriteData);
    }

    private NoteSourceImpl init() {
        String[] noteNames = resources.getStringArray(R.array.noteNames);
        String[] noteContent = resources.getStringArray(R.array.noteContent);
        for (int i = 0; i < noteNames.length; i++) {
            dataSource.add(new NoteData(noteNames[i], noteContent[i]));
        }
        return this;
    }
}
