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

import com.mhmtyldz.yldz.sosyalokur.Adapters.DuvarMesajAdapter;
import com.mhmtyldz.yldz.sosyalokur.Adapters.OkumaListesiAdapter;
import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Duvar;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Kitap;

import java.util.ArrayList;

public class FragmentDuvar extends Fragment {
    private RecyclerView rv;
    private ArrayList<Duvar> duvarArrayList;
    private DuvarMesajAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_duvar, container, false);
        Toast.makeText(getContext(), "Duvar", Toast.LENGTH_SHORT).show();

        rv = rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_recyclerview));
        rv.addItemDecoration(dividerItemDecoration);

        duvarArrayList = new ArrayList<>();
        duvarMesajlariniYukle();

        return rootView;
    }

    private void duvarMesajlariniYukle() {
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
                "mhmtyldz", "alien1", 1));duvarArrayList.add(new Duvar(1, 1, mesaj, "20.04.2022",
                "mhmtyldz", "alien1", 1));
        duvarArrayList.add(new Duvar(1, 1, mesaj, "20.04.2022",
                "mhmtyldz", "alien1", 1));


        rv.setAdapter(null);
        adapter = new DuvarMesajAdapter(getContext(), duvarArrayList);
        rv.setAdapter(adapter);


    }
}
