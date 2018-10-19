package com.example.myfuckingpc.loginapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import java.util.HashMap;
import java.util.Map;

import api.ApiUtils;
import api.UserClient;
import model.LoginInfo;
import model.User;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText username, password;
    UserClient userClient = ApiUtils.getUserClient();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.edtUsername);
        password = findViewById(R.id.edtPassword);

        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    public void clickToLogin(View view) {
        Map<String, String> fields = new HashMap<>();
        fields.put("username", username.getText().toString());
        fields.put("password", password.getText().toString());
        fields.put("grant_type", "password");


        //LoginInfo loginInfo = new LoginInfo(username.getText().toString(), password.getText().toString(), "password");
        Call<User> call = userClient.login(fields);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.body().getAccessToken(), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Kiet Lac", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Thai 2 Dui", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
