package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.todo.realm.MyNote;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //todo realm
        Realm.init(this);
        realm = Realm.getDefaultInstance();

        findViewById(R.id.buttonToAddNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                MyNote myNote1 = realm.createObject(MyNote.class);
//                myNote1.setId(1);
                myNote1.setMyNote(((TextView)findViewById(R.id.editTextForNote)).getText().toString());
                realm.commitTransaction();
                ((TextView) findViewById(R.id.editTextForNote)).setText("");
                Log.i("realm", realm.where(MyNote.class).findAll().asJSON());

                ((TextView)findViewById(R.id.textView)).setText(realm.where(MyNote.class).findAll().asJSON());
            }
        });



    }
}
