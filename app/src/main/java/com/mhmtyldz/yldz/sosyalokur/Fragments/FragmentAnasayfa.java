package com.mhmtyldz.yldz.sosyalokur.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class FragmentAnasayfa extends Fragment {

    private RecyclerView rv;
    private ArrayList<Alinti> alintiArrayList;
    private AlintiAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_anasayfa, container, false);

        rv = rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_recyclerview));
        rv.addItemDecoration(dividerItemDecoration);

        getAlintilar();

        return rootView;
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
                        String YAZAR_RESIM_URL = "YAZAR_RESIM_URL";

                        Yazar yazar = new Yazar(YAZAR_ID, YAZAR_ADI, YAZAR_SOYADI, YAZAR_RESIM_URL);
                        Kitap kitap = new Kitap(KITAP_ID, KITAP_ADI, yazar);

                        Alinti alinti = new Alinti(ALINTI_ID, KULLANICI_ID, KULLANICI_ADI, PIC_NAME,
                                kitap, ALINTI_METNI, ALINTI_BASLIGI, ALINTI_TARIHI);


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
        rv.setAdapter(null);
        adapter = new AlintiAdapter(getContext(), alintiArrayList);
        rv.setAdapter(adapter);


    }
}
