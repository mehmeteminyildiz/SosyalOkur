package com.mhmtyldz.yldz.sosyalokur;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;
import com.mhmtyldz.yldz.sosyalokur.Activities.AlintiEkleActivity;
import com.mhmtyldz.yldz.sosyalokur.Fragments.FragmentAnasayfa;
import com.mhmtyldz.yldz.sosyalokur.Fragments.FragmentAra;
import com.mhmtyldz.yldz.sosyalokur.Fragments.FragmentDuvar;
import com.mhmtyldz.yldz.sosyalokur.Fragments.FragmentMyProfile;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottom_navigation;
    private Fragment tempFragment;
    private FloatingActionButton fab;


    private int seciliSayfa = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.fab);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_tutucu, new FragmentAnasayfa()).commit();
        seciliSayfa = R.id.miAnasayfa;

        bottom_navigation = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_tutucu, new FragmentAnasayfa()).commit();

        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.miAnasayfa && item.getItemId() != bottom_navigation.getSelectedItemId()) {
                    // fragment Anasayfa seçilir.
                    fab.setVisibility(View.VISIBLE);
                    seciliSayfa = R.id.miAnasayfa;
                    tempFragment = new FragmentAnasayfa();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();

                    return true;
                } else if (item.getItemId() == R.id.miDuvar && item.getItemId() != bottom_navigation.getSelectedItemId()) {
                    fab.setVisibility(View.GONE);
                    seciliSayfa = R.id.miDuvar;
                    tempFragment = new FragmentDuvar();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();
                    return true;

                } else if (item.getItemId() == R.id.miAra && item.getItemId() != bottom_navigation.getSelectedItemId()) {
                    fab.setVisibility(View.GONE);
                    seciliSayfa = R.id.miAra;
                    tempFragment = new FragmentAra();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.miMyProfile && item.getItemId() != bottom_navigation.getSelectedItemId()) {
                    fab.setVisibility(View.GONE);
                    seciliSayfa = R.id.miMyProfile;
                    tempFragment = new FragmentMyProfile();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();
                    return true;
                }
                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seciliSayfa == R.id.miAnasayfa) {
                    // alıntı ekle sayfasına git:
                    Toast.makeText(MainActivity.this, "FAB clicked in Ana Sayfa", Toast.LENGTH_SHORT).show();
                    alintiEkleSayfasinaGit();
                } /*else if (seciliSayfa == R.id.miOkumaListesi) {
                    // okuma listesine kitap ekle diyalogunu aç:
                    Toast.makeText(MainActivity.this, "FAB clicked in Okuma Listesi", Toast.LENGTH_SHORT).show();
                    showKitapEkleDialog();
                    //kitapEkleSayfasinaGit();
                } */else if (seciliSayfa == R.id.miDuvar) {
                    // duvara mesaj ekle:
                    Toast.makeText(MainActivity.this, "FAB clicked in Duvar", Toast.LENGTH_SHORT).show();
                } else if (seciliSayfa == R.id.miAra) {
                    Toast.makeText(MainActivity.this, "FAB clicked in Ara", Toast.LENGTH_SHORT).show();
                } else if (seciliSayfa == R.id.miMyProfile) {
                    Toast.makeText(MainActivity.this, "FAB clicked in MyProfile", Toast.LENGTH_SHORT).show();
                }

            }
        });

        SharedPreferences sp = getSharedPreferences("girisBilgileri", MODE_PRIVATE);
        boolean girisYapildiMi = sp.getBoolean("girisYapildiMi", false);
        String email = sp.getString("email_adresi", "");
        String toastMesaj = "girisYapildiMi: " + girisYapildiMi + "\nEmail: " + email;
        Log.e("TAG", "main act - " + toastMesaj);
    }

    private void showKitapEkleDialog() {
        View tasarim = getLayoutInflater().inflate(R.layout.dialog_kitap_ekle, null);
        final TextInputLayout tilDialogYazarAdi = tasarim.findViewById(R.id.tilDialogYazarAdi);
        final TextInputLayout tilDialogKitapAdi = tasarim.findViewById(R.id.tilDialogKitapAdi);
        final Button btnIptal = tasarim.findViewById(R.id.btnIptal);
        final Button btnKaydet = tasarim.findViewById(R.id.btnKaydet);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Kitap Ekle");
        builder.setCancelable(true);
        builder.setView(tasarim);

        AlertDialog testDialog = builder.create();

        testDialog.show();

        tilDialogKitapAdi.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilDialogKitapAdi.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        tilDialogYazarAdi.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilDialogYazarAdi.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kontrol(tilDialogKitapAdi, tilDialogYazarAdi)) {
                    String kitap_adi = tilDialogKitapAdi.getEditText().getText().toString().trim();
                    String yazar_adi = tilDialogYazarAdi.getEditText().getText().toString().trim();
                    // DB'ye kaydedelim. Success mesajı geldiyse:

                    Toast.makeText(MainActivity.this, "Kitap Adı: " + kitap_adi + "\nYazar Adı: " + yazar_adi, Toast.LENGTH_SHORT).show();
                    testDialog.dismiss();

                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

        btnIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "iptal: ");
                testDialog.dismiss();

            }
        });


    }

    private boolean kontrol(TextInputLayout tilDialogKitapAdi, TextInputLayout tilDialogYazarAdi) {
        String kitap_adi = tilDialogKitapAdi.getEditText().getText().toString().trim();
        String yazar_adi = tilDialogYazarAdi.getEditText().getText().toString().trim();
        if (kitap_adi.isEmpty()) {
            tilDialogKitapAdi.setError("Boş bırakılamaz");
            Log.e("TAG", "error: ");
            return false;

        } else if (yazar_adi.isEmpty()) {
            tilDialogYazarAdi.setError("Boş bırakılamaz");
            Log.e("TAG", "error: ");
            return false;
        } else {
            return true;
        }
    }

    private void alintiEkleSayfasinaGit() {
        startActivity(new Intent(MainActivity.this, AlintiEkleActivity.class));

    }

}