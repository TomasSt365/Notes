package com.github.tomasSt365.notes

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.github.tomasSt365.notes.data.NoteData
import com.github.tomasSt365.notes.observer.Publisher
import com.github.tomasSt365.notes.ui.Navigation
import com.github.tomasSt365.notes.ui.main.NoteContentFragment
import com.github.tomasSt365.notes.ui.main.NotesListFragment

class MainActivity : AppCompatActivity(), FragmentManager.OnBackStackChangedListener {
    private var currentNote: NoteData? = null
    var publisher: Publisher? = null
        private set
    private var navigation: Navigation? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.addOnBackStackChangedListener(this)
        initFields()
        initToolBar()
        showContent(currentNote, false)
    }

    /**Content Work*/
    companion object {
        var isLandScape = false
            private set
    }

    private fun initFields() {
        isLandScape = resources
            .configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        currentNote = NotesListFragment.getCurrentNote()
        publisher = Publisher()
        navigation = Navigation(
            supportFragmentManager
        )
    }

    private fun showContent(currentNote: NoteData?, isFavoriteList: Boolean) {
        val notesListFragment: Fragment = NotesListFragment.newInstance(isFavoriteList)
        val noteContentFragment: Fragment = NoteContentFragment.newInstance(currentNote)
        navigation!!.addFragment(
            R.id.main_container, notesListFragment,
            addToBackStack = false,
            popBackStackBeforeAdd = true
        )
        if (isLandScape) {
            navigation!!.addFragment(R.id.content_container, noteContentFragment, false)
        }
    }

    /**TollBarWork*/
    private fun initToolBar() {
        val toolbar = findViewById<Toolbar>(R.id.toolBar)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_main -> showContent(currentNote, false)
            R.id.action_favorite -> showContent(currentNote, true)
            //R.id.action_settings -> navigation.addFragment(R.id.main_container, SettingsFragment.newInstance(),true)
        }
        return super.onOptionsItemSelected(item)
    }

    /**BackButtonWork*/
    override fun onBackStackChanged() {
     supportActionBar!!.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}