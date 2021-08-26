package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data.NoteData;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.observer.Publisher;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.Navigation;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.main.NoteContentFragment;
import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui.main.NotesListFragment;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {
    private static boolean isLandScape;
    private NoteData currentNote;
    private Publisher publisher;
    Navigation navigation;

    public Publisher getPublisher() {
        return publisher;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().addOnBackStackChangedListener(this);

        initFields();
        initToolBar();
        showContent(currentNote, false);
    }

    public static boolean isLandScape() {
        return isLandScape;
    }

    private void initFields() {
        isLandScape = getResources()
                .getConfiguration()
                .orientation == Configuration.ORIENTATION_LANDSCAPE;
        currentNote = NotesListFragment.getCurrentNote();
        publisher = new Publisher();
        navigation = new Navigation(getSupportFragmentManager());
    }

    private void showContent(NoteData currentNote, boolean isFavoriteList) {
        Fragment notesListFragment = NotesListFragment.newInstance(isFavoriteList);
        Fragment noteContentFragment = NoteContentFragment.newInstance(currentNote);

        navigation.addFragment(R.id.main_container, notesListFragment,false,true);
        if (isLandScape()) {
            navigation.addFragment(R.id.content_container, noteContentFragment, false);
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
                //Navigation.addFragment(R.id.main_container, SettingsFragment.newInstance(), true);
                // некоректная работа из-за жёсткой разметки макета в LandScape
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //============================================================================//

    @Override
    public void onBackStackChanged() {
        Objects.requireNonNull(getSupportActionBar())
                .setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount() > 0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}