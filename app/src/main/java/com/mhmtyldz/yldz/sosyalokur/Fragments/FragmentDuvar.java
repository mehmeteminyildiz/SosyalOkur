package com.mhmtyldz.yldz.sosyalokur.Fragments;

import android.os.Bundle;
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

import java.util.ArrayList;

public class FragmentDuvar extends Fragment {
    private RecyclerView rv;
    private ArrayList<Duvar> duvarArrayList;
    private DuvarMesajAdapter adapter;
    private TextInputLayout textInputLayoutYorum;
    private TextView tvGununKonusu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_duvar, container, false);
        Toast.makeText(getContext(), "Duvar", Toast.LENGTH_SHORT).show();

        rv = rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        textInputLayoutYorum = rootView.findViewById(R.id.tilEmail);
        tvGununKonusu = rootView.findViewById(R.id.tvGununKonusu);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_recyclerview));
        rv.addItemDecoration(dividerItemDecoration);

        duvarArrayList = new ArrayList<>();

        getKonu();

        textInputLayoutYorum.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertDuvarMesaj();

            }
        });

        return rootView;
    }

    private void insertDuvarMesaj() {
        Toast.makeText(getContext(), "" + textInputLayoutYorum.getEditText().getText().toString().trim(), Toast.LENGTH_SHORT).show();
        Duvar duvar = new Duvar(1, 1, textInputLayoutYorum.getEditText().getText().toString().trim(),
                "18.05.2022", "mhmtyldz", "alien1", 1);
        // duvarMesajKontrol(duvar); // mesaj uygun mu kontrolü
        // uygun ise DB'ye ekle, ekledikten sonra aşağıdaki komutları çalıştır:
        textInputLayoutYorum.getEditText().setText("");
        textInputLayoutYorum.clearFocus();
        duvarArrayList.add(0, duvar);
        adapter.notifyDataSetChanged();
    }

    private void getKonu() {
        // dB'den günün konusunu getir.
        String gununKonusu = "İnsan ne ile yaşar?";
        tvGununKonusu.setText(gununKonusu);


        getDuvarMessages();

    }

    private void getDuvarMessages() {
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
