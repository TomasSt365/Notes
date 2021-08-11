package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteData implements Parcelable {
    private String name;
    private String noteText;
    private String dateOfCreation;
    private String dateOfChange;

    public NoteData(String name, String noteText) {
        this.name = name;
        this.noteText = noteText;
    }

    public NoteData(String name) {
        this.name = name;
    }


    protected NoteData(Parcel in) {
        name = in.readString();
        noteText = in.readString();
        dateOfCreation = in.readString();
        dateOfChange = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(noteText);
        dest.writeString(dateOfCreation);
        dest.writeString(dateOfChange);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<NoteData> CREATOR = new Creator<NoteData>() {
        @Override
        public NoteData createFromParcel(Parcel in) {
            return new NoteData(in);
        }

        @Override
        public NoteData[] newArray(int size) {
            return new NoteData[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    public String getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(String dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(String dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

}
