package com.mhmtyldz.yldz.sosyalokur.Fragments;

import android.os.Bundle;
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

import com.mhmtyldz.yldz.sosyalokur.Adapters.AlintiAdapter;
import com.mhmtyldz.yldz.sosyalokur.Adapters.OkumaListesiAdapter;
import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Alinti;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Kitap;

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
        okumaListesiniGoster();

        return rootView;
    }

    private void okumaListesiniGoster() {
        kitapArrayList.add(new Kitap(1, "Nutuk", 1, "Mustafa Kemal Atatürk"));
        kitapArrayList.add(new Kitap(1, "Türklerin Tarihi - 1", 1, "İlber Ortaylı"));
        kitapArrayList.add(new Kitap(1, "İrade Terbiyesi - 1", 1, "Jules Payot"));
        kitapArrayList.add(new Kitap(1, "Bir Ömür Nasıl Yaşanır", 1, "Doğan Cüceloğlu"));

        kitapArrayList.add(new Kitap(1, "Nutuk", 1, "Mustafa Kemal Atatürk"));
        kitapArrayList.add(new Kitap(1, "Türklerin Tarihi - 1", 1, "İlber Ortaylı"));
        kitapArrayList.add(new Kitap(1, "İrade Terbiyesi - 1", 1, "Jules Payot"));
        kitapArrayList.add(new Kitap(1, "Bir Ömür Nasıl Yaşanır", 1, "Doğan Cüceloğlu"));

        kitapArrayList.add(new Kitap(1, "Nutuk", 1, "Mustafa Kemal Atatürk"));
        kitapArrayList.add(new Kitap(1, "Türklerin Tarihi - 1", 1, "İlber Ortaylı"));
        kitapArrayList.add(new Kitap(1, "İrade Terbiyesi - 1", 1, "Jules Payot"));
        kitapArrayList.add(new Kitap(1, "Bir Ömür Nasıl Yaşanır", 1, "Doğan Cüceloğlu"));

        kitapArrayList.add(new Kitap(1, "Nutuk", 1, "Mustafa Kemal Atatürk"));
        kitapArrayList.add(new Kitap(1, "Türklerin Tarihi - 1", 1, "İlber Ortaylı"));
        kitapArrayList.add(new Kitap(1, "İrade Terbiyesi - 1", 1, "Jules Payot"));
        kitapArrayList.add(new Kitap(1, "Bir Ömür Nasıl Yaşanır", 1, "Doğan Cüceloğlu"));

        rv.setAdapter(null);
        adapter = new OkumaListesiAdapter(getContext(), kitapArrayList);
        rv.setAdapter(adapter);
    }
}
