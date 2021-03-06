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

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
//                        myNote = realm.createObject(MyNote.class);
                        RealmResults<MyNote> realmResults = realm.where(MyNote.class).findAll();//fixme
                        Log.i("realmResults", realmResults.asJSON() + "");

                        if (direction == ItemTouchHelper.LEFT) {
                            realmResults.get(viewHolder.getAdapterPosition()).deleteFromRealm();
                        }
                        if (direction == ItemTouchHelper.RIGHT) {
//                            RealmList<String> realmResults1 = realmResults.get(viewHolder.getAdapterPosition()).getMyNoteRealmList();//fixme
//
//                            if (realmResults1 == null) {
//                                realmResults1 = new RealmList<>();
//                            }
//                            realmResults1.add("realmResults1");
//
//                            Log.i("realmResults1", realmResults.get(viewHolder.getAdapterPosition()).getMyNoteRealmList().size()+ "");
//
//                            realmResults.get(viewHolder.getAdapterPosition()).setMyNoteRealmList(realmResults1);
//
////                            myNote.setMyNoteRealmList(realmResults1);
//                            Log.i("realmResults1", realmResults.get(viewHolder.getAdapterPosition()).getMyNoteRealmList().size()+"");


                            showPopupMenu();
                        }
//                            Log.i("realmResults", realmResults.get(viewHolder.getAdapterPosition()).getMyNote() +" "+ viewHolder.getAdapterPosition());


                    }
                });


            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        list = new ArrayList<>();


    }

    private void showPopupMenu() {
        LayoutInflater inflater = LayoutInflater.from(getBaseContext());
        final View addNote = inflater.inflate(R.layout.add_note, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        addNote.findViewById(R.id.editTextForNoteBySwipe).isFocused();
        // https://stackoverflow.com/questions/2004344/how-do-i-handle-imeoptions-done-button-click
        builder.setView(addNote);
//        showSoftKeyboard(addNote);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        myNote = realm.createObject(MyNote.class);
                        myNote.setMyNote(((TextView) addNote.findViewById(R.id.editTextForNoteBySwipe)).getText().toString());
//                        myNote.setMyNote("Work");


//                        results1.add(((TextView) findViewById(R.layout.add_note)).getText().toString());
//                        results1.setMyNote(((TextView) findViewById(R.id.editTextForNote)).getText().toString());
                    }
                });

                recreate();
            }
        });

        builder.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                showPopupMenu();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showSoftKeyboard(View view) {
        if(view.requestFocus()){
            InputMethodManager imm =(InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view,InputMethodManager.SHOW_IMPLICIT);
        }
    }

}
