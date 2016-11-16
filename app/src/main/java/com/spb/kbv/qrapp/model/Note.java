package com.spb.kbv.qrapp.model;

import android.util.Patterns;

import com.orm.SugarRecord;

public class Note extends SugarRecord {
    private String noteName;
    private String statusCode;
    private boolean isUrl;

    public Note(){}

    public Note(String noteName) {
        this.noteName = noteName;
        this.isUrl = Patterns.WEB_URL.matcher(noteName).matches();
    }

    public Note(String noteName, boolean isUrl) {
        this.noteName = noteName;
        this.isUrl = isUrl;
    }

    public String getNoteName() {
        return noteName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isUrl() {
        return isUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (o.getClass() == Note.class) {
            Note note = (Note) o;
            return getNoteName().equals(note.getNoteName());
        } else return false;
    }

    @Override
    public int hashCode() {
        return noteName.hashCode();
    }
}
