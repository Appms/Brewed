package com.example.ams.brewed.interfaces;

/**
 * Created by AMS on 14/05/2015.
 */
public interface ResponseReceiver<T> {
    void onResponseReceived(T response);
    void onErrorReceived (String message);
}
