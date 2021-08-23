package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NotesSourceRemoteImpl implements NotesSource {

    private static final String NOTES_COLLECTION = "NOTES_COLLECTION";

    private final FirebaseFirestore store = FirebaseFirestore.getInstance();
    private final CollectionReference collectionReference = store.collection(NOTES_COLLECTION);
    private List<NoteData> notes = new ArrayList<NoteData>();

    public NotesSourceRemoteImpl() {
    }

    public NotesSourceRemoteImpl(List<NoteData> notes) {
        this.notes = notes;
    }

    @Override
    public int size() {
        return notes.size();
    }

    @Override
    public NoteData getNoteData(int position) {
        return notes.get(position);
    }

    @Override
    public void addNote(NoteData note) {
        collectionReference
                .add(NoteDataConvertor.noteDataToDocument(note));
        notes.add(note);
    }

    @Override
    public void editNote(int position, NoteData note) {
        collectionReference
                .document(notes.get(position).getId())
                .update(NoteDataConvertor.noteDataToDocument(note));
        notes.set(position, note);
    }

    @Override
    public void deleteNote(int position) {
        collectionReference
                .document(notes.get(position).getId())
                .delete();
        notes.remove(position);
    }

    @Override
    public void clearAllNote() {
        for (NoteData note : notes) {
            collectionReference
                    .document(note.getId())
                    .delete();
        }
        notes.clear();
    }

    @Override
    public NotesSource getFavoriteData() {
        List<NoteData> favoriteData = new ArrayList<>();
        for (int i = 0; i < notes.size(); i++) {
            if (this.getNoteData(i).isFavorite()) {
                favoriteData.add(this.getNoteData(i));
            }
        }
        return new NotesSourceRemoteImpl(favoriteData);
    }

    @Override
    public NotesSource init(NotesSourceResponse notesSourceResponse) {
        collectionReference.orderBy(NoteDataConvertor.Fields.DATE_OF_EDIT, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            notes = new ArrayList<NoteData>();
                            for (QueryDocumentSnapshot docFields : task.getResult()) {
                                NoteData note = NoteDataConvertor
                                        .documentToNoteData(docFields.getId(), docFields.getData());
                                notes.add(note);
                            }
                            notesSourceResponse.initialized(NotesSourceRemoteImpl.this);
                        }
                    }
                });

        return this;
    }

}
