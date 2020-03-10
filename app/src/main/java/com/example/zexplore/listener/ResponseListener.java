package com.example.zexplore.listener;

/**
 * Created by Ehigiator David on 08/05/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */
public interface ResponseListener<T> {

     void onSuccess(T data);

     void onError(String message);
}
