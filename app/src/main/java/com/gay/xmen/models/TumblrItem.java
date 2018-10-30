package com.gay.xmen.models;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class TumblrItem implements Parcelable {

    private String url_image;

    public boolean isAD() {
        return IsAD;
    }

    public void setAD(boolean AD) {
        IsAD = AD;
    }

    private boolean IsAD;

    protected TumblrItem(Parcel in) {
        name = in.readString();
        autor = in.readString();
        url_image = in.readString();
        boolean[] booleans = new boolean[1];
        in.readBooleanArray(booleans);
        setAD(booleans[0]);

    }

    public TumblrItem(){

    }

    public static final Creator<TumblrItem> CREATOR = new Creator<TumblrItem>() {
        @Override
        public TumblrItem createFromParcel(Parcel in) {
            return new TumblrItem(in);
        }

        @Override
        public TumblrItem[] newArray(int size) {
            return new TumblrItem[size];
        }
    };

    public Uri getUrl_image() {
     //   Log.e("MAIN", "getUrl_image: "+url_image);
        if(url_image != null)
            return Uri.parse(url_image);
   else{
       return null;
        }

    }


    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    private String autor;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
dest.writeString(this.name);
dest.writeString(this.autor);
dest.writeString(this.url_image);
dest.writeBooleanArray(new boolean[]{this.isAD()});
    }
}
