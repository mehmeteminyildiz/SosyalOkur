package com.mhmtyldz.yldz.sosyalokur.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.mhmtyldz.yldz.sosyalokur.Activities.LoginActivity;
import com.mhmtyldz.yldz.sosyalokur.Adapters.DuvarMesajAdapter;
import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Alinti;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Duvar;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Kitap;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Yazar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FragmentDuvar extends Fragment {
    private RecyclerView rv;
    private ArrayList<Duvar> duvarArrayList;
    private DuvarMesajAdapter adapter;
    private TextInputLayout tilDuvarMesaji;
    private TextView tvGununKonusu;

    private int konu_id;
    String konu_metni;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_duvar, container, false);
        Toast.makeText(getContext(), "Duvar", Toast.LENGTH_SHORT).show();


        tasarimNesneleriniBaslat(rootView);

        duvarArrayList = new ArrayList<>();

        getKonuVeDuvarMessges();

        return rootView;
    }

    private void tasarimNesneleriniBaslat(View rootView) {
        tilDuvarMesaji = rootView.findViewById(R.id.tilDuvarMesaji);
        tvGununKonusu = rootView.findViewById(R.id.tvGununKonusu);
        setUpRv(rootView);
    }

    private void setUpRv(View rootView) {
        rv = rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL);
        //dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_recyclerview));
        //rv.addItemDecoration(dividerItemDecoration);

        setListeners();
    }

    private void setListeners() {
        tilDuvarMesaji.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertForYorumYapActivity();
                //insertDuvarMesaj();
            }
        });

        tilDuvarMesaji.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tilDuvarMesaji.setError(null);
                tilDuvarMesaji.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void insertDuvarMesaj() {
        Toast.makeText(getContext(), "" + tilDuvarMesaji.getEditText().getText().toString().trim(), Toast.LENGTH_SHORT).show();
        String tarih = simdikiZaman();
        SharedPreferences sp = getActivity().getSharedPreferences("girisBilgileri", Context.MODE_PRIVATE);
        String email = sp.getString("email_adresi", "");

        String duvarMesaji = tilDuvarMesaji.getEditText().getText().toString().trim();
        if (duvarMesaji.isEmpty()) {
            tilDuvarMesaji.setError("Bir mesaj girmelisin");
        } else {
            // email kullanılarak KULLANICI ADI getirilir ve ayrıca DB'ye Duvar Mesajı eklenir sonra arayüze yansıtılır.
            Duvar duvar = new Duvar(1, 1, duvarMesaji,
                    tarih, "kullanici_adi", "alien1", 1);
            // duvarMesajKontrol(duvar); // mesaj uygun mu kontrolü
            // uygun ise DB'ye ekle, ekledikten sonra aşağıdaki komutları çalıştır:
            tilDuvarMesaji.getEditText().setText("");
            tilDuvarMesaji.clearFocus();
            duvarArrayList.add(0, duvar);
            adapter.notifyDataSetChanged();
        }
    }

    private void insertForYorumYapActivity() {
        SharedPreferences sp = getActivity().getSharedPreferences("girisBilgileri", Context.MODE_PRIVATE);
        String email = sp.getString("email_adresi", "");
        String kullanici_adi = sp.getString("kullanici_adi", "");

        String duvarMesaji = tilDuvarMesaji.getEditText().getText().toString().trim();
        if (duvarMesaji.isEmpty()) {
            tilDuvarMesaji.setError("Bir mesaj girmelisin");
        } else {
            // email kullanılarak KULLANICI ADI getirilir ve ayrıca DB'ye Duvar Mesajı eklenir sonra arayüze yansıtılır.
            Duvar duvar = new Duvar(1, 1, duvarMesaji,
                    simdikiZaman(), kullanici_adi, "alien1", konu_id);

            tilDuvarMesaji.getEditText().setText("");
            tilDuvarMesaji.clearFocus();
            duvarArrayList.add(0, duvar);
            adapter.notifyDataSetChanged();

            String url = "https://mehmetemin.xyz/sosyalOkur/insertDuvarMesaj.php";

            StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("TAG", "Cevap geldi:" + response.trim());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Toast.makeText(RegisterActivity.this, "Hata: " + error.toString(), Toast.LENGTH_SHORT).show();
                    //Log.e("TAG", "onErrorResponse:-" + error.toString().trim() + "-");
                }
            }) {
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("email", email);
                    params.put("duvar_mesaji", duvarMesaji);
                    params.put("konu_id", String.valueOf(konu_id));


                    return params;
                }
            };
            Volley.newRequestQueue(getContext()).add(postStringRequest);
        }




    }

    private String simdikiZaman() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    private void getKonuVeDuvarMessges() {
        duvarArrayList = new ArrayList<>();

        String url = "https://mehmetemin.xyz/sosyalOkur/getForDuvar.php";
        StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray duvar_mesajlari = jsonObject.getJSONArray("duvar_mesajlari");

                    konu_id = duvar_mesajlari.getJSONObject(0).getInt("KONU_ID");
                    konu_metni = duvar_mesajlari.getJSONObject(0).getString("KONU_METNI");

                    for (int j = 0; j < duvar_mesajlari.length(); j++) {
                        JSONObject dm = duvar_mesajlari.getJSONObject(j);

                        int DUVAR_MESAJ_ID = dm.getInt("DUVAR_MESAJ_ID");
                        int KULLANICI_ID = dm.getInt("KULLANICI_ID");
                        String MESAJ = dm.getString("MESAJ");
                        String TARIH = dm.getString("TARIH");
                        int KONU_ID = dm.getInt("KONU_ID");
                        String KONU_METNI = dm.getString("KONU_METNI");
                        String KULLANICI_ADI = dm.getString("KULLANICI_ADI");
                        String RESIM_ADI = dm.getString("RESIM_ADI");

                        Duvar duvar = new Duvar(DUVAR_MESAJ_ID, KULLANICI_ID, MESAJ, TARIH,
                                KULLANICI_ADI, RESIM_ADI, KONU_ID);

                        duvarArrayList.add(duvar);
                    }
                    //
                } catch (Exception e) {
                    e.printStackTrace();
                }


                String haftanin_konusu_yazisi = "<b>Haftanın Konusu:</b> " + konu_metni;
                tvGununKonusu.setText(Html.fromHtml(haftanin_konusu_yazisi));

                duvarMesajlariniAraYuzeYerlestir(rv, duvarArrayList);


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

                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(postStringRequest);


    }

    private void getKonu() {
        // dB'den haftanın konusunu getir.
        String haftaninKonusu = "İnsan ne ile yaşar?";
        String gununKonusu_yazisi = "<b>Haftanın Konusu:</b> " + haftaninKonusu;
        tvGununKonusu.setText(Html.fromHtml(gununKonusu_yazisi));

        konu_id = 1;

        String tarih = getTarih();
        Toast.makeText(getContext(), "Tarih: " + tarih, Toast.LENGTH_SHORT).show();

        getDuvarMessages();
    }

    private String getTarih() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    private void getDuvarMessages() {
        // DB'den o haftaya ait Duvar Mesajları getirilsin.
        String mesaj = "Merhaba bence uygulama çok güzel olmuş. Uzun zamandır bu kadar " +
                "zevk aldığım ve bilgi edindiğim bir uygulama olmamıştı.";
        duvarArrayList.add(new Duvar(1, 1, mesaj, "20.04.2022",
                "mhmtyldz", "alien1", 1));
        duvarArrayList.add(new Duvar(1, 1, mesaj, "20.04.2022",
                "mhmtyldz", "alien1", 1));
        duvarArrayList.add(new Duvar(1, 1, mesaj, "20.04.2022",
                "mhmtyldz", "alien1", 1));
        duvarArrayList.add(new Duvar(1, 1, mesaj, "20.04.2022",
                "mhmtyldz", "alien1", 1));
        duvarArrayList.add(new Duvar(1, 1, mesaj, "20.04.2022",
                "mhmtyldz", "alien1", 1));
        duvarArrayList.add(new Duvar(1, 1, mesaj, "20.04.2022",
                "mhmtyldz", "alien1", 1));
        duvarArrayList.add(new Duvar(1, 1, mesaj, "20.04.2022",
                "mhmtyldz", "alien1", 1));
        duvarArrayList.add(new Duvar(1, 1, mesaj, "20.04.2022",
                "mhmtyldz", "alien1", 1));



    }

    private void duvarMesajlariniAraYuzeYerlestir(RecyclerView rv, ArrayList<Duvar> duvarArrayList) {
        rv.setAdapter(null);
        adapter = new DuvarMesajAdapter(getContext(), duvarArrayList);
        rv.setAdapter(adapter);
    }
}
