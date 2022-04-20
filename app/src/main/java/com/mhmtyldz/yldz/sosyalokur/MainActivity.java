package com.mhmtyldz.yldz.sosyalokur;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.mhmtyldz.yldz.sosyalokur.Fragments.FragmentAnasayfa;
import com.mhmtyldz.yldz.sosyalokur.Fragments.FragmentAra;
import com.mhmtyldz.yldz.sosyalokur.Fragments.FragmentDuvar;
import com.mhmtyldz.yldz.sosyalokur.Fragments.FragmentMyProfile;
import com.mhmtyldz.yldz.sosyalokur.Fragments.FragmentOkumaListesi;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottom_navigation;
    private Fragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottom_navigation = findViewById(R.id.bottom_navigation);

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_tutucu, new FragmentAnasayfa()).commit();

        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.miAnasayfa && item.getItemId() != bottom_navigation.getSelectedItemId()) {
                    // fragment Anasayfa seçilir.
                    tempFragment = new FragmentAnasayfa();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();

                    return true;
                } else if (item.getItemId() == R.id.miOkumaListesi && item.getItemId() != bottom_navigation.getSelectedItemId()) {
                    // fragment okuma listesi seçilir
                    tempFragment = new FragmentOkumaListesi();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();

                    return true;

                } else if (item.getItemId() == R.id.miDuvar && item.getItemId() != bottom_navigation.getSelectedItemId()) {
                    tempFragment = new FragmentDuvar();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();
                    return true;

                } else if (item.getItemId() == R.id.miAra && item.getItemId() != bottom_navigation.getSelectedItemId()) {
                    tempFragment = new FragmentAra();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();
                    return true;
                } else if (item.getItemId() == R.id.miMyProfile && item.getItemId() != bottom_navigation.getSelectedItemId()) {
                    tempFragment = new FragmentMyProfile();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_tutucu, tempFragment).commit();
                    return true;
                }

                return true;
            }
        });


    }
}