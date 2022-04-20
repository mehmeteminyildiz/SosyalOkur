package com.mhmtyldz.yldz.sosyalokur.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.mhmtyldz.yldz.sosyalokur.Adapters.AlintiAdapter;
import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Alinti;

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

        alintiArrayList = new ArrayList<>();
        alintilariGoster();


        return rootView;
    }

    private void alintilariGoster() {
        String alintiMetni = "\"Bizim gibi sevgililer,hiçbir şeyin aşklarını bitiremeyeceğini" +
                " bildikleri için en kötü günlerinde bile, hatta birbirlerine en acımasız" +
                " ve yanlış şeyleri istemeden yaparlarken bile, içlerinde hiç bitmeyen bir" +
                " teselli duygusu taşırlar.\"";
        alintiArrayList.add(new Alinti(1, 1, 1, alintiMetni, "Baslik", "17.04.2022",
                "alien1", "mhmtyldz", "Nutuk", "Mustafa Kemal Ataturk"));
        alintiArrayList.add(new Alinti(1, 1, 1, alintiMetni, "Baslik", "17.04.2022",
                "alien1", "mhmtyldz", "Nutuk", "Mustafa Kemal Ataturk"));
        alintiArrayList.add(new Alinti(1, 1, 1, alintiMetni, "Baslik", "17.04.2022",
                "alien1", "mhmtyldz", "Nutuk", "Mustafa Kemal Ataturk"));
        alintiArrayList.add(new Alinti(1, 1, 1, alintiMetni, "Baslik", "17.04.2022",
                "alien1", "mhmtyldz", "Nutuk", "Mustafa Kemal Ataturk"));
        alintiArrayList.add(new Alinti(1, 1, 1, alintiMetni, "Baslik", "17.04.2022",
                "alien1", "mhmtyldz", "Nutuk", "Mustafa Kemal Ataturk"));
        alintiArrayList.add(new Alinti(1, 1, 1, alintiMetni, "Baslik", "17.04.2022",
                "alien1", "mhmtyldz", "Nutuk", "Mustafa Kemal Ataturk"));
        alintiArrayList.add(new Alinti(1, 1, 1, alintiMetni, "Baslik", "17.04.2022",
                "alien1", "mhmtyldz", "Nutuk", "Mustafa Kemal Ataturk"));

        rv.setAdapter(null);
        adapter = new AlintiAdapter(getContext(), alintiArrayList);
        rv.setAdapter(adapter);


    }
}
