package com.mhmtyldz.yldz.sosyalokur.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mhmtyldz.yldz.sosyalokur.Adapters.AraKitapAdapter;
import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Kitap;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Yazar;

import java.util.ArrayList;

public class YazarActivity extends AppCompatActivity {
    private String gelenYazarAdi;
    private TextView tvYazarAdi, tvYazarBiyografi;
    private RecyclerView rv;
    private CardView cardView;
    private ImageView imgPp;
    private Button btnKitaplariGoster;

    private ArrayList<Kitap> kitapArrayList;
    private AraKitapAdapter kitapAdapter;

    private ImageButton imgBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yazar);

        tasarimNesneleriniBaslat();
        yazarAdiniAl();
        kitapArrayList = new ArrayList<>();
        kitapAdapter = new AraKitapAdapter(getApplicationContext(), kitapArrayList);

        getYazarBilgileriveKitaplari(gelenYazarAdi);



        btnKitaplariGoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rv.getVisibility() == View.VISIBLE) {
                    btnKitaplariGoster.setText("Kitapları Göster");
                    rv.setVisibility(View.GONE);
                    cardView.setVisibility(View.VISIBLE);

                } else if (rv.getVisibility() == View.GONE) {
                    btnKitaplariGoster.setText("Kitapları Gizle");
                    rv.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.GONE);
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    private void tasarimNesneleriniBaslat() {
        tvYazarAdi = findViewById(R.id.tvYazarAdi);
        tvYazarBiyografi = findViewById(R.id.tvOkumaListesiSayisi);
        rv = findViewById(R.id.rv);
        cardView = findViewById(R.id.cardView);
        imgPp = findViewById(R.id.imgPp);
        btnKitaplariGoster = findViewById(R.id.btnKitaplariGoster);
        imgBack = findViewById(R.id.imgBack);
        setUpRv();
    }

    private void setUpRv() {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_recyclerview));
        rv.addItemDecoration(dividerItemDecoration);
    }

    private void yazarAdiniAl() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        gelenYazarAdi = bundle.getString("yazar_adi");
        tvYazarAdi.setText(gelenYazarAdi);
    }

    private void getYazarBilgileriveKitaplari(String gelenYazarAdi) {
        //
        Toast.makeText(this, "aranıyor: " + gelenYazarAdi, Toast.LENGTH_SHORT).show();
        kitapArrayList.clear();
        // burada DB'den verileri getirmeliyiz.
        kitapAdapter.notifyDataSetChanged();
        kitapArrayList = new ArrayList<>();

        Yazar yazar = new Yazar(1, gelenYazarAdi, gelenYazarAdi);
        kitapArrayList.add(new Kitap(1, "Yazarın Kitabı - 1", yazar));
        kitapArrayList.add(new Kitap(1, "Yazarın Kitabı - 2", yazar));
        kitapArrayList.add(new Kitap(1, "Yazarın Kitabı - 3", yazar));
        kitapArrayList.add(new Kitap(1, "Yazarın Kitabı - 4", yazar));

        tvYazarBiyografi.setText("Biyografi yazısı burada yer alır. Örnek biyografi:\n" +
                "Yazar, avukat\nTurgut Özakman (1 Eylül 1930, Ankara-28 Eylül 2013, Ankara), " +
                "Türk bürokrat, yazar ve avukat.  1 Eylül 1930 tarihinde Ankara'da dünyaya geldi. " +
                "Ankara Üniversitesi Hukuk Fakültesi'ni bitirdi. Bir süre avukatlık yaptı.");

        verileriYerlestirKitap(kitapArrayList);


    }

    private void verileriYerlestirKitap(ArrayList<Kitap> kitapArrayList) {
        rv.setAdapter(null);
        kitapAdapter = new AraKitapAdapter(getApplicationContext(), kitapArrayList);


        rv.setAdapter(kitapAdapter);

        if (kitapArrayList.size() <= 0) {
            Toast.makeText(this, "Bulunamadı", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Bulundu!", Toast.LENGTH_SHORT).show();

        }
    }
}