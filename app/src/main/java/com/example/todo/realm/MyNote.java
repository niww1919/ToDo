package com.example.todo.realm;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

public class MyNote extends RealmObject {
    @Required
//    private int id;
    private String myNote;
    private String mySubNote;

    public String getMyNote() {
        return myNote;
    }

    public void setMyNote(String myNote) {
        this.myNote = myNote;
    }

    public String getMySubNote() {
        return mySubNote;
    }

    public void setMySubNote(String mySubNote) {
        this.mySubNote = mySubNote;
    }

}
