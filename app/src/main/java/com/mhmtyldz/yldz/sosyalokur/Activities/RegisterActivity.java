package com.mhmtyldz.yldz.sosyalokur.Activities;

import androidx.annotation.Nullable;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.mhmtyldz.yldz.sosyalokur.MainActivity;
import com.mhmtyldz.yldz.sosyalokur.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private TextView tvZatenHesabim;
    private Button btnRegister;
    private TextInputLayout tilEmail, tilKullaniciAdi, tilParola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tasarimNesneleriniBaslat();


    }

    private void tasarimNesneleriniBaslat() {
        tvZatenHesabim = findViewById(R.id.tvZatenHesabim);
        btnRegister = findViewById(R.id.btnRegister);
        tilEmail = findViewById(R.id.tilDuvarMesaji);
        tilKullaniciAdi = findViewById(R.id.tilKullaniciAdi);
        tilParola = findViewById(R.id.tilParola);

        setListeners();
    }

    private void setListeners() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        tvZatenHesabim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
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

        tilKullaniciAdi.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilKullaniciAdi.setError(null);
                tilKullaniciAdi.setErrorEnabled(false);
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
        String email = tilEmail.getEditText().getText().toString().trim();
        String parola = tilParola.getEditText().getText().toString().trim();
        String kullaniciAdi = tilKullaniciAdi.getEditText().getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError("Geçersiz Email Formatı");
        } else if (TextUtils.isEmpty(kullaniciAdi)) { // username girilmediyse
            tilKullaniciAdi.setError("Kullanıcı adı gir");
        } else if (TextUtils.isEmpty(parola)) { // password girilmediyse
            tilParola.setError("Parolanı gir");
        } else if (parola.length() < 6) {
            tilParola.setError("Parola en az 6 karakter olmalı");
        } else {
            kullaniciAdiKontrol(kullaniciAdi);
        }


    }

    private void kullaniciAdiKontrol(String kullaniciAdi) {
        //Log.e("TAG", "kullaniciAdiKontrol: gelen k.adı : " + username);


        if (kullaniciAdi.contains(" ")) {
            tilKullaniciAdi.setError("Kullanıcı adı boşluk içeremez");
        } else if (kullaniciAdi.startsWith(".") || kullaniciAdi.endsWith(".")) {
            tilKullaniciAdi.setError("Kullanıcı adı nokta(.) ile başlayamaz/bitemez.");
        } else {
            insertAlinti();
            Toast.makeText(RegisterActivity.this, "Kullanıcı Kaydediliyor...", Toast.LENGTH_SHORT).show();

        }

    }

    private void insertAlinti() {

        // gönderilecek: Kullanici_adi,   email   parola      resim_id
        String KULLANICI_ADI = tilKullaniciAdi.getEditText().getText().toString().trim();
        String EMAIL_ADDRESS = tilEmail.getEditText().getText().toString().trim();
        int RESIM_ID = 1;
        String PAROLA = tilParola.getEditText().getText().toString().trim();

        String url = "https://mehmetemin.xyz/sosyalOkur/insertUser.php";
        StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String yanit = jsonObject.getString("success");

                    if (yanit.equals("1")) {
                        // Kullanıcı başarılı olarak eklendiyse:
                        SharedPreferences sp = getSharedPreferences("girisBilgileri", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putBoolean("girisYapildiMi", true);
                        editor.putString("email_adresi", EMAIL_ADDRESS);
                        editor.putString("kullanici_adi", KULLANICI_ADI);
                        editor.commit();

                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(RegisterActivity.this, "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                    } else if (jsonObject.getString("message")
                            .equalsIgnoreCase("Email already using")) {
                        Toast.makeText(RegisterActivity.this, "Email Kullanılıyor", Toast.LENGTH_SHORT).show();
                    } else if (jsonObject.getString("message")
                            .equalsIgnoreCase("User name already using")) {
                        Toast.makeText(RegisterActivity.this, "Kullanıcı adı kullanılıyor", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RegisterActivity.this, "Beklenmeyen Hata", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", EMAIL_ADDRESS);
                params.put("kullanici_adi", KULLANICI_ADI);
                params.put("parola", PAROLA);
                params.put("resim_id", String.valueOf(RESIM_ID));

                return params;
            }
        };
        Volley.newRequestQueue(RegisterActivity.this).add(postStringRequest);


    }


    private void mysqlKullaniciEkle() {


    }
}