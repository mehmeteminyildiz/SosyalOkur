package com.mhmtyldz.yldz.sosyalokur.Siniflar;

import java.io.Serializable;

public class Alinti implements Serializable {

    private int id;
    private int kullanici_id;
    private String kullanici_adi;
    private String kitap_adi;
    private String yazar_adi;
    private int kitap_id;
    private String alinti_metni;
    private String alinti_baslik;
    private String alinti_tarihi;
    private String alinti_resim_ad;

    public Alinti(int id, int kullanici_id, int kitap_id, String alinti_metni, String alinti_baslik, String alinti_tarihi, String alinti_resim_ad,
                  String kullanici_adi, String kitap_adi, String yazar_adi) {
        this.id = id;
        this.kullanici_id = kullanici_id;
        this.kitap_id = kitap_id;
        this.alinti_metni = alinti_metni;
        this.alinti_baslik = alinti_baslik;
        this.alinti_tarihi = alinti_tarihi;
        this.alinti_resim_ad = alinti_resim_ad;
        this.kullanici_adi = kullanici_adi;
        this.kitap_adi = kitap_adi;
        this.yazar_adi = yazar_adi;
    }

    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }

    public String getKitap_adi() {
        return kitap_adi;
    }

    public void setKitap_adi(String kitap_adi) {
        this.kitap_adi = kitap_adi;
    }

    public String getYazar_adi() {
        return yazar_adi;
    }

    public void setYazar_adi(String yazar_adi) {
        this.yazar_adi = yazar_adi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKullanici_id() {
        return kullanici_id;
    }

    public void setKullanici_id(int kullanici_id) {
        this.kullanici_id = kullanici_id;
    }

    public int getKitap_id() {
        return kitap_id;
    }

    public void setKitap_id(int kitap_id) {
        this.kitap_id = kitap_id;
    }

    public String getAlinti_metni() {
        return alinti_metni;
    }

    public void setAlinti_metni(String alinti_metni) {
        this.alinti_metni = alinti_metni;
    }

    public String getAlinti_baslik() {
        return alinti_baslik;
    }

    public void setAlinti_baslik(String alinti_baslik) {
        this.alinti_baslik = alinti_baslik;
    }

    public String getAlinti_tarihi() {
        return alinti_tarihi;
    }

    public void setAlinti_tarihi(String alinti_tarihi) {
        this.alinti_tarihi = alinti_tarihi;
    }

    public String getAlinti_resim_ad() {
        return alinti_resim_ad;
    }

    public void setAlinti_resim_ad(String alinti_resim_ad) {
        this.alinti_resim_ad = alinti_resim_ad;
    }
}
