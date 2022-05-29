package com.mhmtyldz.yldz.sosyalokur.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
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

import com.google.android.material.textfield.TextInputLayout;
import com.mhmtyldz.yldz.sosyalokur.Adapters.DuvarMesajAdapter;
import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Duvar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FragmentDuvar extends Fragment {
    private RecyclerView rv;
    private ArrayList<Duvar> duvarArrayList;
    private DuvarMesajAdapter adapter;
    private TextInputLayout tilDuvarMesaji;
    private TextView tvGununKonusu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_duvar, container, false);
        Toast.makeText(getContext(), "Duvar", Toast.LENGTH_SHORT).show();

        tasarimNesneleriniBaslat(rootView);

        duvarArrayList = new ArrayList<>();

        getKonu();

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
                insertDuvarMesaj();
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

    private String simdikiZaman() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    private void getKonu() {
        // dB'den haftanın konusunu getir.
        String haftaninKonusu = "İnsan ne ile yaşar?";
        String gununKonusu_yazisi = "<b>Haftanın Konusu:</b> " + haftaninKonusu;
        tvGununKonusu.setText(Html.fromHtml(gununKonusu_yazisi));

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

        duvarMesajlariniAraYuzeYerlestir(rv, duvarArrayList);


    }

    private void duvarMesajlariniAraYuzeYerlestir(RecyclerView rv, ArrayList<Duvar> duvarArrayList) {
        rv.setAdapter(null);
        adapter = new DuvarMesajAdapter(getContext(), duvarArrayList);
        rv.setAdapter(adapter);
    }
}
