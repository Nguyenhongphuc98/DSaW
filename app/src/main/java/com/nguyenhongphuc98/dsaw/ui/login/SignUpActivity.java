package com.nguyenhongphuc98.dsaw.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nguyenhongphuc98.dsaw.R;
import com.nguyenhongphuc98.dsaw.data.DataManager;
import com.nguyenhongphuc98.dsaw.data.model.Account;

public class SignUpActivity extends AppCompatActivity {
    EditText user_name;
    EditText user_password;
    EditText user_password_confirm;
    EditText phone_number;
    EditText identity;
    EditText birthday;
    EditText user_email;
    Button btnSignUp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sign_up);

        InitComponent();
        InitEvent();
    }

    void InitComponent()
    {
        user_name = findViewById(R.id.user_name);
        user_password = findViewById(R.id.user_password);
        user_password_confirm = findViewById(R.id.user_password_confirm);
        phone_number = findViewById(R.id.user_phone);
        identity = findViewById(R.id.user_identity);
        birthday = findViewById(R.id.user_birthday);
        btnSignUp = findViewById(R.id.create_account);
        user_email = findViewById(R.id.user_email);
    }

    void InitEvent()
    {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ValidateForm()) {
                    if (DataManager.Instance().registerProcess(user_email.getText().toString(), user_password.getText().toString())) {
                        Account account = new Account(birthday.getText().toString(), identity.getText().toString(),user_password.getText().toString(),
                                phone_number.getText().toString(), "user", user_name.getText().toString(), "", "", user_email.getText().toString());
                        DataManager.Instance().CreateNewAccount(account);
                        Toast.makeText(getApplicationContext(), "Tạo tài khoản thành công", Toast.LENGTH_LONG).show();
                        CreateAccountSuccessfully();
                    }
                    else Toast.makeText(getApplicationContext(), "Tạo tài khoản thất bại", Toast.LENGTH_LONG).show();

                }
            }
        });
    }

    void CreateAccountSuccessfully()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public boolean ValidateForm() {
        boolean valid = true;

        String userName = user_name.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            user_name.setError("Không được bỏ trống họ tên!");
            valid = false;
        } else {
            user_name.setError(null);
        }

        String password = user_password.getText().toString();
        if (TextUtils.isEmpty(password)) {
            user_password.setError("Không được bỏ trống mật khẩu!");
            valid = false;
        } else {
            user_password.setError(null);
        }

        String email = user_email.getText().toString();
        if (TextUtils.isEmpty(email)) {
            user_email.setError("Không được bỏ trống email!");
            valid = false;
        } else {
            user_email.setError(null);
        }

        String passwordConfirmation = user_password_confirm.getText().toString();
        if (!password.equals(passwordConfirmation)){
            user_password_confirm.setError("Mật khẩu không khớp.");
            valid = false;
        }

        return valid;
    }
}
