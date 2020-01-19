package com.example.todo.realm;

import io.realm.RealmObject;
import io.realm.annotations.Required;

public class MyNote extends RealmObject {
    @Required
//    private int id;
    private String myNote;

    public String getMyNote() {
        return myNote;
    }

    public void setMyNote(String myNote) {
        this.myNote = myNote;
    }

}
