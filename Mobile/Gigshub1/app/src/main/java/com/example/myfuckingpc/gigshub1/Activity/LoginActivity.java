package com.example.myfuckingpc.gigshub1.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.myfuckingpc.gigshub1.R;
import com.example.myfuckingpc.gigshub1.api.ApiUtils;
import com.example.myfuckingpc.gigshub1.api.UserClient;
import com.example.myfuckingpc.gigshub1.api.VerifyClient;
import com.example.myfuckingpc.gigshub1.model.SavedToken;
import com.example.myfuckingpc.gigshub1.model.User;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username, et_password;
    private String token,
            verify_username = null;
    private PopupWindow pw, pw_verify;
    private EditText edt_register_email, edt_register_username, edt_register_password, edt_register_confirm_password, edt_register_verify_code ;
    private RelativeLayout dim_backgroud;
    private Button btn_verify;

    UserClient userClient = ApiUtils.getUserClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        dim_backgroud = findViewById(R.id.bac_dim_layout);
        btn_verify = findViewById(R.id.btn_register_verify);
        //install lacking in device
        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

    }

    public void clickToLogin(final View view) {
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);

        String username = et_username.getText().toString();
        String password = et_password.getText().toString();

        if(username.equals("")){
            Toast.makeText(this, "Please input Username", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.equals("")){
            Toast.makeText(this, "Please input Password", Toast.LENGTH_SHORT).show();
            return;
        }
        Map<String, String> fields = new HashMap<>();
        fields.put("username", username );
        fields.put("password", password);
        fields.put("grant_type", "password");
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        Call<User> call = userClient.login(fields);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    token = response.body().getAccessToken();
                    String username = response.body().getUserName();
                    String userInfo = token +"|"+ username;
                    SavedToken.setUserInfo(getApplicationContext(),userInfo);
                    progressDialog.dismiss();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    String message = null;
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        message = jsonObject.getString("error_description");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if(message.equals("Please confirm your email first.")){
                        Toast.makeText(LoginActivity.this, "Please check your EMAIL to get VERIFY CODE", Toast.LENGTH_SHORT).show();
                        verify_username = et_username.getText().toString();

                        dim_backgroud.setVisibility(View.VISIBLE);
                        verifycode(view);

                    }
                    else {
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Please check your network connection.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void clickToRegister(View view) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pw = new PopupWindow(inflater.inflate(R.layout.popup_register, null,false), LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, true);
        pw.setAnimationStyle(R.style.popup_window_animation_phone);
        dim_backgroud.setVisibility(View.VISIBLE);
        pw.showAtLocation(view, Gravity.CENTER,0,0);

    }

    public void clickToCancelRegister(View view) {
        pw.dismiss();
        dim_backgroud.setVisibility(View.GONE);
    }

    public void clickToSignUp(final View view) {
        View v = pw.getContentView();
        edt_register_email = v.findViewById(R.id.edt_register_email);
        edt_register_username = v.findViewById(R.id.edt_register_username);
        edt_register_password = v.findViewById(R.id.edt_register_password);
        edt_register_confirm_password = v.findViewById(R.id.edt_register_confirm_password);

        String email = edt_register_email.getText().toString();
        String username = edt_register_username.getText().toString();
        String password = edt_register_password.getText().toString();
        String confirmPassword = edt_register_confirm_password.getText().toString();

        //validateRegister
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        int isHaveSpace;
        Pattern pattern = Pattern.compile(regex);

        Matcher matcher = pattern.matcher(email);
        boolean emailMatcher = matcher.matches();


        if(email.equals("")){
            Toast.makeText(this, "Please enter your Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!emailMatcher){
            Toast.makeText(this, "Invalid Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.equals("")) {
            Toast.makeText(this, "Please enter Username.", Toast.LENGTH_SHORT).show();
            return;
        }
        isHaveSpace = username.indexOf(" ");
        if(!(isHaveSpace==-1)){
            Toast.makeText(this, "Username can't contain WHITE SPACE", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.equals("")){
            Toast.makeText(this, "Please enter Password.", Toast.LENGTH_SHORT).show();
            return;
        }
        isHaveSpace = password.indexOf(" ");
        if(!(isHaveSpace==-1)){
            Toast.makeText(this, "Password can't contain WHITE SPACE", Toast.LENGTH_SHORT).show();
            return;
        }
        if(password.length()<6){
            Toast.makeText(this, "The Password must be at least 6 characters long.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (confirmPassword.equals("")) {
            Toast.makeText(this, "Please enter Confirm Password.", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!password.equals(confirmPassword)){
            Toast.makeText(this, "The password and confirmation password do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        Call<ResponseBody> call = userClient.register(email, username,password,confirmPassword);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Please check your EMAIL to get VERIFY CODE", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                    pw.dismiss();
                    verifycode(view);
                }
                else {

                    Toast.makeText(LoginActivity.this, "Username or Email is existed, please choose another one.", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Please check your network connection.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                return;
            }
        });
    }

    private void verifycode(View view) {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pw_verify = new PopupWindow(inflater.inflate(R.layout.popup_verify_code, null,false), LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT, true);
        pw_verify.setAnimationStyle(R.style.popup_window_animation_phone);
        dim_backgroud.setVisibility(View.VISIBLE);
        pw_verify.showAtLocation(view,Gravity.CENTER,0,0);

    }


    public void clickToVerifyCode(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        VerifyClient verifyService = ApiUtils.verifyClient();
        View v = pw_verify.getContentView();
        edt_register_verify_code = v.findViewById(R.id.edt_register_verify_code);
        String verifyCode = edt_register_verify_code.getText().toString();
        if(verify_username==null){
            verify_username = edt_register_username.getText().toString();
        }
        Call<ResponseBody> call = verifyService.verify(verify_username,verifyCode);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Register successful, you can login now.", Toast.LENGTH_LONG).show();
                    pw_verify.dismiss();
                    dim_backgroud.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Wrong verify code.", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Please check your network connection.", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

    }
}
