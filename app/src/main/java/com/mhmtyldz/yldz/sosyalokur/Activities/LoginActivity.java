package com.mhmtyldz.yldz.sosyalokur.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.mhmtyldz.yldz.sosyalokur.MainActivity;
import com.mhmtyldz.yldz.sosyalokur.R;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout tilEmail, tilParola;
    private TextView tvParolamiUnuttum;
    private Button btnGiris, btnKayitOl;
    private String email = "";
    private String parola = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tasarimNesneleriniBaslat();
        girisKontrol();
    }

    private void girisKontrol() {
        SharedPreferences sp = getSharedPreferences("girisBilgileri", MODE_PRIVATE);
        boolean girisYapilmisMi = sp.getBoolean("girisYapildiMi", false);
        email = sp.getString("email_adresi", "");
        Log.e("TAG", "girisYapilmisMi: " + girisYapilmisMi);
        if (girisYapilmisMi) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
        }
    }

    private void tasarimNesneleriniBaslat() {
        tilEmail = findViewById(R.id.tilDuvarMesaji);
        tilParola = findViewById(R.id.tilParola);
        tvParolamiUnuttum = findViewById(R.id.tvParolamiUnuttum);
        btnGiris = findViewById(R.id.btnGiris);
        btnKayitOl = findViewById(R.id.btnKayitOl);

        setListeners();
    }

    private void setListeners() {
        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        btnKayitOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        tvParolamiUnuttum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));

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

        tilParola.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilParola.setError(null);
                tilParola.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void validateData() {
        email = tilEmail.getEditText().getText().toString().trim();
        parola = tilParola.getEditText().getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Geçersiz email formatı");
        } else if (TextUtils.isEmpty(parola)) {
            tilParola.setError("Parolanı gir");
        } else {
            girisIslemiYap(email, parola);
        }
    }

    private void girisIslemiYap(String email, String parola) {
        Toast.makeText(LoginActivity.this, "Email: " + email + "\nParola: " + parola, Toast.LENGTH_SHORT).show();
        // email ve parola DB'den kontrol edilir.
        if (email.equals("mhmtxyildiz@gmail.com") && parola.equals("123")) {
            SharedPreferences sp = getSharedPreferences("girisBilgileri", MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("girisYapildiMi", true);
            editor.putString("email_adresi", email);
            editor.commit();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else { // Yanlış ise:
            Toast.makeText(LoginActivity.this, "Email veya Parola Hatalı", Toast.LENGTH_SHORT).show();
            
        }
    }
}