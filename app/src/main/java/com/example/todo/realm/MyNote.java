package com.example.todo.realm;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class MyNote extends RealmObject {
    @Required
//    private int id;
    private RealmList<String> myNoteRealmList;
    private String nameNote;

    public RealmList<String> getMyNoteRealmList() {
        return myNoteRealmList;
    }

    public void setMyNoteRealmList(RealmList<String> myNoteRealmList) {
        this.myNoteRealmList = myNoteRealmList;
    }

    public String getNameNote() {
        return nameNote;
    }

    public void setNameNote(String nameNote) {
        this.nameNote = nameNote;
    }
}
