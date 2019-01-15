package com.example.q.project3_store;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyListView extends ArrayAdapter<Menu>{

    private Context mContext;
    private List<Menu> menuList = new ArrayList<>();
    public MyListView(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Menu> list) {
        super(context, 0 , list);
        mContext = context;
        menuList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.menu_layout,parent,false);

        Menu currentMovie = menuList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.text_menu);
        name.setText(currentMovie.getMenu());

        TextView release = (TextView) listItem.findViewById(R.id.text_price);
        release.setText(currentMovie.getPrice());

        return listItem;
    }


}
