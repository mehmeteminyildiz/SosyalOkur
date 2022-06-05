package com.mhmtyldz.yldz.sosyalokur.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mhmtyldz.yldz.sosyalokur.Adapters.AlintiAdapter;
import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Alinti;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Duvar;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Kitap;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Yazar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private String gelenKullaniciAdi;
    private TextView tvKullaniciAdi;
    private CardView cardView;
    private ImageView imgPp;
    private TextView tvAlintiSayisi;
    private Button btnAlintilariGoster;
    private RecyclerView rv;

    private ImageButton imgBack;


    private ArrayList<Alinti> alintiArrayList;
    private AlintiAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tasarimNesneleriniBaslat();


    }

    private void gelenKullaniciAdiniAl() {
        // gelen kullaniciAdi değerini alalım:
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        gelenKullaniciAdi = bundle.getString("kullanici_adi");
        tvKullaniciAdi.setText(gelenKullaniciAdi);

        getForProfile();


    }

    private void getForProfile() {
        alintiArrayList = new ArrayList<>();

        SharedPreferences sp = getSharedPreferences("girisBilgileri", MODE_PRIVATE);
        String email = sp.getString("email_adresi", ""); // email DB'ye gönderilecek

        String url = "https://mehmetemin.xyz/sosyalOkur/getForProfile.php";
        StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray ALINTILAR = jsonObject.getJSONArray("alintilar"); // tablo adı

                    int alintiSayisi = jsonObject.getJSONArray("alintilar").getJSONObject(0).getInt("ALINTI_SAYISI");
                    String alinti_yazisi = "Toplam <b>" + alintiSayisi + "</b>" + " alıntı paylaştı";
                    tvAlintiSayisi.setText(Html.fromHtml(alinti_yazisi));
                    String imageName = jsonObject.getJSONArray("alintilar").getJSONObject(0).getString("RESIM_ADI");

                    imgPp.setImageResource(getResources()
                            .getIdentifier(imageName, "drawable", getPackageName()));
                    for (int i = 0; i < ALINTILAR.length(); i++) {
                        JSONObject alintilarjsonObject = ALINTILAR.getJSONObject(i);


                        int ALINTI_ID = alintilarjsonObject.getInt("ALINTI_ID");
                        int KULLANICI_ID = alintilarjsonObject.getInt("KULLANICI_ID");
                        int KITAP_ID = alintilarjsonObject.getInt("KITAP_ID");
                        String ALINTI_METNI = alintilarjsonObject.getString("ALINTI_METNI");
                        String ALINTI_BASLIGI = alintilarjsonObject.getString("ALINTI_BASLIGI");
                        String ALINTI_TARIHI = alintilarjsonObject.getString("ALINTI_TARIHI");
                        String KULLANICI_ADI = alintilarjsonObject.getString("KULLANICI_ADI");
                        String KITAP_ADI = alintilarjsonObject.getString("KITAP_ADI");
                        String YAZAR_SOYADI = alintilarjsonObject.getString("YAZAR_SOYADI");
                        String YAZAR_ADI = alintilarjsonObject.getString("YAZAR_ADI");
                        String PIC_NAME = alintilarjsonObject.getString("PIC_NAME");
                        int YAZAR_ID = alintilarjsonObject.getInt("YAZAR_ID");
                        String YAZAR_RESIM_URL = alintilarjsonObject.getString("YAZAR_RESIM_URL");
                        ;

                        Yazar yazar = new Yazar(YAZAR_ID, YAZAR_ADI, YAZAR_SOYADI, YAZAR_RESIM_URL);
                        Kitap kitap = new Kitap(KITAP_ID, KITAP_ADI, yazar);

                        Alinti alinti = new Alinti(ALINTI_ID, KULLANICI_ID, KULLANICI_ADI, PIC_NAME,
                                kitap, ALINTI_METNI, ALINTI_BASLIGI, ALINTI_TARIHI);


                        alintiArrayList.add(alinti);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                verileriYerlestir(alintiArrayList);

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
                params.put("username", gelenKullaniciAdi);


                return params;
            }
        };
        Volley.newRequestQueue(ProfileActivity.this).add(postStringRequest);


    }


    private void verileriYerlestir(ArrayList<Alinti> alintiArrayList) {
        ////Log.e("TAG", "verileriYerlestir: " + paylasimArrayList);
        rv.setAdapter(null);
        // Collections.reverse(paylasimArrayList); bu şekilde yeniye doğru sıralamak yanlış bence.
        // Onun yerine MySQL'den çekerken order by kullanmalıyız.
        adapter = new AlintiAdapter(getApplicationContext(), alintiArrayList);
        rv.setAdapter(adapter);
        //progressBar.setVisibility(View.INVISIBLE);


    }

    private void tasarimNesneleriniBaslat() {
        tvKullaniciAdi = findViewById(R.id.tvKullaniciAdi);
        rv = findViewById(R.id.rv);
        btnAlintilariGoster = findViewById(R.id.btnAlintilariGoster);
        cardView = findViewById(R.id.cardView);
        tvAlintiSayisi = findViewById(R.id.tvAlintiSayisi);
        //tvOkumaListesiSayisi = findViewById(R.id.tvOkumaListesiSayisi);
        //tvOkuduguSayisi = findViewById(R.id.tvOkuduguSayisi);
        imgPp = findViewById(R.id.imgPp);
        imgBack = findViewById(R.id.imgBack);

        setListeners();


    }

    private void setListeners() {
        btnAlintilariGoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rv.getVisibility() == View.VISIBLE) {
                    btnAlintilariGoster.setText("Alıntıları Göster");

                    rv.setVisibility(View.GONE);
                    cardView.setVisibility(View.VISIBLE);

                } else if (rv.getVisibility() == View.GONE) {
                    btnAlintilariGoster.setText("Alıntıları Gizle");


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
        setUpRv();
    }

    private void setUpRv() {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_recyclerview));
        rv.addItemDecoration(dividerItemDecoration);

        gelenKullaniciAdiniAl();


    }
}