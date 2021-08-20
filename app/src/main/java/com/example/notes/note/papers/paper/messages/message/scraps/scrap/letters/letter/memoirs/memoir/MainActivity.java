package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.observer.Publisher;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.Settings.SettingsFragment;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.main.NoteContentFragment;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.main.NotesListFragment;

public class MainActivity extends AppCompatActivity {
    private static boolean isLandScape;
    private NoteData currentNote;
    private Publisher publisher;

    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initField();
        initToolBar();
        showContent(currentNote, false);
    }

    private void initField() {
        isLandScape = getResources()
                .getConfiguration()
                .orientation == Configuration.ORIENTATION_LANDSCAPE;
        currentNote = NotesListFragment.getCurrentNote();
        publisher = new Publisher();
    }

    private void showContent(NoteData currentNote, boolean isFavoriteList) {
        Fragment notesListFragment = NotesListFragment.newInstance(isFavoriteList);

        getSupportFragmentManager()
                .popBackStack();

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, notesListFragment)
                .commit();
        if (isLandScape()) {
            getSupportFragmentManager()
                    .popBackStack();

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_container, NoteContentFragment.newInstance(currentNote))
                    .commit();
        }
    }

    //==========================TollBarWork============================================//

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main:
                showContent(currentNote,false);
                break;
            case R.id.action_favorite:
                showContent(currentNote,true);
                break;
            case R.id.action_settings:
                //onSettingsClick();//некоректная работа из-за жёсткой разметки макета в LandScape
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onSettingsClick() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack("")
                .replace(R.id.main_container, SettingsFragment.newInstance())
                .commit();
    }

    //============================================================================//

    public static boolean isLandScape() {
        return isLandScape;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}