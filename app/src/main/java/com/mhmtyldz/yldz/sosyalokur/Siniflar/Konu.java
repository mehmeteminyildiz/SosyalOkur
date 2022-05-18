package com.mhmtyldz.yldz.sosyalokur.Siniflar;

import java.io.Serializable;

public class Konu implements Serializable {

    private int id;
    private String konu_metni;

    public Konu(int id, String konu_metni) {
        this.id = id;
        this.konu_metni = konu_metni;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKonu_metni() {
        return konu_metni;
    }

    public void setKonu_metni(String konu_metni) {
        this.konu_metni = konu_metni;
    }
}
