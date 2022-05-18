package com.mhmtyldz.yldz.sosyalokur.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Kitap;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Yazar;

import java.util.List;

public class AraYazarAdapter extends RecyclerView.Adapter<AraYazarAdapter.CardViewTasarimNesneleriniTutucu>  {

    private Context mContext;
    private List<Yazar> yazarList;

    public AraYazarAdapter(Context mContext, List<Yazar> yazarList) {
        this.mContext = mContext;
        this.yazarList = yazarList;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {

        public TextView tvYazarAdi;
        public ConstraintLayout cl;

        public CardViewTasarimNesneleriniTutucu(View itemView) {
            super(itemView);
            tvYazarAdi = itemView.findViewById(R.id.tvYazarAdi);
            cl = itemView.findViewById(R.id.cl);

            itemView.setOnClickListener(new View.OnClickListener() { // Card Nesnesine dokunulursa:
                @Override
                public void onClick(View v) {
                    //Toast.makeText(mContext, "Card Nesnesine Tıklandı!", Toast.LENGTH_SHORT).show();
                }
            });
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
        holder.tvYazarAdi.setText(yazar.getAd().trim() + " " + yazar.getSoyad().trim());

        // kitap detay sayfası hazır olunca bunu kullanalım
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(mContext, KitapDetayActivity.class);
//                intent.putExtra("tiklanilan_kitap_adi", holder.tvKitapAdi.getText().toString().trim());
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // yeni activity'e geçerken kullandık
//                mContext.startActivity(intent);
//            }
//        });

    }

    @Override
    public int getItemCount() { // 09.12.2021... Burada return 0 kalmış yanlışlıkla. 30 dakikadır bunun yüzünden çalışmadı program!
        return yazarList.size();
    }

}
