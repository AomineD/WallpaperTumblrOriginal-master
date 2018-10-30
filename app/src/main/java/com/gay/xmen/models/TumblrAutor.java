package com.gay.xmen.models;

import android.net.Uri;

public class TumblrAutor {
    public String getNameAutor() {
        return NameAutor;
    }

    public void setNameAutor(String nameAutor) {
        NameAutor = nameAutor;
    }

    public Uri getUrl_avatar() {
        return Uri.parse(url_avatar);
    }

    public void setUrl_avatar(String url_avatar) {
        this.url_avatar = url_avatar;
    }

    private String NameAutor;
    private String url_avatar;

    public int getCantidadWallpaper() {
        return CantidadWallpaper;
    }

    public void setCantidadWallpaper(int cantidadWallpaper) {
        CantidadWallpaper = cantidadWallpaper;
    }

    private int CantidadWallpaper;
}
