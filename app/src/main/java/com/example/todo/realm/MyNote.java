package com.example.todo.realm;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class MyNote extends RealmObject {
    @Required
//    private int id;
    private String myNote;
    private RealmList<SubNote> myNoteRealmList = new RealmList<>();


    public String getMyNote() {
        return myNote;
    }

    public void setMyNote(String myNote) {
        this.myNote = myNote;
    }

    public RealmList<SubNote> getMyNoteRealmList() {
        return myNoteRealmList;
    }

    public void setMyNoteRealmList(RealmList<SubNote> myNoteRealmList) {
        this.myNoteRealmList = myNoteRealmList;
    }
}
