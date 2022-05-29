package com.mhmtyldz.yldz.sosyalokur.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.mhmtyldz.yldz.sosyalokur.R;

public class ForgotPasswordActivity extends AppCompatActivity {
    private Button btnResetPassword;
    private TextInputLayout tilEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        tasarimNesneleriniBaslat();
    }

    private void tasarimNesneleriniBaslat() {
        btnResetPassword = findViewById(R.id.btnResetPassword);
        tilEmail = findViewById(R.id.tilDuvarMesaji);
        setListeners();

    }

    private void setListeners() {
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
        tilEmail.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilEmail.setError(null);
                tilEmail.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void resetPassword() {
        String email = tilEmail.getEditText().getText().toString().trim();
        if (email.isEmpty()) {
            tilEmail.setError("Email adresini girmelisin");
            return;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Geçersiz email formatı!");
            return;
        } else {
            // DB'deki email adresine kullanıcının parolası gönderilir veya sıfırlama bağlantısı gönderilir...
            Toast.makeText(ForgotPasswordActivity.this, "Mail kutunuzu kontrol edin", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    }
}