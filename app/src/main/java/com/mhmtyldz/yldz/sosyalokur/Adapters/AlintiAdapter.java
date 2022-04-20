package com.mhmtyldz.yldz.sosyalokur.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
                    //Toast.makeText(mContext, "Card Nesnesine Tıklandı!" + textViewKullaniciAdi.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                }
            });
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
        holder.tvKitapAdi.setText(alinti.getKitap_adi());
        holder.tvYazarAdi.setText(alinti.getYazar_adi());
        holder.tvAlintiTarihi.setText(alinti.getAlinti_tarihi());

    }

    @Override
    public int getItemCount() {
        return alintiList.size();
    }
}
