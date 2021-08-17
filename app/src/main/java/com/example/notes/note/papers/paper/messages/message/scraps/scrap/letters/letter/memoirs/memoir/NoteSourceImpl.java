package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class NoteSourceImpl implements NoteSource {
    private List<NoteData> dataSource;
    private Resources resources;

    public NoteSourceImpl(Resources resources) {
        dataSource = new ArrayList<>();
        this.resources = resources;
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public NoteData getNoteData(int position) {
        return dataSource.get(position);
    }

    public NoteSourceImpl init() {
        String[] noteNames = resources.getStringArray(R.array.noteNames);
        String[] noteContent = resources.getStringArray(R.array.noteContent);
        for (int i = 0; i < noteNames.length; i++) {
            dataSource.add(new NoteData(noteNames[i], noteContent[i]));
        }
        return this;
    }
}
