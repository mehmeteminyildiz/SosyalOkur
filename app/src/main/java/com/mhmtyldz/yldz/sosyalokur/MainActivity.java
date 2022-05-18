package com.mhmtyldz.yldz.sosyalokur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.mhmtyldz.yldz.sosyalokur.Fragments.FragmentAnasayfa;
import com.mhmtyldz.yldz.sosyalokur.Fragments.FragmentAra;
import com.mhmtyldz.yldz.sosyalokur.Fragments.FragmentDuvar;
import com.mhmtyldz.yldz.sosyalokur.Fragments.FragmentMyProfile;
import com.mhmtyldz.yldz.sosyalokur.Fragments.FragmentOkumaListesi;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottom_navigation;
    private BottomAppBar bottomAppBar;
    private Fragment tempFragment;
    private FloatingActionButton fab;

    private int seciliSayfa = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomAppBar = findViewById(R.id.bottomAppBar);
        fab = findViewById(R.id.fab);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_tutucu, new FragmentAnasayfa()).commit();
        seciliSayfa = R.id.miAnasayfa;
        //fab.setVisibility(View.VISIBLE);

        bottomAppBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.miAnasayfa && seciliSayfa != R.id.miAnasayfa) {
                    //fab.setVisibility(View.VISIBLE);
                    seciliSayfa = R.id.miAnasayfa;
                    tempFragment = new FragmentAnasayfa();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();

                    return true;
                } else if (item.getItemId() == R.id.miOkumaListesi && seciliSayfa != R.id.miOkumaListesi) {
                    // fragment okuma listesi seçilir
                    //fab.setVisibility(View.VISIBLE);
                    seciliSayfa = R.id.miOkumaListesi;

                    tempFragment = new FragmentOkumaListesi();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();

                    return true;

                } else if (item.getItemId() == R.id.miDuvar && seciliSayfa != R.id.miDuvar) {
                    //fab.setVisibility(View.VISIBLE);
                    seciliSayfa = R.id.miDuvar;

                    tempFragment = new FragmentDuvar();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();


                    return true;

                } else if (item.getItemId() == R.id.miAra && seciliSayfa != R.id.miAra) {
                    seciliSayfa = R.id.miAra;
                    //fab.setVisibility(View.INVISIBLE);

                    tempFragment = new FragmentAra();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.miMyProfile && seciliSayfa != R.id.miMyProfile) {
                    seciliSayfa = R.id.miMyProfile;
                    //fab.setVisibility(View.INVISIBLE);


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
                } else if (seciliSayfa == R.id.miOkumaListesi) {
                    // okuma listesine kitap ekle sayfasına git:
                    Toast.makeText(MainActivity.this, "FAB clicked in Okuma Listesi", Toast.LENGTH_SHORT).show();
                } else if (seciliSayfa == R.id.miDuvar) {
                    // duvara mesaj ekle:
                    Toast.makeText(MainActivity.this, "FAB clicked in Duvar", Toast.LENGTH_SHORT).show();
                } else if (seciliSayfa == R.id.miAra) {
                    Toast.makeText(MainActivity.this, "FAB clicked in Ara", Toast.LENGTH_SHORT).show();
                } else if (seciliSayfa == R.id.miMyProfile) {
                    Toast.makeText(MainActivity.this, "FAB clicked in MyProfile", Toast.LENGTH_SHORT).show();
                }

            }
        });

//        bottom_navigation = findViewById(R.id.bottom_navigation);
//
//        getSupportFragmentManager().beginTransaction().add(R.id.fragment_tutucu, new FragmentAnasayfa()).commit();
//
//        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                if (item.getItemId() == R.id.miAnasayfa && item.getItemId() != bottom_navigation.getSelectedItemId()) {
//                    // fragment Anasayfa seçilir.
//                    tempFragment = new FragmentAnasayfa();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();
//
//                    return true;
//                } else if (item.getItemId() == R.id.miOkumaListesi && item.getItemId() != bottom_navigation.getSelectedItemId()) {
//                    // fragment okuma listesi seçilir
//                    tempFragment = new FragmentOkumaListesi();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();
//
//                    return true;
//
//                } else if (item.getItemId() == R.id.miDuvar && item.getItemId() != bottom_navigation.getSelectedItemId()) {
//                    tempFragment = new FragmentDuvar();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();
//                    return true;
//
//                } else if (item.getItemId() == R.id.miAra && item.getItemId() != bottom_navigation.getSelectedItemId()) {
//                    tempFragment = new FragmentAra();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();
//                    return true;
//                } else if (item.getItemId() == R.id.miMyProfile && item.getItemId() != bottom_navigation.getSelectedItemId()) {
//                    tempFragment = new FragmentMyProfile();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();
//                    return true;
//                }
//
//                return true;
//            }
//        });


    }
}