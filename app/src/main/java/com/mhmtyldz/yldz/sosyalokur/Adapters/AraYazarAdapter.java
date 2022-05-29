package com.mhmtyldz.yldz.sosyalokur.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mhmtyldz.yldz.sosyalokur.Activities.YazarActivity;
import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Yazar;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AraYazarAdapter extends RecyclerView.Adapter<AraYazarAdapter.CardViewTasarimNesneleriniTutucu> {

    private Context mContext;
    private List<Yazar> yazarList;

    public AraYazarAdapter(Context mContext, List<Yazar> yazarList) {
        this.mContext = mContext;
        this.yazarList = yazarList;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {

        public TextView tvYazarAd;
        public ConstraintLayout cl;

        public CardViewTasarimNesneleriniTutucu(View itemView) {
            super(itemView);
            tvYazarAd = itemView.findViewById(R.id.tvYazarAd);
            cl = itemView.findViewById(R.id.cl);
        }
    }


    @NonNull
    @Override
    public AraYazarAdapter.CardViewTasarimNesneleriniTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ara_yazar_tasarim, parent, false);

        return new CardViewTasarimNesneleriniTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AraYazarAdapter.CardViewTasarimNesneleriniTutucu holder, int position) {

        final Yazar yazar = yazarList.get(position);
        String yazarAd = yazar.getAd();
        String yazarSoyad = yazar.getSoyad();
        holder.tvYazarAd.setText(yazarAd + " " + yazarSoyad);


        // kitap detay sayfası hazır olunca bunu kullanalım
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, YazarActivity.class);
                intent.putExtra("yazar_adi", yazarAd);
                intent.putExtra("yazar_soyadi", yazarSoyad);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // yeni activity'e geçerken kullandık
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() { // 09.12.2021... Burada return 0 kalmış yanlışlıkla. 30 dakikadır bunun yüzünden çalışmadı program!
        return yazarList.size();
    }

}
