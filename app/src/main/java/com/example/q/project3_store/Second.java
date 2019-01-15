package com.example.q.project3_store;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

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


public class Second extends ListFragment {

    public static Integer customer_number = 0;
    ArrayList<Integer> customer_list;
    String url = "http://socrip3.kaist.ac.kr:9280/customers";
    String store_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_second, container, false);
        TextView button_update = view.findViewById(R.id.textView);
        TextView button_call = view.findViewById(R.id.textView2);
        TextView button_delete = view.findViewById(R.id.textView3);
        ListView listView = view.findViewById(android.R.id.list);

        store_name = getActivity().getIntent().getStringExtra("store_name");
//        store_name = "lotteria";

        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                        Request.Method.GET,
                        url + "/" + store_name,
                        (String) null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    JSONArray contact = response;
                                    customer_list = new ArrayList<>();
                                    for(int i = 0; i < contact.length(); i++) {
                                        JSONObject jsonObject = contact.getJSONObject(i);
                                        if(!jsonObject.getString("store_name").isEmpty()) {
                                            // put in the list
                                            customer_list.add(Integer.valueOf(jsonObject.getString("customer_number")));
                                        }
                                    }
                                    ArrayAdapter adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_list_item_1, customer_list);
                                    ListView listview = view.findViewById(android.R.id.list);
                                    listview.setAdapter(adapter);
                                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            customer_number = customer_list.get(position);
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if(error.networkResponse.statusCode == 404) {
                            Integer[] tmp = {};
                            ArrayAdapter adapter = new ArrayAdapter<Integer>(getContext(), android.R.layout.simple_list_item_1, tmp);
                            ListView listview = view.findViewById(android.R.id.list);
                            listview.setAdapter(adapter);
                        }
                    }
                }
                );
                Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
            }
        });

        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                        Request.Method.GET,
                        url + "/" + store_name + "/" + customer_number,
                        (String) null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

                );
                Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
            }
        });

        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
                        Request.Method.DELETE,
                        url + "/" + store_name + "/" + customer_number,
                        (String) null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }

                );
                Volley.newRequestQueue(getContext()).add(jsonArrayRequest);
            }
        });


        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int i, long id) {

    }

}
