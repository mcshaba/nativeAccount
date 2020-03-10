package com.example.zexplore.util;

public interface BaseView {
    void showLoading(String message);

    void showLoading();

    void hideLoading();

    void onUnknownError(String error);

    void onTimeout();

    void onNetworkError();

    boolean isNetworkConnected();

    void onConnectionError();
}
