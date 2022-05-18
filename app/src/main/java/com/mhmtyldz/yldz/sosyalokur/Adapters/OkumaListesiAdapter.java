package com.mhmtyldz.yldz.sosyalokur.Adapters;

import android.content.Context;
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
            tvYazarAdi = itemView.findViewById(R.id.tvYazarAdi);
            checkOkudum = itemView.findViewById(R.id.checkOkudum);
            cl = itemView.findViewById(R.id.cl);

            checkOkudum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) { // işaretlendiyse:
                        String kitapAdi = tvKitapAdi.getText().toString().trim();
                        updateOkunmaDurumu("email_adresi", kitapAdi, true);
                        tvKitapAdi.setPaintFlags(tvKitapAdi.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        tvYazarAdi.setPaintFlags(tvKitapAdi.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        sesCal();
                    } else {     // işaret kaldırıldıysa:
                        String kitapAdi = tvKitapAdi.getText().toString().trim();
                        updateOkunmaDurumu("email_adresi", kitapAdi, false);
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
                    } else {
                        tvKitapAdi.setPaintFlags(tvKitapAdi.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                        tvYazarAdi.setPaintFlags(tvYazarAdi.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    }

                }
            });

            cl.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(mContext, "Siliniyor...", Toast.LENGTH_SHORT).show();

                    cl.setBackgroundColor(mContext.getResources().getColor(R.color.silinecek_eleman));


                    deleteFromOkumaListesiDB("email", kitapList.get(getAdapterPosition()).getKitap_adi().trim());

                    kitapList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());

                    return false;
                }
            });


//            imgMoreList.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(mContext, "More!", Toast.LENGTH_SHORT).show();
//                    showPopup(imgMoreList, kitapList, getAdapterPosition());
//
//
//                }
//            });
        }
    }

    private void showPopup(ImageView imgMoreList, List<Kitap> kitapList, int adapterPosition) {
        PopupMenu popupMenu = new PopupMenu(mContext, imgMoreList);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_okuma_listesi, popupMenu.getMenu());
        popupMenu.show();

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_sil:
                        // geçerli kitabı hem rv'den hem de DB'den silmeliyiz.
                        Toast.makeText(mContext, "Siliniyor...", Toast.LENGTH_SHORT).show();

                        deleteFromOkumaListesiDB("email", kitapList.get(adapterPosition).getKitap_adi().trim());

                        kitapList.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition);


                        return true;
                    default:
                }
                return false;
            }
        });
    }

    private void deleteFromOkumaListesiDB(String email, String kitap_adi) {
        // burada email ve kitap_adi değerleri gönderilir. Serviste gerekli işlemler yapılır ve
        // okuma listesinden o kitap silinir.
        Log.e("TAG", "deleteFromOkumaListesiDB:\nemail: " + email + "\nkitap_adi: " + kitap_adi);
    }


    private void updateOkunmaDurumu(String email_adresi, String kitapAdi, boolean okunma_durumu) {
        // burada DB'de kullanıcının email'ine göre volley ile DB'deki okuma listesinde bulunan
        // kitabın okunma_durumu değeri güncellenir
        Log.e("TAG", "email: " + email_adresi + "\nkitapAdi : " + kitapAdi + "\n" +
                "Okunma Durumu: " + okunma_durumu);
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
        holder.tvYazarAdi.setText(kitap.getYazar().getAd());
    }

    @Override
    public int getItemCount() {
        return kitapList.size();
    }

}
