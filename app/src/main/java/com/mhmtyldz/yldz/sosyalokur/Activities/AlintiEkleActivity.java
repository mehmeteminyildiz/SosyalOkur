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
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.mhmtyldz.yldz.sosyalokur.MainActivity;
import com.mhmtyldz.yldz.sosyalokur.R;

import java.util.Objects;

public class AlintiEkleActivity extends AppCompatActivity {
    private TextInputLayout tilKitapAdi, tilYazarAdi, tilAlintiBaslik, tilAlintiMetin;
    private ExtendedFloatingActionButton fabPaylas;
    private ImageButton imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alinti_ekle);

        tasarimNesneleriniBaslat();
    }

    private void alintiEkleDB(String kitapAdi, String yazarAdi, String baslik, String alinti_metni) {
        SharedPreferences sp = getSharedPreferences("girisBilgileri", MODE_PRIVATE);
        String email = sp.getString("email_adresi", "");
        Log.e("TAG", "alıntıEkle email: " + email);
        // insert_alinti.php çalıştırılır.
        // php'ye şunlar gönderilir:
        // EMAIL    KITAP_ADI   YAZAR_ADI   BASLIK  ALINTI_METNI
        // alıntı eklendikten sonra Success mesajı alındıysa:
        Toast.makeText(this, "Alıntı Başarıyla Paylaşıldı!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AlintiEkleActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // bu flag; tüm activity'leri sonlandırır ve MainActivity'yi sanki uygulama yeniden açılıyormuş gibi açar.
        startActivity(intent);
    }

    private void tasarimNesneleriniBaslat() {
        tilKitapAdi = findViewById(R.id.tilKitapAdi);
        tilYazarAdi = findViewById(R.id.tilYazarAdi);
        tilAlintiBaslik = findViewById(R.id.tilAlintiBaslik);
        tilAlintiMetin = findViewById(R.id.tilAlintiMetin);
        fabPaylas = findViewById(R.id.fabPaylas);
        imgBack = findViewById(R.id.imgBack);

        setListeners();
    }

    private void setListeners() {
        fabPaylas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kitapAdi = Objects.requireNonNull(tilKitapAdi.getEditText()).getText().toString().trim();
                String yazarAdi = tilYazarAdi.getEditText().getText().toString().trim();
                String baslik = tilAlintiBaslik.getEditText().getText().toString().trim();
                String alinti_metni = tilAlintiMetin.getEditText().getText().toString().trim();

                if (veriKontrol(kitapAdi, yazarAdi, baslik, alinti_metni)) {
                    alintiEkleDB(kitapAdi, yazarAdi, baslik, alinti_metni);
                }


            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tilKitapAdi.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilKitapAdi.setError(null);
                tilKitapAdi.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tilYazarAdi.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilYazarAdi.setError(null);
                tilYazarAdi.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tilAlintiBaslik.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilAlintiBaslik.setError(null);
                tilAlintiBaslik.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        tilAlintiMetin.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilAlintiMetin.setError(null);
                tilAlintiMetin.setErrorEnabled(false);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private boolean veriKontrol(String kitapAdi, String yazarAdi, String baslik, String alinti_metni) {
        if (TextUtils.isEmpty(kitapAdi)) { // kitapAdi girilmediyse
            tilKitapAdi.setError("Kitap adı gir");
            return false;
        } else if (TextUtils.isEmpty(yazarAdi)) { // yazarAdi girilmediyse
            tilYazarAdi.setError("Yazar adı gir");
            return false;

        } else if (TextUtils.isEmpty(baslik)) { // yazarAdi girilmediyse
            tilAlintiBaslik.setError("Başlık gir");
            return false;

        } else if (TextUtils.isEmpty(alinti_metni)) { // yazarAdi girilmediyse
            tilAlintiMetin.setError("Alıntı giriniz");
            return false;
        }
        return true;
    }


}