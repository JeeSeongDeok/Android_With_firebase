package com.example.team_p;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class imageAdapter extends ArrayAdapter<String> {
    imageAdapter(Context context, String[] items){
        super(context, R.layout.image_layout, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater imageInflater = LayoutInflater.from(getContext());
        View view = imageInflater.inflate(R.layout.image_layout, parent, false);
        String item = getItem(position);
        TextView textView = (TextView)view.findViewById(R.id.textView);
        ImageView imageView = (ImageView)view.findViewById(R.id.imageView);
        textView.setText(item);
        imageView.setImageResource(R.drawable.list_image);
        return view;
    }
}
