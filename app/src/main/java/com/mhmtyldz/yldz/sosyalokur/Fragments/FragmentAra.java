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

import com.google.android.material.textfield.TextInputLayout;
import com.mhmtyldz.yldz.sosyalokur.Adapters.AraKitapAdapter;
import com.mhmtyldz.yldz.sosyalokur.Adapters.AraYazarAdapter;
import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Kitap;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Yazar;

import java.util.ArrayList;

public class FragmentAra extends Fragment {

    private boolean secim = false; // false: kitap  true: yazar

    private Switch mySwitch;

    private TextInputLayout tilAramaEkrani;
    private RecyclerView rv;
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
        rv = rootView.findViewById(R.id.rv);
        tvKitap = rootView.findViewById(R.id.tvKitap);
        tvYazar = rootView.findViewById(R.id.tvYazar);


        tvKitap.setTextColor(getResources().getColor(R.color.black));

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
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
                    tvKitap.setTextColor(getResources().getColor(R.color.secili_olmayan_yazi));
                    tvYazar.setTextColor(getResources().getColor(R.color.secili_yazi));
                    secim = true;
                } else { // Kitap Seçili
                    tvKitap.setTextColor(getResources().getColor(R.color.secili_yazi));
                    tvYazar.setTextColor(getResources().getColor(R.color.secili_olmayan_yazi));
                    secim = false;
                }
            }
        });

        kitapAdapter = new AraKitapAdapter(getActivity().getApplicationContext(), kitapArrayList);
        yazarAdapter = new AraYazarAdapter(getActivity().getApplicationContext(), yazarArrayList);

        getPopulerKitaplar();

        return rootView;
    }

    private void getPopulerKitaplar() {
        kitapArrayList.clear();
        // burada DB'den verileri getirmeliyiz.
        kitapAdapter.notifyDataSetChanged();
        kitapArrayList = new ArrayList<>();

        Yazar yazar = new Yazar(1, "Yazar Adı", "Soyadı");
        kitapArrayList.add(new Kitap(1, "Popüler Kitap - 1", yazar));
        kitapArrayList.add(new Kitap(1, "Popüler Kitap - 2", yazar));
        kitapArrayList.add(new Kitap(1, "Popüler Kitap - 3", yazar));
        kitapArrayList.add(new Kitap(1, "Popüler Kitap - 4", yazar));


        verileriYerlestirKitap(kitapArrayList);

    }

    private void getYazarlarByYazarAdi(String aramaMetni) {
        Toast.makeText(getContext(), "Yazar: " + aramaMetni, Toast.LENGTH_SHORT).show();
        if (aramaMetni.equals("")) {
            tilAramaEkrani.setError("Bir yazar adı girmelisin!");
        } else {
            tietAramaEkrani.setText("");
            yazarArrayList.clear();


            // burada DB'den verileri getirmeliyiz.
            yazarAdapter.notifyDataSetChanged();
            yazarArrayList = new ArrayList<>();

            if (aramaMetni.equals("a")) {
                yazarArrayList.add(new Yazar(1, "Jules", "Payot"));
                yazarArrayList.add(new Yazar(1, "Jules", "Payot"));
                yazarArrayList.add(new Yazar(1, "Jules", "Payot"));
                yazarArrayList.add(new Yazar(1, "Jules", "Payot"));
                yazarArrayList.add(new Yazar(1, "Jules", "Payot"));
            } else {

                yazarArrayList.add(new Yazar(1, "A", "B"));
                yazarArrayList.add(new Yazar(1, "A", "B"));
                yazarArrayList.add(new Yazar(1, "A", "B"));
                yazarArrayList.add(new Yazar(1, "A", "B"));
                yazarArrayList.add(new Yazar(1, "A", "B"));
            }


            verileriYerlestirYazar(yazarArrayList);

        }
    }

    private void getKitaplarByKitapAdi(String aramaMetni) {
        if (aramaMetni.equals("")) {
            tilAramaEkrani.setError("Bir yazar adı girmelisin!");
        } else {
            tietAramaEkrani.setText("");
            kitapArrayList.clear();

            // burada DB'den verileri getirmeliyiz.

            kitapAdapter.notifyDataSetChanged();
            kitapArrayList = new ArrayList<>();

            if (aramaMetni.equals("a")) {
                Yazar yazar = new Yazar(1, "İlber", "Ortaylı");
                kitapArrayList.add(new Kitap(1, "Nutuk", yazar));
                kitapArrayList.add(new Kitap(1, "Nutuk", yazar));
                kitapArrayList.add(new Kitap(1, "Nutuk", yazar));
                kitapArrayList.add(new Kitap(1, "Nutuk", yazar));
                kitapArrayList.add(new Kitap(1, "Nutuk", yazar));
            } else {
                Yazar yazar = new Yazar(1, "İlber", "Ortaylı");
                kitapArrayList.add(new Kitap(1, "B", yazar));
                kitapArrayList.add(new Kitap(1, "B", yazar));
                kitapArrayList.add(new Kitap(1, "B", yazar));
                kitapArrayList.add(new Kitap(1, "B", yazar));
                kitapArrayList.add(new Kitap(1, "B", yazar));
            }


            verileriYerlestirKitap(kitapArrayList);

        }
    }

    private void verileriYerlestirKitap(ArrayList<Kitap> kitapArrayList) {
        rv.setAdapter(null);
        kitapAdapter = new AraKitapAdapter(getActivity().getApplicationContext(), kitapArrayList);


        rv.setAdapter(kitapAdapter);

        if (kitapArrayList.size() <= 0) {
            Toast.makeText(getContext(), "Bulunamadı", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Bulundu!", Toast.LENGTH_SHORT).show();

        }
    }

    private void verileriYerlestirYazar(ArrayList<Yazar> yazarArrayList) {
        rv.setAdapter(null);

        yazarAdapter = new AraYazarAdapter(getActivity().getApplicationContext(), yazarArrayList);
        rv.setAdapter(yazarAdapter);

        if (yazarArrayList.size() <= 0) {
            Toast.makeText(getContext(), "Bulunamadı", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Bulundu!", Toast.LENGTH_SHORT).show();

        }
    }

    private void showKeyboard(InputMethodManager imm) {
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    private void hideKeyboard(InputMethodManager imm) {
        imm.hideSoftInputFromWindow(tietAramaEkrani.getWindowToken(), 0);

    }
}
