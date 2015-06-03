package com.example.ams.brewed.interfaces;

import com.example.ams.brewed.data.Beer;

/**
 * Created by PC on 02/06/2015.
 */
public interface IBeerView {

    void changeBackgroundColorBySRM();
    void displayInfo(Beer beer);

    void startShowInProgress();

    void stopShowSearchInProgress();

    void showError(String message);
}
