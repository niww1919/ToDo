package com.example.todo.realm;

import io.realm.RealmObject;

public class SubNote extends RealmObject {
    private String subNote;

    public String getSubNote() {
        return subNote;
    }

    public void setSubNote(String subNote) {
        this.subNote = subNote;
    }
}
