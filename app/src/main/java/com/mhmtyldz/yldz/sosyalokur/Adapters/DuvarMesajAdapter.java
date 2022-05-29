package com.mhmtyldz.yldz.sosyalokur.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
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

import com.mhmtyldz.yldz.sosyalokur.Activities.ProfileActivity;
import com.mhmtyldz.yldz.sosyalokur.R;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Duvar;
import com.mhmtyldz.yldz.sosyalokur.Siniflar.Kitap;

import java.util.List;

public class DuvarMesajAdapter extends RecyclerView.Adapter<DuvarMesajAdapter.CardViewTasarimNesneleriniTutucu> {

    private Context mContext;
    private List<Duvar> duvarMesajList;

    public DuvarMesajAdapter(Context mContext, List<Duvar> duvarMesajList) {
        this.mContext = mContext;
        this.duvarMesajList = duvarMesajList;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder {
        // cardView nesnelerini Adapter'a burası sayesinde bağlıyoruz...
        public ConstraintLayout cl;
        //public ImageView imgDuvarProfilResmi;
        public TextView tvKullaniciAdi, tvMesaj, tvTarih;


        public CardViewTasarimNesneleriniTutucu(View itemView) {
            super(itemView);
           // imgDuvarProfilResmi = itemView.findViewById(R.id.imgDuvarProfilResmi);
            cl = itemView.findViewById(R.id.cl);

            tvKullaniciAdi = itemView.findViewById(R.id.tvKullaniciAdi);
            tvMesaj = itemView.findViewById(R.id.tvMesaj);
            tvTarih = itemView.findViewById(R.id.tvTarih);




        }
    }




    @NonNull
    @Override
    public DuvarMesajAdapter.CardViewTasarimNesneleriniTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_duvar_mesaj_tasarim, parent, false);


        return new CardViewTasarimNesneleriniTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DuvarMesajAdapter.CardViewTasarimNesneleriniTutucu holder, int position) {
        final Duvar duvar = duvarMesajList.get(position);
        holder.tvKullaniciAdi.setText(duvar.getKullaniciAdi());
        holder.tvMesaj.setText(duvar.getMesaj());
        holder.tvTarih.setText(duvar.getTarih());
        //holder.imgDuvarProfilResmi.setImageResource(mContext.getResources()
        //        .getIdentifier(duvar.getResimAd(), "drawable", mContext.getPackageName()));


        holder.tvKullaniciAdi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kullanici_adi = holder.tvKullaniciAdi.getText().toString().trim();
                kullaniciProfilineGit(kullanici_adi);
            }
        });

        /*holder.imgDuvarProfilResmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String kullanici_adi = holder.tvKullaniciAdi.getText().toString().trim();
                kullaniciProfilineGit(kullanici_adi);


            }
        });*/
    }

    private void kullaniciProfilineGit(String kullanici_adi) {
        Intent intent = new Intent(mContext, ProfileActivity.class);
        intent.putExtra("kullanici_adi", kullanici_adi);
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return duvarMesajList.size();
    }
}
