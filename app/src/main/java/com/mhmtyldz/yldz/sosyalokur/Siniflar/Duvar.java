package com.mhmtyldz.yldz.sosyalokur.Siniflar;

import java.io.Serializable;

public class Duvar implements Serializable {
    private int id;
    private int kullanici_id;
    private String mesaj;
    private String tarih;
    private String kullaniciAdi;
    private String resimAd;

    public String getResimAd() {
        return resimAd;
    }

    public void setResimAd(String resimAd) {
        this.resimAd = resimAd;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public Duvar(int id, int kullanici_id, String mesaj, String tarih, String kullaniciAdi, String resimAd) {
        this.id = id;
        this.kullanici_id = kullanici_id;
        this.mesaj = mesaj;
        this.tarih = tarih;
        this.kullaniciAdi = kullaniciAdi;
        this.resimAd = resimAd;
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

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }
}
