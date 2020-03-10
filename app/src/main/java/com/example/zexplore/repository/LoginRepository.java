package com.example.zexplore.repository;

import android.util.Log;

import com.example.zexplore.model.Login;
import com.example.zexplore.model.LoginResponse;
import com.example.zexplore.network.NetworkClient;
import com.example.zexplore.network.NetworkInterface;
import com.example.zexplore.util.BaseView;
import com.example.zexplore.util.Constant;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.SocketTimeoutException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.HttpException;
import retrofit2.Response;

public class LoginRepository {
    private NetworkInterface networkInterface;

    public LoginRepository(){
        networkInterface = NetworkClient.getNetworkInterface(Constant.ZENITH_BASE_URL);
    }

    public LiveData<LoginResponse> login(Login login){
        final MutableLiveData<LoginResponse> liveData = new MutableLiveData<>();

        networkInterface.login(login).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<LoginResponse>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<LoginResponse> loginResponse) {

                        if(loginResponse.code() == 400){

                            Gson gson = new Gson();

                            LoginResponse response1 = null;
                            try {
                                response1 = gson.fromJson(loginResponse.errorBody().string(), LoginResponse.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            liveData.setValue(response1);
                        } else if(loginResponse.code() == 200){
                            liveData.setValue(loginResponse.body());
                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException)e).response().errorBody();
                            liveData.setValue(null);

                        } else if (e instanceof SocketTimeoutException) {
                            liveData.setValue(null);

                        } else if (e instanceof IOException) {
                            liveData.setValue(null);

                        } else {
                            liveData.setValue(null);
                        }

                    }
                });


        return liveData;
    }
    private String getErrorMessage(ResponseBody responseBody) {
        try {
            JSONObject jsonObject = new JSONObject(responseBody.string());
            return jsonObject.getString("message");
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}

