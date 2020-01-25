package com.example.todo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
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

    public NoteAdapter(Realm realm) {
        this.realm = realm;

    }

    @NonNull
    @Override
    public NoteAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.MyViewHolder holder, int position) {


//        result = realm.getSchema().get(
        realmResults = realm.where(MyNote.class).findAll();
        Log.i("realmResults", realm.where(MyNote.class).findAll().asJSON());

        for (int i = 0; i <= position; i++) {
            holder.tvNote.setText(" - " + i + " "+ realmResults.get(i).getMyNote());
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

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNote = itemView.findViewById(R.id.tvForItem);
//            tvList = itemView.findViewById(R.id.tvForList);
            tvNote.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    tvNote.setVisibility(View.INVISIBLE);

                    return false;
                }
            });

        }

    }
}
