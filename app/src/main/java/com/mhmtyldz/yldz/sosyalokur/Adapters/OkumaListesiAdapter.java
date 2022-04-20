package com.mhmtyldz.yldz.sosyalokur.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Alinti;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Kitap;

import java.util.List;

public class OkumaListesiAdapter extends RecyclerView.Adapter<OkumaListesiAdapter.CardViewTasarimNesneleriniTutucu> {

    private Context mContext;
    private List<Kitap> kitapList;

    public OkumaListesiAdapter(Context mContext, List<Kitap> kitapList) {
        this.mContext = mContext;
        this.kitapList = kitapList;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {
        // cardView nesnelerini Adapter'a burası sayesinde bağlıyoruz...
        public ImageView imgMoreList;
        public TextView tvKitapAdi, tvYazarAdi;
        public CheckBox checkOkudum;
        public ConstraintLayout cl;


        public CardViewTasarimNesneleriniTutucu(View itemView) {
            super(itemView);
            imgMoreList = itemView.findViewById(R.id.imgMoreList);
            tvKitapAdi = itemView.findViewById(R.id.tvKitapAdi);
            tvYazarAdi = itemView.findViewById(R.id.tvYazarAdi);
            checkOkudum = itemView.findViewById(R.id.checkOkudum);
            cl = itemView.findViewById(R.id.cl);

            checkOkudum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) { // işaretlendiyse:
                        tvKitapAdi.setPaintFlags(tvKitapAdi.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        tvYazarAdi.setPaintFlags(tvKitapAdi.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    } else {     // işaret kaldırıldıysa:
                        tvKitapAdi.setPaintFlags(tvKitapAdi.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        tvYazarAdi.setPaintFlags(tvKitapAdi.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

                    }
                }
            });

            cl.setOnClickListener(new View.OnClickListener() { // Card Nesnesine dokunulursa:
                @Override
                public void onClick(View v) {
                    checkOkudum.setChecked(!checkOkudum.isChecked());
                    Toast.makeText(mContext, "CL Tıklandı!", Toast.LENGTH_SHORT).show();
                    if (checkOkudum.isChecked()) { //
                        tvKitapAdi.setPaintFlags(tvKitapAdi.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        tvYazarAdi.setPaintFlags(tvKitapAdi.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    } else { //
                        tvKitapAdi.setPaintFlags(tvKitapAdi.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        tvYazarAdi.setPaintFlags(tvYazarAdi.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    }

                }
            });

            imgMoreList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "More!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    @NonNull
    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_okuma_listesi_tasarim, parent, false);


        return new CardViewTasarimNesneleriniTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewTasarimNesneleriniTutucu holder, int position) {
        final Kitap kitap = kitapList.get(position);
        holder.tvKitapAdi.setText(kitap.getKitap_adi());
        holder.tvYazarAdi.setText(kitap.getYazar_adi());

    }

    @Override
    public int getItemCount() {
        return kitapList.size();
    }

}
