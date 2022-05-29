package com.mhmtyldz.yldz.sosyalokur.Adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.mhmtyldz.yldz.sosyalokur.R;
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
        //public ImageView imgMoreList;
        public TextView tvKitapAdi, tvYazarAdi;
        public CheckBox checkOkudum;
        public ConstraintLayout cl;


        public CardViewTasarimNesneleriniTutucu(View itemView) {
            super(itemView);
            //imgMoreList = itemView.findViewById(R.id.imgMoreList);
            tvKitapAdi = itemView.findViewById(R.id.tvKitapAdi);
            tvYazarAdi = itemView.findViewById(R.id.tvYazarAd);
            checkOkudum = itemView.findViewById(R.id.checkOkudum);
            cl = itemView.findViewById(R.id.cl);
        }
    }

    private void deleteFromOkumaListesiDB(String kitap_adi) {
        SharedPreferences sp = mContext.getSharedPreferences("girisBilgileri", Context.MODE_PRIVATE);
        String email = sp.getString("email_adresi", "");
        // burada email ve kitap_adi değerleri gönderilir. Serviste gerekli işlemler yapılır ve
        // okuma listesinden o kitap silinir.
        Log.e("TAG", "deleteFromOkumaListesiDB:\nemail: " + email + "\nkitap_adi: " + kitap_adi);
    }


    private void updateOkunmaDurumu(String kitapAdi, boolean okunma_durumu) {
        // burada DB'de kullanıcının email'ine göre volley ile DB'deki okuma listesinde bulunan
        // kitabın okunma_durumu değeri güncellenir
        SharedPreferences sp = mContext.getSharedPreferences("girisBilgileri", Context.MODE_PRIVATE);
        String email = sp.getString("email_adresi", "");
        Log.e("TAG", "email: " + email + "\nkitapAdi : " + kitapAdi + "\n" +
                "Okunma Durumu: " + okunma_durumu);

        if (okunma_durumu){

        }
    }

    private void sesCal() {
        MediaPlayer mediaPlayer = MediaPlayer.create(mContext, R.raw.ding);
        mediaPlayer.start(); // no need to call prepare(); create() does that for you
        titret();
    }

    private void titret() {
        Vibrator vibe = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
        vibe.vibrate(50);
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
        holder.tvYazarAdi.setText(kitap.getYazar().getAd() + " " + kitap.getYazar().getSoyad());

        holder.checkOkudum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) { // işaretlendiyse:
                    String kitapAdi = holder.tvKitapAdi.getText().toString().trim();
                    updateOkunmaDurumu(kitapAdi, true);
                    holder.tvKitapAdi.setPaintFlags(holder.tvKitapAdi.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tvYazarAdi.setPaintFlags(holder.tvKitapAdi.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    holder.cl.setBackgroundColor(mContext.getResources().getColor(R.color.okunmus_eleman));
                    holder.tvKitapAdi.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.tvYazarAdi.setTextColor(mContext.getResources().getColor(R.color.white));

                    sesCal();
                } else {     // işaret kaldırıldıysa:
                    String kitapAdi = holder.tvKitapAdi.getText().toString().trim();
                    updateOkunmaDurumu(kitapAdi, false);
                    holder.tvKitapAdi.setPaintFlags(holder.tvKitapAdi.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    holder.tvYazarAdi.setPaintFlags(holder.tvKitapAdi.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

                    holder.cl.setBackgroundColor(mContext.getResources().getColor(R.color.cl_bg));
                    holder.tvKitapAdi.setTextColor(mContext.getResources().getColor(R.color.black));
                    holder.tvYazarAdi.setTextColor(mContext.getResources().getColor(R.color.okuma_listesi_yazar));
                }
            }
        });

        holder.cl.setOnClickListener(new View.OnClickListener() { // Card Nesnesine dokunulursa:
            @Override
            public void onClick(View v) {

                holder.checkOkudum.setChecked(!holder.checkOkudum.isChecked());
                Toast.makeText(mContext, "CL Tıklandı!", Toast.LENGTH_SHORT).show();
                if (holder.checkOkudum.isChecked()) { //
                    String kitapAdi = holder.tvKitapAdi.getText().toString().trim();
                    updateOkunmaDurumu(kitapAdi, true);

                    holder.tvKitapAdi.setPaintFlags(holder.tvKitapAdi.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    holder.tvYazarAdi.setPaintFlags(holder.tvKitapAdi.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                    holder.cl.setBackgroundColor(mContext.getResources().getColor(R.color.okunmus_eleman));
                    holder.tvKitapAdi.setTextColor(mContext.getResources().getColor(R.color.white));
                    holder.tvYazarAdi.setTextColor(mContext.getResources().getColor(R.color.white));

                    kitapList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                } else {
                    holder.tvKitapAdi.setPaintFlags(holder.tvKitapAdi.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    holder.tvYazarAdi.setPaintFlags(holder.tvYazarAdi.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

                    holder.cl.setBackgroundColor(mContext.getResources().getColor(R.color.cl_bg));
                    holder.tvKitapAdi.setTextColor(mContext.getResources().getColor(R.color.black));
                    holder.tvYazarAdi.setTextColor(mContext.getResources().getColor(R.color.okuma_listesi_yazar));

                    kitapList.remove(holder.getAdapterPosition());
                    notifyItemRemoved(holder.getAdapterPosition());
                }

            }
        });


        holder.cl.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(mContext, "Siliniyor...", Toast.LENGTH_SHORT).show();

                holder.cl.setBackgroundColor(mContext.getResources().getColor(R.color.silinecek_eleman));
                holder.tvKitapAdi.setTextColor(mContext.getResources().getColor(R.color.white));
                holder.tvYazarAdi.setTextColor(mContext.getResources().getColor(R.color.white));


                deleteFromOkumaListesiDB(kitapList.get(holder.getAdapterPosition()).getKitap_adi().trim());

                kitapList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return kitapList.size();
    }

}
