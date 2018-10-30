package com.gay.xmen.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gay.xmen.models.TumblrAutor;
import com.gay.xmen.models.TumblrItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TumblrApi {

    private int cantity = 0;
    private ResponseApi responseApi;
    private ResponseAutor autorResponse;

    private Context mContext;
    private String urlMain;


    public TumblrApi(String url, Context mxContext, ResponseApi responseApi){


this.urlMain = url;
this.responseApi = responseApi;
this.mContext = mxContext;

    }

    public TumblrApi(String url, Context mxContext, ResponseAutor responseAutor){


        this.urlMain = url;
        this.autorResponse = responseAutor;
        this.mContext = mxContext;

    }


    public void RunApi(){
        Log.e("MAIN", "RunApi: URL "+urlMain);

        RequestQueue queue = Volley.newRequestQueue(mContext);

        StringRequest request = new StringRequest(StringRequest.Method.GET, urlMain, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
               // Log.e("MAIN", "onResponse: "+response);
                if(responseApi!=null) {
                    responseApi.Correct(getArray(response));

                }else if(autorResponse != null){
                    autorResponse.OnDataLoaded(autorGet(response));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Log.e("MAIN", "onErrorResponse: "+error.getMessage() );
                if(responseApi !=null) {
                    responseApi.Incorrect(error.getMessage());
                }else if(autorResponse != null){
autorResponse.OnDataFailedToLoad(error.getMessage());
                }
            }
        });

        queue.add(request);
    }



    private ArrayList<TumblrItem> getArray(String r){
        ArrayList<TumblrItem> items = new ArrayList<>();

        String JsonString = r.replace("var tumblr_api_read =", "");

        try {
            JSONObject objectMain = new JSONObject(JsonString);

            if(objectMain.has("posts")) {
                JSONArray posts = objectMain.getJSONArray("posts");


                for (int i=0; i < posts.length(); i++){


                    String url_photo = "";
                    if(posts.getJSONObject(i).has("photo-url-1280")) {
                        cantity++;
                        url_photo = posts.getJSONObject(i).getString("photo-url-1280");

                        TumblrItem item = new TumblrItem();
                        JSONObject objectData = posts.getJSONObject(1).getJSONObject("tumblelog");

                        String title = objectData.getString("title");
                        String autor = objectData.getString("name");

                        String url_avatar = objectData.getString("avatar_url_512");



                        item.setName(title);
                        item.setAutor(autor);
                        item.setUrl_image(url_photo);

                        items.add(item);

                    }else if(posts.getJSONObject(i).has("photo-url-500")){
                        url_photo = posts.getJSONObject(i).getString("photo-url-500");

                        TumblrItem item = new TumblrItem();
                        JSONObject objectData = posts.getJSONObject(1).getJSONObject("tumblelog");

                        String title = objectData.getString("title");
                        String autor = objectData.getString("name");


                        item.setName(title);
                        item.setAutor(autor);
                        item.setUrl_image(url_photo);

                        items.add(item);
                    }







                }

                Log.e("MAIN", "CANTIDAD: "+cantity);

               // Log.e("MAIN", "posts: "+);


            }




        } catch (JSONException e) {
            Log.e("MAIN", "getArray: "+e.getMessage());
        }


        return items;
    }


    public TumblrAutor autorGet(String r){

        TumblrAutor item = new TumblrAutor();

        String JsonString = r.replace("var tumblr_api_read =", "");

        try {
            JSONObject objectMain = new JSONObject(JsonString);

            if(objectMain.has("posts")) {
                JSONArray posts = objectMain.getJSONArray("posts");


                for (int i=0; i < posts.length(); i++){


                   // String url_photo = "";
                    if(posts.getJSONObject(i).has("photo-url-1280")) {
                        cantity++;
if(i == 0) {

    JSONObject objectData = posts.getJSONObject(1).getJSONObject("tumblelog");

    String autor = objectData.getString("name");

    String url_avatar = objectData.getString("avatar_url_512");

    item.setNameAutor(autor);
    item.setUrl_avatar(url_avatar);



}
                    }







                }

                Log.e("MAIN", "CANTIDAD autors: "+cantity);

                // Log.e("MAIN", "posts: "+);


            }else{
                return null;
            }




        } catch (JSONException e) {
            Log.e("MAIN", "getArray: "+e.getMessage());
        }
        item.setCantidadWallpaper(cantity);
        return item;
    }




}
