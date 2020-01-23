package com.example.todo;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todo.realm.MyNote;

import java.util.zip.Inflater;

import io.realm.Realm;
import io.realm.RealmResults;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
    private Realm realm;
    private RealmResults<MyNote> result;

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


        result = realm.where(MyNote.class).findAll();

        for (int i = 0; i <= position; i++) {

            holder.tvNote.setText(result.get(i).getNameNote());
            for (int j = 0; j < result.get(i).getMyNoteRealmList().size(); j++) {
                holder.tvList.setText(" - " +j +" " + result.get(i).getMyNoteRealmList().get(j));

            }
//        holder.tvList.setText(" - "+ result.asJSON());


        }
//        holder.tvNote.setText("Test");
        Log.i("holder", realm.where(MyNote.class).findAll().asJSON());

    }


    @Override
    public int getItemCount() {
        return (int) realm.where(MyNote.class).count();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNote;
        TextView tvList;
        TextView newTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNote = itemView.findViewById(R.id.tvForItem);
            tvList = itemView.findViewById(R.id.tvForList);
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
