package com.mhmtyldz.yldz.sosyalokur.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mhmtyldz.yldz.sosyalokur.R;

public class KitapActivity extends AppCompatActivity {

    private String gelenKitapAdi;
    private TextView tvKitapAdi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitap);

        tvKitapAdi = findViewById(R.id.tvKitapAdi);

        // gelen kullaniciAdi değerini alalım:
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        gelenKitapAdi = bundle.getString("kitap_adi");
        tvKitapAdi.setText(gelenKitapAdi);


    }
}