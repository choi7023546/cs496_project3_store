package com.example.q.project3_store;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static Integer customer_number = 0;
    ArrayList<Integer> customer_list;
    String url = "http://socrip3.kaist.ac.kr:9280/customers";
    String store_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button update_button = findViewById(R.id.button);
        Button call_button = findViewById(R.id.button2);
        Button delete_button = findViewById(R.id.button3);
//        TextView view = findViewById(R.id.textView);
//        view.setText(FirebaseInstanceId.getInstance().getToken());
        store_name = getIntent().getStringExtra("store_name");


        // get
        update_button.setOnClickListener(new View.OnClickListener() {
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
                                    ArrayAdapter adapter = new ArrayAdapter<Integer>(MainActivity.this, android.R.layout.simple_list_item_1, customer_list);
                                    ListView listview = findViewById(R.id.listview);
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
                            ArrayAdapter adapter = new ArrayAdapter<Integer>(MainActivity.this, android.R.layout.simple_list_item_1, tmp);
                            ListView listview = findViewById(R.id.listview);
                            listview.setAdapter(adapter);
                        }
                    }
                }
                );
                Volley.newRequestQueue(MainActivity.this).add(jsonArrayRequest);
            }
        });

        call_button.setOnClickListener(new View.OnClickListener() {
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
                Volley.newRequestQueue(MainActivity.this).add(jsonArrayRequest);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
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
                Volley.newRequestQueue(MainActivity.this).add(jsonArrayRequest);
            }
        });
    }
}
