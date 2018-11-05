package com.example.myfuckingpc.gigshub1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class LoginActivity extends AppCompatActivity {
    private EditText et_username, et_password;
    private PopupWindow pw;
    private EditText edt_register_email, edt_register_username, edt_register_password, edt_register_confirm_password;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

    }

    public void clickToLogin(View view) {
        if (et_username.getText().toString().equalsIgnoreCase("ADMIN")) {
            intent = new Intent(this, AdminActivity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }

        startActivity(intent);
    }

    public void clickToRegister(View view) {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        pw = new PopupWindow(inflater.inflate(R.layout.popup_register, null, false), LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        pw.setAnimationStyle(R.style.popup_window_animation_phone);
        pw.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

    public void clickToCancelRegister(View view) {
        pw.dismiss();
    }

    public void clickToSignUp(View view) {
        View v = pw.getContentView();
        edt_register_email = v.findViewById(R.id.edt_register_email);
        edt_register_username = v.findViewById(R.id.edt_register_username);
        edt_register_password = v.findViewById(R.id.edt_register_password);
        edt_register_confirm_password = v.findViewById(R.id.edt_register_confirm_password);

        String email = edt_register_email.getText().toString();
    }
}
