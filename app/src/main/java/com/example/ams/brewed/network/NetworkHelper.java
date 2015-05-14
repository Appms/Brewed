package com.example.ams.brewed.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import com.example.ams.brewed.interfaces.ResponseReceiver;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by AMS on 14/05/2015.
 */
public class NetworkHelper {
    private Context context;

    public NetworkHelper(Context context) {
        this.context = context;
    }

    private boolean isNetworkConnected(){
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private static String downloadUrl (String myurl) throws IOException {
        InputStream is = null;

        try{
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /*miliseconds*/);
            conn.setConnectTimeout(15000 /*miliseconds*/);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            //Starts the query
            conn.connect();
            is = conn.getInputStream();

            Scanner s = new Scanner(is).useDelimiter("\\A");
            return s.hasNext() ? s.next() : "";
        } finally {
            if (is != null) {
                is.close();
            }
        }


    }

    public void fetchWebPage(String url,
                             final ResponseReceiver<String> pageProcessor){

        if(!isNetworkConnected())
            pageProcessor.onErrorReceived("No Network Access");

        else {
            new AsyncTask<String, Void, Boolean>(){

                String result;

                @Override
                protected Boolean doInBackground(String... urls){
                    try {
                        result = downloadUrl(urls[0]);
                        return true;
                    } catch (IOException e) {
                        result = "Network Error";
                        return false;
                    }
                }

                @Override
                protected void onPostExecute(Boolean taskOk){
                    if(taskOk)
                        pageProcessor.onResponseReceived(result);
                    else
                        pageProcessor.onErrorReceived(result);
                }
            }.execute(url);
        }
    }
}
