package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class NoteData implements Parcelable {
    private String id;
    private String title;
    private String noteContent;
    private Date dateOfCreation;
    private Date dateOfChange;
    private boolean isFavorite;

    public NoteData(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.noteContent = builder.noteContent;
        this.dateOfCreation = builder.dateOfCreation;
        this.dateOfChange = builder.dateOfChange;
        this.isFavorite = builder.isFavorite;
    }

    //==========================Builder================================//

    public static class Builder {
        private String id;
        private String title = "";
        private String noteContent = "";
        private Date dateOfCreation;
        private Date dateOfChange;
        private boolean isFavorite = false;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setNoteContent(String noteContent) {
            this.noteContent = noteContent;
            return this;
        }

        public Builder setDateOfCreation(Date dateOfCreation) {
            this.dateOfCreation = dateOfCreation;
            return this;
        }

        public Builder setDateOfChange(Date dateOfChange) {
            this.dateOfChange = dateOfChange;
            return this;
        }

        public Builder setIsFavorite(boolean isFavorite) {
            this.isFavorite = isFavorite;
            return this;
        }

        public NoteData build() {
            return new NoteData(this);
        }
    }

    //=================Getters and Setters=======================//

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    //=================Parcelable implementation=======================//

    protected NoteData(Parcel in) {
        id = in.readString();
        title = in.readString();
        noteContent = in.readString();
        isFavorite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(noteContent);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
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
}
