package com.mhmtyldz.yldz.sosyalokur.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mhmtyldz.yldz.sosyalokur.MainActivity;
import com.mhmtyldz.yldz.sosyalokur.R;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText etEskiParola, etYeniParola;
    private Button btnDegistir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        etEskiParola = findViewById(R.id.etEskiParola);
        etYeniParola = findViewById(R.id.etYeniParola);
        btnDegistir = findViewById(R.id.btnDegistir);

        btnDegistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String eskiParola = etEskiParola.getText().toString().trim();
                String yeniParola = etYeniParola.getText().toString().trim();

                parolaDegistir(eskiParola, yeniParola);


            }
        });
    }

    private void parolaDegistir(String eskiParola, String yeniParola) {
        SharedPreferences sp = getSharedPreferences("girisBilgileri", Context.MODE_PRIVATE);
        String email = sp.getString("email_adresi", ""); // email DB'ye gönderilecek

        String url = "https://mehmetemin.xyz/sosyalOkur/updateUserPassword.php";
        StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String yanit = jsonObject.getString("success");

                    if (yanit.equals("1")) {
                        // başarıyla değişti ise:
                        Intent intent = new Intent(ChangePasswordActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        Toast.makeText(ChangePasswordActivity.this, "Parola başarıyla değişti!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Bir hata oluştu!", Toast.LENGTH_SHORT).show();

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
                params.put("email", email);
                params.put("oldpassword", eskiParola);
                params.put("newpassword", yeniParola);

                return params;
            }
        };
        Volley.newRequestQueue(ChangePasswordActivity.this).add(postStringRequest);


    }
}