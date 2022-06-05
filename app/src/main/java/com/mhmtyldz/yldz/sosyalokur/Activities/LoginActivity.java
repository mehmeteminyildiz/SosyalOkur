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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout tilEmail, tilParola;
    private TextView tvParolamiUnuttum;
    private Button btnGiris, btnKayitOl;
    private String email = "";
    private String parola = "";
    private String kullanici_adi = "";

    private String yanit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        yanit = "yanlis";

        tasarimNesneleriniBaslat();
        girisKontrol();
    }

    private void girisKontrol() {
        SharedPreferences sp = getSharedPreferences("girisBilgileri", MODE_PRIVATE);
        boolean girisYapilmisMi = sp.getBoolean("girisYapildiMi", false);
        email = sp.getString("email_adresi", "");
        kullanici_adi = sp.getString("kullanici_adi", "");
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
            getForBildirimDetay(email, parola);

        }
    }


    private void getForBildirimDetay(String email, String parola) {
        String url = "https://mehmetemin.xyz/sosyalOkur/login.php";

        StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("TAG", "onResponse: " + response.toString().trim());

                    if (jsonObject.getString("success").equals("1")) {
                        kullanici_adi = jsonObject.getString("KULLANICI_ADI");
                        girisBasarili();

                        Log.e("TAG", "kullanici_adi: " + kullanici_adi);
                    } else {
                        Toast.makeText(LoginActivity.this, "Email veya Parola Hatalı"
                                , Toast.LENGTH_SHORT).show();

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Bir hata oluştu", Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(LoginActivity.this, "Bir hata oluştu", Toast.LENGTH_SHORT).show();

            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", parola);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postStringRequest);

    }

    private void girisBasarili() {
        SharedPreferences sp = getSharedPreferences("girisBilgileri", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("girisYapildiMi", true);
        editor.putString("email_adresi", email);
        editor.putString("kullanici_adi", kullanici_adi);
        editor.commit();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }
}