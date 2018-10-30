package com.gay.xmen.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.gay.xmen.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Downloader extends AsyncTask<String, Integer, String> {

    private PowerManager.WakeLock wakeLock;
    private Context mContext;
    private ProgressDialog progressDialog;

private DownloadArchiveResponse downloadArchiveResponse;
private Uri pathing;

    public Downloader(Context needed, ProgressDialog dialog){
        this.mContext = needed;
        this.progressDialog = dialog;
    }

    public Downloader(Context contexto, ProgressDialog dialog, DownloadArchiveResponse responseApi){
        this.mContext = contexto;
        this.progressDialog = dialog;
        this.downloadArchiveResponse = responseApi;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        PowerManager mg = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        wakeLock = mg.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, getClass().getName());
        wakeLock.acquire();
if(progressDialog != null) {
    progressDialog.show();
}
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        wakeLock.release();
        progressDialog.dismiss();

        if(downloadArchiveResponse!=null) {

            downloadArchiveResponse.Donwloaded(pathing);
        }
        if(s != null){
            Toast.makeText(mContext, "Download error: "+s, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(mContext, "Downloaded!", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setIndeterminate(false);
        progressDialog.setMax(100);
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected String doInBackground(String... strings) {

        String url = strings[0];
        String name = strings[1].replace(" ", "");

        String[] datasw = url.split("\\.");

        String extension = "."+datasw[datasw.length - 1];

        InputStream input = null;
        OutputStream outputStream = null;
        HttpURLConnection connection = null;




        try {

           connection = (HttpURLConnection) new URL(url).openConnection();

            connection.connect();


            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                return "Connection error: "+connection.getResponseMessage();
            }


            int filelength = connection.getContentLength();

            int count = 0;

            long total = 0;

            input = connection.getInputStream();

            String fileLocation = mContext.getResources().getString(R.string.app_name).replace(" ", "")+"/"+name+extension;

            File folder = new File(Environment.getExternalStorageDirectory()+"/"+mContext.getResources().getString(R.string.app_name).replace(" ", ""));
            boolean success;
            Log.e("MAIN", "doInBackground: "+fileLocation);
            if(!folder.exists()){
success = folder.mkdir();
            }


            outputStream = new FileOutputStream(Environment.getExternalStorageDirectory()+"/"+fileLocation);

            pathing = Uri.parse(Environment.getExternalStorageState()+"/"+fileLocation);

            byte[] bytes = new byte[4098];

            while((count = input.read(bytes)) != -1){
                if(isCancelled()){
                    input.close();
                    return null;
                }
                total += count;
                if(filelength > 0){
                    publishProgress((int) total*100 / filelength);
                }
                outputStream.write(bytes, 0, count);
            }





        } catch (IOException e) {
            Log.e("MAIN", "doInBackground: "+e.getMessage());
        }finally {
            try {
            if(outputStream!=null){

                    outputStream.close();
                }


                if(input != null){
                input.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            if(connection != null){
                connection.disconnect();
            }

        }


        return null;
    }
}
