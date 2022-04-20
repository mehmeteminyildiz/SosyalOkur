package com.mhmtyldz.yldz.sosyalokur.Siniflar;

import java.io.Serializable;

public class Kullanici implements Serializable {

    private int id;
    private String kullanici_adi;
    private String email;
    private int resim_id;

    public Kullanici(int id, String kullanici_adi, String email, int resim_id) {
        this.id = id;
        this.kullanici_adi = kullanici_adi;
        this.email = email;
        this.resim_id = resim_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getResim_id() {
        return resim_id;
    }

    public void setResim_id(int resim_id) {
        this.resim_id = resim_id;
    }
}
