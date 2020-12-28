package com.example.team_p;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class FAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<FItem> data;
    private int layout;

    public FAdapter(Context context, int layout, ArrayList<FItem> data){
        this.inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        return data.get(position).getName_text();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = inflater.inflate(layout, parent, false);
        }
        FItem fItem = data.get(position);

        final ImageView profile = (ImageView)convertView.findViewById(R.id.img_prof);

        FirebaseStorage storage = FirebaseStorage.getInstance("gs://teamp-c4c2f.appspot.com");
        StorageReference storageRef = storage.getReference();
        storageRef.child("NOTEBOOK/"+ fItem.getName_text() + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                //이미지 로드 성공시
                Glide.with(inflater.getContext())
                        .load(uri)
                        .into(profile);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                //이미지 로드 실패시
                Toast.makeText(inflater.getContext(), "실패", Toast.LENGTH_SHORT).show();
            }
        });
        // profile.setImageResource(fItem.getProfile());

        TextView name =(TextView)convertView.findViewById(R.id.name);
        name.setText(fItem.getName_text());

        return convertView;
    }

    public void add(FItem data) {
        this.data.add(data);
    }
}
