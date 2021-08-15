package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {
    private boolean isLandScape;
    private final Fragment notesListFragment = NotesListFragment.newInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLandScape = getResources()
                .getConfiguration()
                .orientation == Configuration.ORIENTATION_LANDSCAPE;

        setContentView(R.layout.activity_main);
        initToolBar();
        showFragments(notesListFragment);
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
                Fragment contentFragment = NoteContentFragment
                        .newInstance(new NoteData(""));
                showFragments
                        (notesListFragment, contentFragment);
                break;
            case R.id.action_favorite:
                break;
            case R.id.action_settings:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    //===============================ViewWork===================================================//

    private void showFragments(Fragment leftFragment, Fragment rightFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, leftFragment)
                .addToBackStack("TAG")
                .commit();

        if (isLandScape) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_container, rightFragment)
                    .addToBackStack("TAG")
                    .commit();
        }
    }

    private void showFragments(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, NotesListFragment.newInstance())
                .addToBackStack("TAG")
                .commit();
    }

}