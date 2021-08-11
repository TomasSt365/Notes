package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notesList_container, NotesListFragment.newInstance())
                .commit();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.noteContent_container, NoteContentFragment.newInstance(new NoteData("", "")))
                    .commit();
        }
    }

}