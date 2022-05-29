package com.mhmtyldz.yldz.sosyalokur.Adapters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhmtyldz.yldz.sosyalokur.Activities.KitapActivity;
import com.mhmtyldz.yldz.sosyalokur.Activities.ProfileActivity;
import com.mhmtyldz.yldz.sosyalokur.Activities.YazarActivity;
import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Alinti;

import java.util.List;

public class AlintiAdapter extends RecyclerView.Adapter<AlintiAdapter.CardViewTasarimNesneleriniTutucu> {

    private Context mContext;
    private List<Alinti> alintiList;

    public AlintiAdapter(Context mContext, List<Alinti> alintiList) {
        this.mContext = mContext;
        this.alintiList = alintiList;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {
        // cardView nesnelerini Adapter'a burası sayesinde bağlıyoruz...
        public ImageView imgProfilResmi;
        public TextView tvKullaniciAdi, tvAlintiBaslik, tvAlintiMetin, tvKitapAdi, tvYazarAd, tvYazarSoyad, tvAlintiTarihi;


        public CardViewTasarimNesneleriniTutucu(View itemView) {
            super(itemView);
            tasarimNesneleriniBaslat();
        }

        private void tasarimNesneleriniBaslat() {
            imgProfilResmi = itemView.findViewById(R.id.imgProfilResmi);
            tvKullaniciAdi = itemView.findViewById(R.id.tvKullaniciAdi);
            tvAlintiBaslik = itemView.findViewById(R.id.tvAlintiBaslik);
            tvAlintiMetin = itemView.findViewById(R.id.tvAlintiMetin);
            tvKitapAdi = itemView.findViewById(R.id.tvKitapAdi);
            tvYazarAd = itemView.findViewById(R.id.tvYazarAd);
            tvYazarSoyad = itemView.findViewById(R.id.tvYazarSoyad);
            tvAlintiTarihi = itemView.findViewById(R.id.tvAlintiTarihi);
        }

    }

    @NonNull
    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_alinti_tasarim, parent, false);
        return new CardViewTasarimNesneleriniTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewTasarimNesneleriniTutucu holder, int position) {
        final Alinti alinti = alintiList.get(position);
        holder.imgProfilResmi.setImageResource(mContext.getResources()
                .getIdentifier(alinti.getAlinti_resim_ad(), "drawable", mContext.getPackageName()));

        holder.tvKullaniciAdi.setText(alinti.getKullanici_adi());
        holder.tvAlintiBaslik.setText(alinti.getAlinti_baslik());
        holder.tvAlintiMetin.setText(alinti.getAlinti_metni());
        holder.tvKitapAdi.setText(alinti.getKitap().getKitap_adi());
        holder.tvYazarAd.setText(alinti.getKitap().getYazar().getAd());
        holder.tvYazarSoyad.setText(alinti.getKitap().getYazar().getSoyad());
        holder.tvAlintiTarihi.setText(alinti.getAlinti_tarihi());

        holder.tvKullaniciAdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kullaniciProfilineGit(holder.tvKullaniciAdi);
            }
        });

        holder.imgProfilResmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kullaniciProfilineGit(holder.tvKullaniciAdi);
            }
        });

        holder.tvKitapAdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kitapGit(holder.tvKitapAdi, holder.tvYazarAd, holder.tvYazarSoyad);
            }
        });

        holder.tvYazarAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yazarGit(alinti.getKitap().getYazar().getAd(),
                        alinti.getKitap().getYazar().getSoyad());
            }
        });

        holder.tvYazarSoyad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yazarGit(alinti.getKitap().getYazar().getAd(),
                        alinti.getKitap().getYazar().getSoyad());
            }
        });


    }

    private void yazarGit(String yazarAd, String yazarSoyad) {
        Intent intent = new Intent(mContext, YazarActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra("yazar_adi", yazarAd);
        intent.putExtra("yazar_soyadi", yazarSoyad);

        mContext.startActivity(intent);
    }

    private void kitapGit(TextView tvKitapAdi, TextView tvYazarAd, TextView tvYazarSoyad) {
        Intent intent = new Intent(mContext, KitapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra("kitap_adi", tvKitapAdi.getText().toString().trim());
        intent.putExtra("yazar_ad", tvYazarAd.getText().toString().trim());
        intent.putExtra("yazar_soyad", tvYazarSoyad.getText().toString().trim());
        Log.e("TAG", "gonderilen: " + tvKitapAdi.getText().toString().trim());

        mContext.startActivity(intent);
    }

    private void kullaniciProfilineGit(TextView tvKullaniciAdi) {
        Toast.makeText(mContext, "Card Nesnesine Tıklandı!" + tvKullaniciAdi.getText().toString().trim(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(mContext, ProfileActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.putExtra("kullanici_adi", tvKullaniciAdi.getText().toString().trim());
        Log.e("TAG", "gonderilen: " + tvKullaniciAdi.getText().toString().trim());

        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return alintiList.size();
    }
}
