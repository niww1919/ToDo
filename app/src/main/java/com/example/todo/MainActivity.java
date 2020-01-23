/*** https://code.tutsplus.com/ru/tutorials/up-and-running-with-realm-for-android--cms-25241
 * */

package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.todo.realm.MyNote;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;

public class MainActivity extends AppCompatActivity {
    Realm realm;
    RecyclerView.LayoutManager layoutManager;
    NoteAdapter noteAdapter;
    List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //todo realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("mynote.realm")
                .schemaVersion(1)
                .build();
        realm = Realm.getInstance(config);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        noteAdapter = new NoteAdapter(realm);
        recyclerView.setAdapter(noteAdapter);

        list = new ArrayList<>();

        findViewById(R.id.buttonToAddNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                //fixme RealmList https://realm.io/docs/java/latest/#other-libraries

                MyNote myNote1 = realm.createObject(MyNote.class);
                RealmList<String> realmList = new RealmList<>();
                realmList.add(((TextView) findViewById(R.id.editTextForNote)).getText().toString());
                realmList.add(((TextView) findViewById(R.id.editTextForNote)).getText().toString());
                realmList.add(((TextView) findViewById(R.id.editTextForNote)).getText().toString());
                myNote1.setMyNoteRealmList(realmList);

                myNote1.setNameNote(((TextView)findViewById(R.id.editTextForNote)).getText().toString());
                realm.commitTransaction();
                ((TextView) findViewById(R.id.editTextForNote)).setText("");
                Log.i("realm", realm.where(MyNote.class).findAll().asJSON());

            }
        });





    }
}
