/*** https://code.tutsplus.com/ru/tutorials/up-and-running-with-realm-for-android--cms-25241
 * */

package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todo.realm.MyNote;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {
    Realm realm;
    RecyclerView.LayoutManager layoutManager;
    NoteAdapter noteAdapter;
    List<String> list;
    MyNote myNote;
    RealmList<String> realmList;


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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, final int direction) {

                Toast.makeText(getBaseContext(), "Ok", Toast.LENGTH_SHORT).show();
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        myNote = realm.createObject(MyNote.class);
                        RealmResults<MyNote> realmResults = realm.where(MyNote.class).findAll();//fixme
//                            realmResults.get(viewHolder.getAdapterPosition()).setMyNote("1");
                        if (direction == ItemTouchHelper.LEFT) {

                            realmResults.get(viewHolder.getAdapterPosition()).deleteFromRealm();
                        } else {

                            RealmList<String> realmResults1 = realmResults.get(viewHolder.getAdapterPosition()).getMyNoteRealmList();//fixme
                            if (realmResults1 == null) {
                                realmResults1 = new RealmList<>();
                            }
                            realmResults1.add("realmResults1");

                            Log.i("getMyNoteRealmList", realmResults.get(viewHolder.getAdapterPosition()).getMyNoteRealmList().size()+ "");

                            realmResults.get(viewHolder.getAdapterPosition()).setMyNoteRealmList(realmResults1);
                            myNote.setMyNoteRealmList(realmResults1);
                            Log.i("myNote", + myNote.getMyNoteRealmList().size()+"");


//                            showPopupMenu();
                        }
//                            Log.i("realmResults", realmResults.get(viewHolder.getAdapterPosition()).getMyNote() +" "+ viewHolder.getAdapterPosition());


                    }
                });


            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        list = new ArrayList<>();

        findViewById(R.id.buttonToAddNote).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                //fixme RealmList https://realm.io/docs/java/latest/#other-libraries

                myNote = realm.createObject(MyNote.class);
                myNote.setMyNote(((TextView) findViewById(R.id.editTextForNote)).getText().toString());

                realm.commitTransaction();
                ((TextView) findViewById(R.id.editTextForNote)).setText("");
                Log.i("realm", realm.where(MyNote.class).findAll().asJSON());

            }
        });


    }

    private void showPopupMenu() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        View addNote = inflater.inflate(R.layout.add_note, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(addNote);
        builder.show();

    }

}
