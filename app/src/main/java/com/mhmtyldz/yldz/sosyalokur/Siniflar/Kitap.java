package com.mhmtyldz.yldz.sosyalokur.Siniflar;

import java.io.Serializable;

public class Kitap implements Serializable {

    private int id;
    private String kitap_adi;
    private int yazar_id;
    private String yazar_adi;

    public Kitap(int id, String kitap_adi, int yazar_id, String yazar_adi) {
        this.id = id;
        this.kitap_adi = kitap_adi;
        this.yazar_id = yazar_id;
        this.yazar_adi = yazar_adi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKitap_adi() {
        return kitap_adi;
    }

    public void setKitap_adi(String kitap_adi) {
        this.kitap_adi = kitap_adi;
    }

    public int getYazar_id() {
        return yazar_id;
    }

    public void setYazar_id(int yazar_id) {
        this.yazar_id = yazar_id;
    }

    public String getYazar_adi() {
        return yazar_adi;
    }

    public void setYazar_adi(String yazar_adi) {
        this.yazar_adi = yazar_adi;
    }
}
