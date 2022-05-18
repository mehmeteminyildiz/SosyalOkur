package com.mhmtyldz.yldz.sosyalokur.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.mhmtyldz.yldz.sosyalokur.Adapters.OkumaListesiAdapter;
import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Kitap;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Yazar;

import java.util.ArrayList;

public class FragmentOkumaListesi extends Fragment {

    private RecyclerView rv;
    private ArrayList<Kitap> kitapArrayList;
    private OkumaListesiAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_okuma_listesi, container, false);
        Toast.makeText(getContext(), "Okuma listesi", Toast.LENGTH_SHORT).show();

        rv = rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_recyclerview));
        rv.addItemDecoration(dividerItemDecoration);

        kitapArrayList = new ArrayList<>();
        getOkumaListesi();

        return rootView;
    }

    private void getOkumaListesi() {
        // burada volley ile DB'den nesneleri alır ve gerekli şekilde işleriz.
        // en sonda da listemizi yerleştiririz.
        Log.e("TAG", "getOkumaListesi");
        Yazar yazar = new Yazar(1, "İlber", "Ortaylı");
        kitapArrayList.add(new Kitap(1, "Türklerin Tarihi", yazar));
        kitapArrayList.add(new Kitap(1, "Nutuk", yazar));
        kitapArrayList.add(new Kitap(1, "C# Dersleri", yazar));
        kitapArrayList.add(new Kitap(1, "30 Adımda Özgüven", yazar));
        kitapArrayList.add(new Kitap(1, "Türklerin Tarihi", yazar));
        kitapArrayList.add(new Kitap(1, "Türklerin Tarihi", yazar));
        kitapArrayList.add(new Kitap(1, "Türklerin Tarihi", yazar));
        kitapArrayList.add(new Kitap(1, "Türklerin Tarihi", yazar));
        kitapArrayList.add(new Kitap(1, "Türklerin Tarihi", yazar));
        kitapArrayList.add(new Kitap(1, "Türklerin Tarihi", yazar));
        kitapArrayList.add(new Kitap(1, "Türklerin Tarihi", yazar));


        verileriYerlestir();
    }

    private void verileriYerlestir() {
        Log.e("TAG", "verileriYerlestir: ");
        rv.setAdapter(null);
        adapter = new OkumaListesiAdapter(getContext(), kitapArrayList);
        rv.setAdapter(adapter);
    }
}
