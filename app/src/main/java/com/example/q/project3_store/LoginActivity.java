package com.example.q.project3_store;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.q.project3_store.Retrofit.IMyInterface;
import com.example.q.project3_store.Retrofit.RetrofitClient;
import com.github.javiersantos.materialstyleddialogs.MaterialStyledDialog;
import com.google.firebase.iid.FirebaseInstanceId;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {

    private Button button_login;
    private Button button_register;
    EditText email = null;
    EditText password = null;
    String url = "http://socrip3.kaist.ac.kr:9280";
    String store_name;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyInterface iMyInterface;

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Log.d("token: ", FirebaseInstanceId.getInstance().getToken());

        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyInterface = retrofitClient.create(IMyInterface.class);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        button_login = (Button)findViewById(R.id.login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent loginIntent = new Intent(LoginActivity.this,StoreInfoActivity.class );
//                LoginActivity.this.startActivity(loginIntent);
                try {
                    loginUser(email.getText().toString(), password.getText().toString());
                    email.setText("");
                    password.setText("");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        button_register = findViewById(R.id.register);
        button_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View register_layout = LayoutInflater.from(LoginActivity.this)
                        .inflate(R.layout.register_layout, null);
                new MaterialStyledDialog.Builder(LoginActivity.this)
                        .setIcon(R.drawable.heart)
                        .setHeaderColor(R.color.yellow)
                        .setTitle("REGISTRATION")
                        .setDescription("Please fill all fields")
                        .setCustomView(register_layout)
                        .setNegativeText("CANCEL")
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveText("REGISTER")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                MaterialEditText edit_email = register_layout.findViewById(R.id.email);
                                MaterialEditText edit_name = register_layout.findViewById(R.id.name);
                                MaterialEditText edit_password = register_layout.findViewById(R.id.password);

                                if(TextUtils.isEmpty(edit_email.getText().toString())) {
                                    Toast.makeText(LoginActivity.this, "Email cannot be null or empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if(TextUtils.isEmpty(edit_name.getText().toString())) {
                                    Toast.makeText(LoginActivity.this, "Name cannot be null or empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                if(TextUtils.isEmpty(edit_password.getText().toString())) {
                                    Toast.makeText(LoginActivity.this, "Password cannot be null or empty", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                registerUser(edit_email.getText().toString(), edit_name.getText().toString(), edit_password.getText().toString());

                            }
                        }).show();

            }
        });
    }
    private void loginUser(final String email, String password) throws JSONException {
        if(TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email cannot be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password cannot be null or empty", Toast.LENGTH_SHORT).show();
            return;
        }
        compositeDisposable.add(iMyInterface.loginUser(email, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {

                    @Override
                    public void accept(String response) throws Exception {
                        JSONObject jsonobject = new JSONObject(response);
                        Toast.makeText(LoginActivity.this, "" + jsonobject.getString("message"), Toast.LENGTH_SHORT).show();
//                        JSONObject information = new JSONObject();
//                        information.put("email", email);
                        store_name = jsonobject.getString("store_name");
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("store_name", store_name);
                        startActivity(intent);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(LoginActivity.this, "wrong information", Toast.LENGTH_SHORT).show();
                    }
                })

        );


    }

    private void registerUser(String email, String name, String password) {

        compositeDisposable.add(iMyInterface.registerUser(email, name, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {

                    @Override
                    public void accept(String response) throws Exception {
                        Toast.makeText(LoginActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                    }
                }));
    }
}