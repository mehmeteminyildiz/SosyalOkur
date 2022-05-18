package com.mhmtyldz.yldz.sosyalokur.Siniflar;

import java.io.Serializable;

public class Kitap implements Serializable {

    private int id;
    private String kitap_adi;
    private Yazar yazar;


    public Kitap(int id, String kitap_adi, Yazar yazar) {
        this.id = id;
        this.kitap_adi = kitap_adi;
        this.yazar = yazar;
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

    public Yazar getYazar() {
        return yazar;
    }

    public void setYazar(Yazar yazar) {
        this.yazar = yazar;
    }
}
