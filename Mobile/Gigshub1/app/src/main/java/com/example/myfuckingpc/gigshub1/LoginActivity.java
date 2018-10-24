package com.example.myfuckingpc.gigshub1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import java.util.HashMap;
import java.util.Map;

import api.ApiUtils;
import api.UserClient;
import model.SavedToken;
import model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username, et_password;
    private String username, password, token;
    UserClient userClient = ApiUtils.getUserClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);



    }

    public void clickToLogin(View view) {
        username = et_username.getText().toString();
        password = et_password.getText().toString();
        Map<String, String> fields = new HashMap<>();
        fields.put("username", username );
        fields.put("password", password);
        fields.put("grant_type", "password");
        Call<User> call = userClient.login(fields);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    token = response.body().getAccessToken();
                    SavedToken.setUserToken(LoginActivity.this, token);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this, "Wrong Username or Password.", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Please check your network connection.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
