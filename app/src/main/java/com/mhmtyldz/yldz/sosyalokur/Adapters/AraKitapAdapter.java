package com.mhmtyldz.yldz.sosyalokur.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Kitap;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Yazar;

import java.util.List;

public class AraKitapAdapter extends RecyclerView.Adapter<AraKitapAdapter.CardViewTasarimNesneleriniTutucu> {

    private Context mContext;
    private List<Kitap> kitapList;

    public AraKitapAdapter(Context mContext, List<Kitap> kitapList) {
        this.mContext = mContext;
        this.kitapList = kitapList;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {

        public TextView tvKitapAdi, tvYazarAdi;
        public ConstraintLayout cl;

        public CardViewTasarimNesneleriniTutucu(View itemView) {
            super(itemView);
            tvKitapAdi = itemView.findViewById(R.id.tvKitapAdi);
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
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ara_kitap_tasarim, parent, false);

        return new CardViewTasarimNesneleriniTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewTasarimNesneleriniTutucu holder, int position) {

        final Kitap kitap = kitapList.get(position);
        holder.tvKitapAdi.setText(kitap.getKitap_adi().trim());
        holder.tvYazarAdi.setText(kitap.getYazar().getAd().trim());

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
        return kitapList.size();
    }


}
