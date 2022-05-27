package com.mhmtyldz.yldz.sosyalokur.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.mhmtyldz.yldz.sosyalokur.MainActivity;
import com.mhmtyldz.yldz.sosyalokur.R;

public class AlintiEkleActivity extends AppCompatActivity {
    private TextInputLayout tilKitapAdi, tilYazarAdi, tilAlintiBaslik, tilAlintiMetin;
    private ExtendedFloatingActionButton fabPaylas;
    private ImageButton imgBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alinti_ekle);

        tasarimNesneleriniBaslat();

        fabPaylas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kitapAdi = tilKitapAdi.getEditText().getText().toString().trim();
                String yazarAdi = tilYazarAdi.getEditText().getText().toString().trim();
                String baslik = tilAlintiBaslik.getEditText().getText().toString().trim();
                String alinti_metni = tilAlintiMetin.getEditText().getText().toString().trim();

                alintiEkleDB(kitapAdi, yazarAdi, baslik, alinti_metni);

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void alintiEkleDB(String kitapAdi, String yazarAdi, String baslik, String alinti_metni) {
        // insert_alinti.php çalıştırılır.

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
    }


}