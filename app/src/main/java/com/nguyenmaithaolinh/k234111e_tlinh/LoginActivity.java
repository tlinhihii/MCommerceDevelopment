package com.nguyenmaithaolinh.k234111e_tlinh;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {

    /* declare all views as variables
    **/
    EditText editUsername;
    EditText editPassword;
    TextView txtMessage;
    CheckBox chkSaveInfo;
    String shared_pref_key="LoginInfo";
    RadioButton radAdmin, radEmployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        addViews();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void addViews() {
        editUsername=findViewById (R.id.editUsername);
        editPassword=findViewById (R.id.editPassword);
        txtMessage=findViewById (R.id.txtMessage);
        radAdmin = findViewById(R.id.radAdmin);
        radEmployee = findViewById(R.id.radEmployee);
        chkSaveInfo = findViewById(R.id.chkSaveInfo);
    }

    public void loginSystem(View view) {
        String username=editUsername.getText ().toString ();
        String password=editPassword.getText ().toString ();
        boolean saved= false;
        if (chkSaveInfo.isChecked())
        {
            saved=true;
        }
        if(username.equalsIgnoreCase("admin") &&
        password.equals("123"))
            if (txtMessage != null) {
                txtMessage.setText(getString(R.string.str_login_success));
                txtMessage.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
                SharedPreferences preferences=getSharedPreferences(shared_pref_key, MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences.edit();
                editor.putString("UserName", username);
                editor.putString("PassWord", password);
                editor.putBoolean("Saved", saved);
                editor.commit();
                if (radAdmin.isChecked())
                {
                    Intent intent= new Intent(LoginActivity.this, MainActivity.class);
                }
                else {
                    Intent intent=new Intent(LoginActivity.this, EmployeeManagementActivity.class);
                    startActivity(intent);
                    }
            }
    }

    public void exitSystem(View view) {
        finish ();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferences = getSharedPreferences(shared_pref_key, MODE_PRIVATE);
        String username = preferences.getString("UserName","");
        String password = preferences.getString("PassWord", "");

        boolean saved = preferences.getBoolean("Saved", false);

        if (saved) {
            editUsername.setText(username);
            editPassword.setText(password);
        }
        chkSaveInfo.setChecked(saved);
    }

}