package com.mhmtyldz.yldz.sosyalokur.Siniflar;

public class Yazar {
    // id, ad, soyad
    private int id;
    private String ad;
    private String soyad;
    private String resim_url;


    public Yazar(int id, String ad, String soyad, String resim_url) {
        this.id = id;
        this.ad = ad;
        this.soyad = soyad;
        this.resim_url = resim_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getResim_url() {
        return resim_url;
    }

    public void setResim_url(String resim_url) {
        this.resim_url = resim_url;
    }
}
