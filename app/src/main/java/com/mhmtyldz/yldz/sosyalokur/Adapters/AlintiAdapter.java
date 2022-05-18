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
        public TextView tvKullaniciAdi, tvAlintiBaslik, tvAlintiMetin, tvKitapAdi, tvYazarAdi, tvAlintiTarihi;


        public CardViewTasarimNesneleriniTutucu(View itemView) {
            super(itemView);
            imgProfilResmi = itemView.findViewById(R.id.imgProfilResmi);
            tvKullaniciAdi = itemView.findViewById(R.id.tvKullaniciAdi);
            tvAlintiBaslik = itemView.findViewById(R.id.tvAlintiBaslik);
            tvAlintiMetin = itemView.findViewById(R.id.tvAlintiMetin);
            tvKitapAdi = itemView.findViewById(R.id.tvKitapAdi);
            tvYazarAdi = itemView.findViewById(R.id.tvYazarAdi);
            tvAlintiTarihi = itemView.findViewById(R.id.tvAlintiTarihi);


            itemView.setOnClickListener(new View.OnClickListener() { // Card Nesnesine dokunulursa:
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext, "Card Nesnesine Tıklandı!" + tvKullaniciAdi.getText().toString().trim(), Toast.LENGTH_SHORT).show();

                }
            });
            tvKullaniciAdi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    kullaniciProfilineGit();

                }
            });

            imgProfilResmi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    kullaniciProfilineGit();

                }
            });

            tvKitapAdi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    kitapGit();
                }
            });
            tvYazarAdi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    yazarGit();
                }
            });
        }

        private void kullaniciProfilineGit() {
            Toast.makeText(mContext, "Card Nesnesine Tıklandı!" + tvKullaniciAdi.getText().toString().trim(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, ProfileActivity.class);
            intent.putExtra("kullanici_adi", tvKullaniciAdi.getText().toString().trim());
            Log.e("TAG", "gonderilen: " + tvKullaniciAdi.getText().toString().trim());

            mContext.startActivity(intent);
        }

        private void kitapGit() {
            Toast.makeText(mContext, "Card Nesnesine Tıklandı!" + tvKullaniciAdi.getText().toString().trim(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, KitapActivity.class);
            intent.putExtra("kitap_adi", tvKitapAdi.getText().toString().trim());
            Log.e("TAG", "gonderilen: " + tvKitapAdi.getText().toString().trim());

            mContext.startActivity(intent);
        }

        private void yazarGit() {
            Toast.makeText(mContext, "Card Nesnesine Tıklandı!" + tvKullaniciAdi.getText().toString().trim(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mContext, YazarActivity.class);
            intent.putExtra("yazar_adi", tvYazarAdi.getText().toString().trim());
            Log.e("TAG", "gonderilen: " + tvYazarAdi.getText().toString().trim());

            mContext.startActivity(intent);
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
        holder.tvYazarAdi.setText(alinti.getKitap().getYazar().getAd() + " " + alinti.getKitap().getYazar().getSoyad());
        holder.tvAlintiTarihi.setText(alinti.getAlinti_tarihi());
    }

    @Override
    public int getItemCount() {
        return alintiList.size();
    }
}
