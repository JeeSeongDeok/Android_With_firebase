package com.example.team_p;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<ListVO> listVO = new ArrayList<ListVO>();
    public ListViewAdapter(){
    }
    @Override
    public int getCount() {
        return listVO.size();
    }

    @Override
    public Object getItem(int position) {
        com.example.team_p.ListVO listVO = this.listVO.get(position);
        return listVO;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_listview,parent,false);
        }
        ImageView image = (ImageView)convertView.findViewById(R.id.img);
        TextView title = (TextView)convertView.findViewById(R.id.title);
        TextView Context = (TextView)convertView.findViewById(R.id.context);

        ListVO listViewItem = listVO.get(position);

        image.setImageDrawable(listViewItem.getImg());
        title.setText(listViewItem.getTitle());
        Context.setText(listViewItem.getContext());


        convertView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(context, (pos+1)+"번째 리스트가 클릭됨", Toast.LENGTH_LONG).show();
            }
        });
        return convertView;
    }

    public void addVO(Drawable drawable, String s, String s1) {
        ListVO item = new ListVO();
        item.setImg(drawable);
        item.setTitle(s);
        item.setContext(s1);
        listVO.add(item);
    }
}
