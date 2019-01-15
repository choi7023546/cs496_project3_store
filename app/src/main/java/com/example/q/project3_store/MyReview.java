package com.example.q.project3_store;

import android.annotation.SuppressLint;
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

public class MyReview extends ArrayAdapter<Review> {

    private Context mContext;
    private List<Review> menuList = new ArrayList<>();
    public MyReview(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Review> list) {
        super(context, 0 , list);
        mContext = context;
        menuList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.review_layout,parent,false);

         Review currentMovie = menuList.get(position);

        TextView name = (TextView) listItem.findViewById(R.id.review);
        name.setText(currentMovie.getReview());

        return listItem;
    }
}
