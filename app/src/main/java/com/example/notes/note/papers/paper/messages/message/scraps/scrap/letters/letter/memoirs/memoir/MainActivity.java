package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.Settings.SettingsFragment;

public class MainActivity extends AppCompatActivity {
    private final Fragment notesListFragment = NotesListFragment.newInstance();

    private static boolean isLandScape;
    private NoteData currentNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandScape = getResources()
                .getConfiguration()
                .orientation == Configuration.ORIENTATION_LANDSCAPE;
        currentNote = NotesListFragment.getCurrentNote();

        setContentView(R.layout.activity_main);
        initToolBar();
        showContent(currentNote);
    }

    private void showContent(NoteData currentNote) {
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
                showContent(currentNote);
                break;
            /*case R.id.action_favorite:
                break;*/
            case R.id.action_settings:
                onSettingsClick();
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