package com.mhmtyldz.yldz.sosyalokur.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.mhmtyldz.yldz.sosyalokur.Adapters.AlintiAdapter;
import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Alinti;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Kitap;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Yazar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class KitapActivity extends AppCompatActivity {

    private String gelenKitapAdi;
    private TextView tvKitapAdi, tvKitapOzeti, tvYazarAdi;
    private ImageButton imgBack;
    private RecyclerView rv;
    private ImageView imgOkumaListemeEkle;
    private Button btnAlintilariGoster;
    private boolean okumaListesindeVarMi;
    private AlintiAdapter adapter;

    private ArrayList<Alinti> alintiArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitap);
        okumaListesindeVarMi = false;
        alintiArrayList = new ArrayList<>();

        tasarimNesneleriniBaslat();

        // gelen kullaniciAdi değerini alalım:


        imgOkumaListemeEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (okumaListesindeVarMi) {
                    okumaListesindenSil("email_adresi", gelenKitapAdi);
                } else {
                    okumaListesineEkle("email_adresi", gelenKitapAdi);
                }

            }
        });

        tvYazarAdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(KitapActivity.this, YazarActivity.class);
                intent.putExtra("yazar_adi", tvYazarAdi.getText().toString().trim());
                startActivity(intent);
            }
        });

        btnAlintilariGoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rv.getVisibility() == View.GONE) {
                    rv.setVisibility(View.VISIBLE);
                    // ad, ozet, yazar
                    tvYazarAdi.setVisibility(View.GONE);
                    tvKitapAdi.setVisibility(View.GONE);
                    tvKitapOzeti.setVisibility(View.GONE);
                    btnAlintilariGoster.setText("Alıntıları Gizle");
                } else if (rv.getVisibility() == View.VISIBLE) {
                    rv.setVisibility(View.GONE);
                    // ad, ozet, yazar
                    tvYazarAdi.setVisibility(View.VISIBLE);
                    tvKitapAdi.setVisibility(View.VISIBLE);
                    tvKitapOzeti.setVisibility(View.VISIBLE);
                    btnAlintilariGoster.setText("Alıntıları Göster");

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


    private void okumaListesindeVarMiCheck(String gelenKitapAdi) {

        // DB'den okuma listesinde olup olmadığını kontrol et.
        okumaListesindeVarMi = !okumaListesindeVarMi;
        if (okumaListesindeVarMi) {
            imgOkumaListemeEkle.setImageResource(R.drawable.icon_book);
        } else {
            imgOkumaListemeEkle.setImageResource(R.drawable.icon_book_outlined);
        }
        imgOkumaListemeEkle.setEnabled(true);
        getKitapBilgileriByKitapAdi(gelenKitapAdi);


    }

    private void okumaListesineEkle(String email_adresi, String gelenKitapAdi) {
        okumaListesindeVarMi = true;
        imgOkumaListemeEkle.setImageResource(R.drawable.icon_book);
    }

    private void okumaListesindenSil(String email_adresi, String gelenKitapAdi) {
        okumaListesindeVarMi = false;
        imgOkumaListemeEkle.setImageResource(R.drawable.icon_book_outlined);

    }

    private void tasarimNesneleriniBaslat() {
        tvKitapAdi = findViewById(R.id.tvKitapAdi);
        tvKitapOzeti = findViewById(R.id.tvKitapOzeti);
        tvYazarAdi = findViewById(R.id.tvYazarAdi);
        imgBack = findViewById(R.id.imgBack);
        imgOkumaListemeEkle = findViewById(R.id.imgOkumaListemeEkle);
        btnAlintilariGoster = findViewById(R.id.btnAlintilariGoster);
        rv = findViewById(R.id.rv);

        imgOkumaListemeEkle.setEnabled(false);

        setUpRv();
    }

    private void setUpRv() {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_recyclerview));
        rv.addItemDecoration(dividerItemDecoration);

        kitapAdiniYerlestir();

    }

    private void kitapAdiniYerlestir() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        gelenKitapAdi = bundle.getString("kitap_adi");
        tvKitapAdi.setText(gelenKitapAdi);
        okumaListesindeVarMiCheck(gelenKitapAdi);

    }

    private void getKitapBilgileriByKitapAdi(String gelenKitapAdi) {
        // kitap özeti, yazar adı soyadı bilgilerini alalım.
        String kitap_ozeti = "Kitap özet metni burada yer alır. Örneğin:\nİrade Terbiyesi, tüm zamanların en çok farklı dile çevrilen kitapları arasındadır. Yazıldığı dönemde hayli ses getirmiş ve kısa sürede baskı üstüne baskı yapmıştır. Cemil Meriç; “Disiplin içinde çalışmayı bu kitaptan öğrendim” der onun için. Ali Fuat Başgil ise şu sözleriyle onun değerini pekiştirir: “Mösyö Girard bize bir kitap tavsiye etti ve mutlaka okumamızı söyledi. Bu, Aix-Marseille Üniversitesi rektörü Jules Payot’un İrade Terbiyesi adlı kitabıydı. Ertesi gün şehre inerek kitabı aldım, ihtiyar bir meşenin dibine oturarak okumaya koyuldum. Okudukça, içimde özlem ve pişmanlıkla karışık, belli belirsiz bir acı duymaya başladım. Kendi kendime, ah bu kitap on sekiz, yirmi yaşlarımdayken elime geçmeliydi, böyle bir kitabı okumakta geciktiğim için üzülüyordum.” Fransızca aslından yapılan elinizdeki bu özgün ve titiz çevirisiyle İrade Terbiyesi, böylece Türk okurunun hak ettiği niteliğe kavuşmuş oluyor.";
        String yazar_adi = "Yazar Adı";

        tvKitapOzeti.setText(kitap_ozeti.trim());
        tvYazarAdi.setText(yazar_adi);

        getAlintilarByKitapAdi(gelenKitapAdi);

    }

    private void getAlintilarByKitapAdi(String gelenKitapAdi) {

        String url = "https://mehmetemin.xyz/sosyalOkur/getAlintilar.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray ALINTILAR = jsonObject.getJSONArray("alintilar"); // tablo adı

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


                        Yazar yazar = new Yazar(YAZAR_ID, YAZAR_ADI, YAZAR_SOYADI);
                        Kitap kitap = new Kitap(KITAP_ID, KITAP_ADI, yazar);

                        Alinti alinti = new Alinti(ALINTI_ID, KULLANICI_ID, KULLANICI_ADI, PIC_NAME,
                                kitap, ALINTI_METNI, ALINTI_BASLIGI, ALINTI_TARIHI);

                        Log.e("TAG", "Alıntı: " + alinti.getAlinti_resim_ad());

                        alintiArrayList.add(alinti);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                verileriYerlestir(alintiArrayList);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        });

        Volley.newRequestQueue(getApplicationContext()).add(stringRequest);

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
}