package com.github.tomasSt365.notes.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.ContextMenu.ContextMenuInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.tomasSt365.notes.Const
import com.github.tomasSt365.notes.MainActivity
import com.github.tomasSt365.notes.R
import com.github.tomasSt365.notes.data.NoteData
import com.github.tomasSt365.notes.data.notesSources.NotesSource
import com.github.tomasSt365.notes.data.notesSources.NotesSourceRemoteImpl
import com.github.tomasSt365.notes.data.notesSources.NotesSourceResponse
import com.github.tomasSt365.notes.observer.Observer
import com.github.tomasSt365.notes.observer.Publisher
import com.github.tomasSt365.notes.ui.Navigation
import com.github.tomasSt365.notes.ui.edit.EditFragment
import com.github.tomasSt365.notes.ui.main.noteListAdapter.NoteListAdapter
import com.github.tomasSt365.notes.ui.main.noteListAdapter.OnRecyclerViewClickListener
import com.github.tomasSt365.notes.ui.main.removeDialog.OnRemoveDialogClickListener
import com.github.tomasSt365.notes.ui.main.removeDialog.RemoveDialogFragment

class NotesListFragment : Fragment(),
    OnRemoveDialogClickListener {

    private var noteListAdapter: NoteListAdapter? = null
    private var recyclerView: RecyclerView? = null
    private var publisher: Publisher? = null
    private var navigation: Navigation? = null
    private lateinit var data: NotesSource

    override fun onAttach(context: Context) {
        super.onAttach(context)
        publisher = (context as MainActivity).publisher
        navigation = Navigation(requireActivity().supportFragmentManager)
    }

    override fun onDetach() {
        super.onDetach()
        publisher = null
        navigation = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_notes_list, container, false)
        data = NotesSourceRemoteImpl().init(object : NotesSourceResponse {
            @SuppressLint("NotifyDataSetChanged")
            override fun initialized(notesSource: NotesSource?) {
                noteListAdapter!!.notifyDataSetChanged()
            }
        })
        initNotesList(view)
        noteListAdapter!!.setDataSource(data)
        return view
    }

    /**ContentWork*/
    private fun initNotesList(view: View) {
        val recyclerView: RecyclerView = view.findViewById(R.id.listRecyclerView)
        val layoutManager = LinearLayoutManager(context)
        noteListAdapter = NoteListAdapter(this)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = noteListAdapter

        noteListAdapter!!.setOnClickListener(object : OnRecyclerViewClickListener {
            @SuppressLint("NonConstantResourceId", "NotifyDataSetChanged")
            override fun onRecyclerViewClick(view: View, position: Int) {
                when (view.id) {
                    R.id.item_card_view -> {
                        val currentNoteTitle = data.getNoteData(position)!!.title
                        val currentNoteContent = data.getNoteData(position)!!.noteContent
                        val currentNoteIsFavorite = data.getNoteData(position)!!.isFavorite
                        val noteBuilder = NoteData.Builder()
                            .setTitle(currentNoteTitle)
                            .setNoteContent(currentNoteContent)
                            .setIsFavorite(currentNoteIsFavorite)
                        currentNote = noteBuilder.build()
                        showContent()
                    }
                    R.id.favoriteButton -> {
                        data.getNoteData(position)!!.isFavorite =
                            !data.getNoteData(position)!!.isFavorite
                        data.editNote(position, data.getNoteData(position))
                    }
                }
            }
        })
    }

    private fun showContent() {
        if (MainActivity.isLandScape) {
            navigation!!.addFragment(
                R.id.content_container,
                NoteContentFragment.newInstance(currentNote), false
            )
        } else {
            navigation!!.addFragment(
                R.id.main_container,
                NoteContentFragment.newInstance(currentNote), true
            )
        }
    }

    /**MainMenuWork*/
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.notes_list_fragment_menu, menu)
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionAdd -> onActionAddClick()
            R.id.actionClearAll -> showRemoveDialogFragment(Const.ALL_NOTES_POSITION)
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onActionAddClick() {
        navigation!!.addFragment(R.id.main_container, EditFragment.newInstance(), true)
        publisher!!.subscribe(object : Observer {
            override fun updateState(note: NoteData?) {
                if (isFavoriteList) {
                    note!!.isFavorite = true
                }
                data.addNote(note)
                noteListAdapter!!.notifyDataSetChanged()
                recyclerView?.smoothScrollToPosition(Const.NULL_POSITION)
            }
        })
    }

    /**ContextMenuWork*/
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        requireActivity().menuInflater.inflate(R.menu.context_recycler_view, menu)
    }

    @SuppressLint("NonConstantResourceId", "NotifyDataSetChanged")
    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = noteListAdapter!!.clickContextPosition
        when (item.itemId) {
            R.id.action_edit -> onActionEditClick(position)
            R.id.action_delete -> showRemoveDialogFragment(position)
        }
        return super.onContextItemSelected(item)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onActionEditClick(position: Int) {
        navigation!!.addFragment(
            R.id.main_container,
            EditFragment.newInstance(data.getNoteData(position)), true
        )
        publisher!!.subscribe(object : Observer {
            override fun updateState(note: NoteData?) {
                data.editNote(position, note)
                noteListAdapter!!.notifyDataSetChanged()
                recyclerView?.smoothScrollToPosition(Const.NULL_POSITION)
            }
        })
    }

    /**RemoveDialogWork*/
    private fun showRemoveDialogFragment(position: Int) {
        positionToRemote = position
        noteToDelete = if (position == Const.ALL_NOTES_POSITION) {
            Const.ALL_NOTES
        } else {
            data.getNoteData(position)
        }
        val fragment = RemoveDialogFragment.newInstance()
        fragment.setOnDialogClickListener(this)
        val fragmentManager = requireActivity().supportFragmentManager
        fragment.show(fragmentManager, "")
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onPositiveButtonClick(position: Int) {
        if (position == Const.ALL_NOTES_POSITION) {
            data.clearAllNote()
        } else {
            data.deleteNote(position)
        }
        noteListAdapter!!.notifyDataSetChanged()
    }

    override fun onNegativeButtonClick() {}

    companion object {
        private var currentNote: NoteData? = null
        private var isFavoriteList = false
        private var positionToRemote = -1
        private var noteToDelete: NoteData? = null

        fun getCurrentNote(): NoteData? {
            return if (currentNote != null) {
                currentNote
            } else {
                NoteData.Builder().build()
            }
        }

        fun getPositionToRemote() = positionToRemote

        fun getNoteToDelete() = noteToDelete

        fun newInstance(isFavoriteList: Boolean): NotesListFragment {
            Companion.isFavoriteList = isFavoriteList
            return NotesListFragment()
        }
    }
}