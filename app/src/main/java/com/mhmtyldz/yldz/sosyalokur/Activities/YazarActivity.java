package com.mhmtyldz.yldz.sosyalokur.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.mhmtyldz.yldz.sosyalokur.R;

public class YazarActivity extends AppCompatActivity {
    private String gelenYazarAdi;
    private TextView tvYazarAdi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yazar);

        tvYazarAdi = findViewById(R.id.tvYazarAdi);

        // gelen kullaniciAdi değerini alalım:
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        gelenYazarAdi = bundle.getString("yazar_adi");
        tvYazarAdi.setText(gelenYazarAdi);
    }
}