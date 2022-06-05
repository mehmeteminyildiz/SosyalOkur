package com.mhmtyldz.yldz.sosyalokur.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;
import com.mhmtyldz.yldz.sosyalokur.Adapters.AraKitapAdapter;
import com.mhmtyldz.yldz.sosyalokur.Adapters.AraYazarAdapter;
import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Kitap;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Yazar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentAra extends Fragment {

    private boolean secim = false; // false: kitap  true: yazar

    private Switch mySwitch;

    private TextInputLayout tilAramaEkrani;
    private RecyclerView rvKitap, rvYazar;
    private EditText tietAramaEkrani;
    private ArrayList<Kitap> kitapArrayList;
    private ArrayList<Yazar> yazarArrayList;
    private AraKitapAdapter kitapAdapter;
    private AraYazarAdapter yazarAdapter;
    private TextView tvKitap, tvYazar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ara, container, false);

        mySwitch = rootView.findViewById(R.id.mySwitch);
        tilAramaEkrani = rootView.findViewById(R.id.tilAramaEkrani);
        tietAramaEkrani = rootView.findViewById(R.id.tietAramaEkrani);
        rvYazar = rootView.findViewById(R.id.rvYazar);
        rvKitap = rootView.findViewById(R.id.rvKitap);
        tvKitap = rootView.findViewById(R.id.tvKitap);
        tvYazar = rootView.findViewById(R.id.tvYazar);


        tvKitap.setTextColor(getResources().getColor(R.color.black));

        rvKitap.setHasFixedSize(true);

        rvKitap.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        rvYazar.setHasFixedSize(true);
        rvYazar.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getActivity().getApplicationContext().INPUT_METHOD_SERVICE);

        kitapArrayList = new ArrayList<>();
        yazarArrayList = new ArrayList<>();


        tietAramaEkrani.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //Log.e("TAG", "onTextChanged:" + charSequence.toString().trim()+"." );
                tilAramaEkrani.setErrorEnabled(false);
                tilAramaEkrani.setError(null);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        tilAramaEkrani.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (secim) { // true: yazar aranacak
                    Toast.makeText(getContext(), "Yazar aranacak", Toast.LENGTH_SHORT).show();
                    hideKeyboard(imm);
                    tietAramaEkrani.clearFocus(); // verileri alıp bunları ListView içinde göstermeliyiz.
                    String aramaMetni = tietAramaEkrani.getText().toString().trim();
                    aramaMetni = aramaMetni.replace("'", "`");
                    getYazarlarByYazarAdi(aramaMetni);
                } else {  // false: kitap aranacak
                    Toast.makeText(getContext(), "Kitap aranacak", Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "ICON'dan Ara Tuşu: " + tietAramaEkrani.getText().toString().trim());
                    hideKeyboard(imm);
                    tietAramaEkrani.clearFocus(); // verileri alıp bunları ListView içinde göstermeliyiz.
                    String aramaMetni = tietAramaEkrani.getText().toString().trim();
                    aramaMetni = aramaMetni.replace("'", "`");
                    getKitaplarByKitapAdi(aramaMetni);
                }


            }
        });

        tietAramaEkrani.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (secim) {
                    Toast.makeText(getContext(), "Yazar Aranacak!", Toast.LENGTH_SHORT).show();

                    if (i == EditorInfo.IME_ACTION_SEARCH) {
                        Log.e("TAG", "Keyboard'tan Ara Tuşu: " + tietAramaEkrani.getText().toString().trim());
                        hideKeyboard(imm);
                        tietAramaEkrani.clearFocus(); // verileri alıp bunları ListView içinde göstermeliyiz.
                        String aramaMetni = tietAramaEkrani.getText().toString().trim();
                        aramaMetni = aramaMetni.replace("'", "`");
                        getYazarlarByYazarAdi(aramaMetni);


                        return true;
                    }

                } else {
                    Toast.makeText(getContext(), "Kitap Adranacak!", Toast.LENGTH_SHORT).show();
                    if (i == EditorInfo.IME_ACTION_SEARCH) {
                        Log.e("TAG", "Keyboard'tan Ara Tuşu: " + tietAramaEkrani.getText().toString().trim());
                        hideKeyboard(imm);
                        tietAramaEkrani.clearFocus(); // verileri alıp bunları ListView içinde göstermeliyiz.
                        String aramaMetni = tietAramaEkrani.getText().toString().trim();
                        aramaMetni = aramaMetni.replace("'", "`");
                        getKitaplarByKitapAdi(aramaMetni);

                        return true;
                    }
                }

                return false;
            }
        });


        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getContext(), "is checked: " + isChecked, Toast.LENGTH_SHORT).show();
                if (isChecked) { // Yazar seçili
                    //rv.setAdapter(yazarAdapter);
                    rvYazar.setVisibility(View.VISIBLE);
                    rvKitap.setVisibility(View.GONE);
                    tvKitap.setTextColor(getResources().getColor(R.color.secili_olmayan_yazi));
                    tvYazar.setTextColor(getResources().getColor(R.color.secili_yazi));
                    secim = true;
                } else { // Kitap Seçili
                    //rv.setAdapter(kitapAdapter);
                    rvYazar.setVisibility(View.GONE);
                    rvKitap.setVisibility(View.VISIBLE);
                    tvKitap.setTextColor(getResources().getColor(R.color.secili_yazi));
                    tvYazar.setTextColor(getResources().getColor(R.color.secili_olmayan_yazi));
                    secim = false;
                }
            }
        });

        kitapAdapter = new AraKitapAdapter(getActivity().getApplicationContext(), kitapArrayList);
        yazarAdapter = new AraYazarAdapter(getActivity().getApplicationContext(), yazarArrayList);

        rvKitap.setAdapter(kitapAdapter);
        rvYazar.setAdapter(yazarAdapter);

        rvYazar.setVisibility(View.GONE);


        return rootView;
    }

    private void getYazarlarByYazarAdi(String aramaMetni) {
        ArrayList<Yazar> geciciArrayList = new ArrayList<>();

        if (aramaMetni.equals("")) {
            tilAramaEkrani.setError("Bir yazar adı girmelisin!");
        } else {
            tietAramaEkrani.setText("");
            yazarArrayList.clear();
        }

        String url = "https://mehmetemin.xyz/sosyalOkur/getYazarFromYazarAdiOrSoyadi.php";

        StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray yazarlar = jsonObject.getJSONArray("yazarlar");


                    for (int i = 0; i < yazarlar.length(); i++) {  // kullanici_id alınır
                        JSONObject k = yazarlar.getJSONObject(i);
                        String yazar_ad = k.getString("AD");
                        String yazar_soyad = k.getString("SOYAD");
                        int id = k.getInt("ID");
                        String yazar_resim_url = k.getString("YAZAR_RESIM_URL");
                        geciciArrayList.add(new Yazar(id, yazar_ad, yazar_soyad, yazar_resim_url));
                    }

                    yazarArrayList.addAll(geciciArrayList);

                    yazarAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                params.put("parametre", aramaMetni);

                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(postStringRequest);
    }

    private void getKitaplarByKitapAdi(String aramaMetni) {
        ArrayList<Kitap> geciciArrayList = new ArrayList<>();


        if (aramaMetni.equals("")) {
            tilAramaEkrani.setError("Bir kitap adı girmelisin!");
        } else {
            tietAramaEkrani.setText("");
            kitapArrayList.clear();
        }

        String url = "https://mehmetemin.xyz/sosyalOkur/getKitapFromKitapAdi.php";

        StringRequest postStringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray kitaplar = jsonObject.getJSONArray("kitaplar");


                    for (int i = 0; i < kitaplar.length(); i++) {  // kullanici_id alınır
                        JSONObject k = kitaplar.getJSONObject(i);
                        int KITAP_ID = k.getInt("KITAP_ID");
                        String KITAP_ADI = k.getString("KITAP_ADI");
                        String KITAP_OZET = k.getString("KITAP_OZET");

                        int YAZAR_ID = k.getInt("YAZAR_ID");
                        String YAZAR_RESIM_URL = k.getString("YAZAR_RESIM_URL");
                        String YAZAR_ADI = k.getString("YAZAR_ADI");
                        String YAZAR_SOYADI = k.getString("YAZAR_SOYADI");
                        Yazar yazar = new Yazar(YAZAR_ID, YAZAR_ADI, YAZAR_SOYADI, YAZAR_RESIM_URL);

                        geciciArrayList.add(new Kitap(KITAP_ID, KITAP_ADI, yazar));
                    }

                    kitapArrayList.addAll(geciciArrayList);

                    kitapAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                params.put("kitap_adi", aramaMetni);

                return params;
            }
        };
        Volley.newRequestQueue(getContext()).add(postStringRequest);
    }




    private void hideKeyboard(InputMethodManager imm) {
        imm.hideSoftInputFromWindow(tietAramaEkrani.getWindowToken(), 0);

    }
}
