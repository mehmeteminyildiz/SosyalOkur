package com.mhmtyldz.yldz.sosyalokur.Fragments;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

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

public class FragmentMyProfile extends Fragment {

    private RecyclerView rv;
    private ArrayList<Alinti> alintiArrayList;
    private AlintiAdapter adapter;
    private Button btnAlintilariGoster;
    private CardView cardView;
    private TextView tvAlintiSayisi, tvOkumaListesiSayisi, tvOkuduguSayisi;
    private TextView textViewProfilKullaniciAdi;
    private ImageView imgLogout, imgPp;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_profile, container, false);

        tasarimNesneleriniBaslat(rootView);

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

        imgLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Çıkış yapılıyor...", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

    private void getKullaniciProfili() {
        // burada DB'den kullanıcı bilgileri getirilir. ve arayüze yansıtılır
        String imageName = "alien2";
        String kullanici_adi = "mehmetemin_yildiz";
        int alinti_sayisi = 15;
        int okuma_listesi_sayisi = 59;
        int okudugu_kitap_sayisi = 5;

        String alinti_yazisi = "<b>" + alinti_sayisi + "</b>" + " alıntı paylaştı";
        String okuma_listesi_yazisi = "Okuma listesinde <b>" + okuma_listesi_sayisi + "</b>" + " kitap var";
        String okudugu_kitap_sayisi_yazisi = "Bugüne kadar <b>" + okudugu_kitap_sayisi + "</b>" + " kitap okudu";

        tvAlintiSayisi.setText(Html.fromHtml(alinti_yazisi));
        tvOkumaListesiSayisi.setText(Html.fromHtml(okuma_listesi_yazisi));
        tvOkuduguSayisi.setText(Html.fromHtml(okudugu_kitap_sayisi_yazisi));
        imgPp.setImageResource(getActivity().getResources()
                .getIdentifier(imageName, "drawable", getActivity().getPackageName()));


        getAlintilar();

    }

    private void tasarimNesneleriniBaslat(View rootView) {
        rv = rootView.findViewById(R.id.rv);
        btnAlintilariGoster = rootView.findViewById(R.id.btnAlintilariGoster);
        cardView = rootView.findViewById(R.id.cardView);
        tvAlintiSayisi = rootView.findViewById(R.id.tvAlintiSayisi);
        tvOkumaListesiSayisi = rootView.findViewById(R.id.tvOkumaListesiSayisi);
        tvOkuduguSayisi = rootView.findViewById(R.id.tvOkuduguSayisi);
        imgLogout = rootView.findViewById(R.id.imgLogout);
        imgPp = rootView.findViewById(R.id.imgPp);
        textViewProfilKullaniciAdi = rootView.findViewById(R.id.textViewProfilKullaniciAdi);

        setUpRv();

    }

    private void setUpRv() {
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_recyclerview));
        rv.addItemDecoration(dividerItemDecoration);

        getKullaniciProfili();

    }

    private void getAlintilar() {

        alintiArrayList = new ArrayList<>();

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

        Volley.newRequestQueue(getActivity().getApplicationContext()).add(stringRequest);
    }

    private void verileriYerlestir(ArrayList<Alinti> alintiArrayList) {
        ////Log.e("TAG", "verileriYerlestir: " + paylasimArrayList);
        rv.setAdapter(null);
        // Collections.reverse(paylasimArrayList); bu şekilde yeniye doğru sıralamak yanlış bence.
        // Onun yerine MySQL'den çekerken order by kullanmalıyız.
        adapter = new AlintiAdapter(getContext(), alintiArrayList);
        rv.setAdapter(adapter);
        //progressBar.setVisibility(View.INVISIBLE);


    }
}
