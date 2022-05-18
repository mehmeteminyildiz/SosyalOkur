package com.mhmtyldz.yldz.sosyalokur.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.mhmtyldz.yldz.sosyalokur.R;

public class ProfileActivity extends AppCompatActivity {

    private String gelenKullaniciAdi;
    private TextView tvKullaniciAdi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvKullaniciAdi = findViewById(R.id.tvKullaniciAdi);

        // gelen kullaniciAdi değerini alalım:
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        gelenKullaniciAdi = bundle.getString("kullanici_adi");
        tvKullaniciAdi.setText(gelenKullaniciAdi);



    }
}