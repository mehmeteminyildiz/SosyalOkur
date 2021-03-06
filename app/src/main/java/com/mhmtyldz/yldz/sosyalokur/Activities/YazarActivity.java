package com.mhmtyldz.yldz.sosyalokur.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mhmtyldz.yldz.sosyalokur.Adapters.AraKitapAdapter;
import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Alinti;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Kitap;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Yazar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class YazarActivity extends AppCompatActivity {
    private String gelenYazarAd;
    private String gelenYazarSoyad;
    private TextView tvYazarAdi, tvYazarBiyografi;
    private RecyclerView rv;
    private CardView cardView;
    private ImageView imgPp;
    private Button btnKitaplariGoster;

    private ArrayList<Kitap> kitapArrayList;
    private AraKitapAdapter kitapAdapter;

    private ImageButton imgBack;

    // Load image from URL işlemleri
    Handler mainHandler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yazar);

        kitapArrayList = new ArrayList<>();
        kitapAdapter = new AraKitapAdapter(getApplicationContext(), kitapArrayList);
        tasarimNesneleriniBaslat();


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
        tvYazarAdi = findViewById(R.id.tvYazarAd);
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

        yazarAdiniAl();

    }

    private void yazarAdiniAl() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        gelenYazarAd = bundle.getString("yazar_adi");
        gelenYazarSoyad = bundle.getString("yazar_soyadi");
        tvYazarAdi.setText(gelenYazarAd + " " + gelenYazarSoyad);

        getForYazar();
        //getYazarBilgileriveKitaplari(gelenYazarAd);
    }

    private void getForYazar() {

        String url = "https://mehmetemin.xyz/sosyalOkur/getForYazar.php";
        StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    kitapArrayList.clear();
                    kitapAdapter.notifyDataSetChanged();
                    kitapArrayList = new ArrayList<>();

                    JSONArray YAZAR = jsonObject.getJSONArray("Yazar"); // tablo adı

                    String resim_url = YAZAR.getJSONObject(0).getString("yazar_resim_url");
                    String yazarBiyografi = YAZAR.getJSONObject(0).getString("yazar_biyografi");
                    int YAZAR_ID = YAZAR.getJSONObject(0).getInt("YAZAR_ID");


                    tvYazarBiyografi.setText(yazarBiyografi);
                    new FetchImage(resim_url).start();


                    JSONArray KITAPLAR = jsonObject.getJSONArray("Kitaplar"); // tablo adı

                    for (int i = 0; i < KITAPLAR.length(); i++) {
                        JSONObject kitaplarjsonObject = KITAPLAR.getJSONObject(i);

                        String KITAP_ADI = kitaplarjsonObject.getString("KITAP_ADI");
                        int KITAP_ID = kitaplarjsonObject.getInt("KITAP_ID");

                        Yazar yazar = new Yazar(YAZAR_ID, gelenYazarAd, gelenYazarSoyad, resim_url);
                        Kitap kitap = new Kitap(KITAP_ID, KITAP_ADI, yazar);
                        kitapArrayList.add(kitap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                verileriYerlestirKitap(kitapArrayList);


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
                params.put("yazar_adi", gelenYazarAd);
                params.put("yazar_soyadi", gelenYazarSoyad);


                return params;
            }
        };
        Volley.newRequestQueue(YazarActivity.this).add(postStringRequest);


    }

    private void getYazarBilgileriveKitaplari(String gelenYazarAdi) {
        // load image:
        String url = "https://i.pinimg.com/564x/c0/73/69/c0736966d8b5dd522791bea513852ce0.jpg";
        new FetchImage(url).start();

        //
        Toast.makeText(this, "aranıyor: " + gelenYazarAdi, Toast.LENGTH_SHORT).show();
        kitapArrayList.clear();
        // burada DB'den verileri getirmeliyiz.
        kitapAdapter.notifyDataSetChanged();
        kitapArrayList = new ArrayList<>();
        Yazar yazar = new Yazar(1, gelenYazarAdi, gelenYazarSoyad, url);
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
            Toast.makeText(this, "Yazarın kitabı yok", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Yazarın kitapları geldi", Toast.LENGTH_SHORT).show();

        }
    }

    class FetchImage extends Thread {
        String URL;
        Bitmap bitmap;

        FetchImage(String URL) {
            this.URL = URL;
        }

        @Override
        public void run() {

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(YazarActivity.this, "Yükleniyor...", Toast.LENGTH_SHORT).show();

                }
            });

            InputStream inputStream = null;
            try {
                inputStream = new java.net.URL(URL).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }

            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(YazarActivity.this, "Yüklendi!", Toast.LENGTH_SHORT).show();
                    imgPp.setImageBitmap(bitmap);
                }
            });


        }
    }
}