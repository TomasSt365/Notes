package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.data;

import android.os.Parcel;
import android.os.Parcelable;

public class NoteData implements Parcelable {
    public static final byte TRUE = 1;
    public static final byte FALSE = 0;

    private String id;
    private String title;
    private String noteContent;
    private String dateOfCreation;
    private String dateOfChange;
    private byte isFavorite;

    public NoteData(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.noteContent = builder.noteContent;
        this.dateOfCreation = builder.dateOfCreation;
        this.dateOfChange = builder.dateOfChange;
        this.isFavorite = builder.isFavorite;
    }

    //==========================Builder================================//

    public static class Builder{
        private String id;
        private String title = "";
        private String noteContent = "";
        private String dateOfCreation;
        private String dateOfChange;
        private byte isFavorite = FALSE;

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

        public Builder setDateOfCreation(String dateOfCreation) {
            this.dateOfCreation = dateOfCreation;
            return this;
        }

        public Builder setDateOfChange(String dateOfChange) {
            this.dateOfChange = dateOfChange;
            return this;
        }

        public Builder setIsFavorite(byte isFavorite) {
            this.isFavorite = isFavorite;
            return this;
        }

        public NoteData build(){
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

    public byte isFavorite() {
        return isFavorite;
    }

    public void setFavorite(byte favorite) {
        isFavorite = favorite;
    }

    //=================Parcelable implementation=======================//

    protected NoteData(Parcel in) {
        id = in.readString();
        title = in.readString();
        noteContent = in.readString();
        dateOfCreation = in.readString();
        dateOfChange = in.readString();
        isFavorite = in.readByte();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(noteContent);
        dest.writeString(dateOfCreation);
        dest.writeString(dateOfChange);
        dest.writeByte(isFavorite);
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

    @Override
    public int describeContents() {
        return 0;
    }

}
