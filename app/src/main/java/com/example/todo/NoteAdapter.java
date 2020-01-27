package com.example.todo;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.realm.MyNote;

import java.util.List;
import java.util.zip.Inflater;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
    private Realm realm;
    private List<String> result;
    private MyNote myNote;
    private RealmList<MyNote> realmList;
    private RealmResults<MyNote> realmResults;
    private Context context;

    public NoteAdapter(Realm realm, Context context) {
        this.realm = realm;
        this.context = context;

    }

    @NonNull
    @Override
    public NoteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.MyViewHolder holder, int position) {

        realmResults = realm.where(MyNote.class).findAll();
        Log.i("realmResults", realm.where(MyNote.class).findAll().asJSON());

        for (int i = 0; i <= position; i++) {

            //fixme how to create new cardview??
            TextView textView = new TextView(context);
            textView.setTextSize(30);
//                holder.tvNote.setText();
                textView.setText(i + " " + realmResults.get(i).getMyNote());
                holder.cardView.addView(textView);
//                holder.ll.addView(textView);

            for (int j = 0; j < realmResults.get(i).getMyNoteRealmList().size(); j++) {
                TextView textView1 = new TextView(context);
                textView1.setTextSize(30);
//                holder.tvNote.setText();
                textView1.setText(i + " " + realmResults.get(i).getMyNoteRealmList().get(j).getSubNote());
                holder.cardView.addView(textView1);
//                holder.tvNote.setText(" - " + j + " " + realmResults.get(i).getMyNoteRealmList().get(j).getSubNote());
            }
        }


        Log.i("holder", realm.where(MyNote.class).findAll().asJSON());

    }


    @Override
    public int getItemCount() {
//        if (result == null) {
//            return 0;
//        } else {
//
//            return result.size();
//        }
        return (int) realm.where(MyNote.class).count();
//            return  myNote.getMyNoteRealmList().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNote;
        TextView tvList;
        TextView newTV;
        LinearLayoutCompat ll;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ll = itemView.findViewById(R.id.llOfList);
            cardView = itemView.findViewById(R.id.cardView);

//            tvNote = new TextView();
//            tvList = itemView.findViewById(R.id.tvForList);
//            tvNote.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    tvNote.setVisibility(View.INVISIBLE);
//
//                    return false;
//                }
//            });

        }

    }
}
