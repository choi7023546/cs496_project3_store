package com.example.q.project3_store;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fourth extends Fragment {
    // review tab

    private MyReview mAdapter;
    String url = "http://socrip3.kaist.ac.kr:9280/stores";
    String store_name;
    ArrayList<Review> reviewList = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_fourth, container, false);

        store_name = getActivity().getIntent().getStringExtra("store_name");

        final ListView listview = view.findViewById(R.id.review_list);
        TextView text = view.findViewById(R.id.text);
//        ArrayList<Review> reviewList = new ArrayList<>();
//        reviewList.add(new Review("너무 너무 맛있어요 너무 너무 맛있어요너무 너무 맛있어요너무 너무 맛있어요너무 너무 맛있어요너무"));
//        mAdapter = new MyReview(getContext(), reviewList);
//        listview.setAdapter(mAdapter);

//        click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
                JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        url + "/" + store_name,
                        (String) null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    reviewList = new ArrayList<>();
                                    JSONArray tmp = (JSONArray) response.get("review");
                                    for(int i = 0; i< tmp.length(); i++) {
                                        JSONObject comment = tmp.getJSONObject(i);
                                        reviewList.add(new Review(comment.getString("write")));
                                    }
                                    mAdapter = new MyReview(getContext(), reviewList);
                                    listview.setAdapter(mAdapter);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

                );
                Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
//            }
//        });

//        reviewList.add(new Review("너무 너무 맛있어요 너무 너무 맛있어요너무 너무 맛있어요너무 너무 맛있어요너무 너무 맛있어요너무"));

        return view;
    }
}
