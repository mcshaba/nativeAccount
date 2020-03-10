package com.example.zexplore.repository;

import android.widget.Toast;

import com.example.zexplore.listener.ResponseListener;
import com.example.zexplore.model.AccountBaseResponse;
import com.example.zexplore.model.AccountEdit;
import com.example.zexplore.model.AccountModel;
import com.example.zexplore.model.Bvn;
import com.example.zexplore.model.LoginResponse;
import com.example.zexplore.model.SaveAccountResponse;
import com.example.zexplore.model.StatusRSM;
import com.example.zexplore.model.formresponse.AccountResponse;
import com.example.zexplore.model.formresponse.Data;
import com.example.zexplore.network.NetworkClient;
import com.example.zexplore.network.NetworkInterface;
import com.example.zexplore.service.ZxploreService;
import com.example.zexplore.util.Constant;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import androidx.annotation.NonNull;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.ResourceSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public class AccountRepository {

    private NetworkInterface networkInterface;

    private static volatile AccountRepository accountRepository;


    //this should be private
    public AccountRepository() {

        this.networkInterface = NetworkClient.getNetworkInterface(Constant.ZENITH_BASE_URL);
        //Prevent form the reflection api.
//        if (accountRepository != null){
//            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
//        }
    }


    public static AccountRepository getInstance() {
        if (accountRepository == null) {
            synchronized (AccountRepository.class) {
                if (accountRepository == null) accountRepository = new AccountRepository();
            }
        }

        return accountRepository;
    }

    protected AccountRepository readResolve() {
        return getInstance();
    }

    public Single<Response<SaveAccountResponse>> getAccountsByRefId(String token, String refId) {
        return networkInterface.GetAccountDetailsByRefID(token, refId);
    }

    public Single<Response<AccountBaseResponse<List<AccountModel>>>> getAccountsByRsmID(String token, String statusRSM, String status) {
        return networkInterface.getAccountsByRsmID(token, statusRSM, status);
    }


    public void verifyAccountsByReferenceId(String token, String referenceId, @NonNull ResponseListener<String> responseListener) {

        networkInterface.verifyReferenceId(token, referenceId).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new ResourceSingleObserver<Response<LoginResponse>>() {
            @Override
            public void onSuccess(Response<LoginResponse> loginResponseResponse) {

                if (loginResponseResponse.code() == 200) {
                    if (loginResponseResponse.body() != null && loginResponseResponse.body().getData() != null && loginResponseResponse.body().getData().getResponseMessage() != null) {
                        String message = loginResponseResponse.body().getData().getResponseMessage();
                        responseListener.onSuccess(message);
                    } else {
                        responseListener.onError("Couldn't connect to the server to get a response.");
                    }
                } else if (loginResponseResponse.code() == 400) {
                    assert loginResponseResponse.body() != null;

                    try {
                        assert loginResponseResponse.errorBody() != null;
                        JSONObject jObjError = new JSONObject(loginResponseResponse.errorBody().string());

                        responseListener.onError(jObjError.getString("message"));

                    } catch (Exception e) {
                        responseListener.onError(e.getLocalizedMessage());
                    }


                } else if (loginResponseResponse.code() == 401) {
                    responseListener.onError("Your current session has expired, kindly login again.");
                } else if (loginResponseResponse.code() == 404) {
                    responseListener.onError("Can't reach the Zenith network at the moment.");
                } else if (loginResponseResponse.code() == 502) {
                    responseListener.onError("Can't reach the Zenith network at the moment. Contact support.");
                } else {
                    responseListener.onError("An error occurred, . Kindly contact support. ");
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    if (responseBody != null) {
                        responseListener.onError(responseBody.toString());
                    } else {
                        responseListener.onError("An error occurred, . Kindly contact support.");
                    }

                } else if (e instanceof SocketTimeoutException) {
                    responseListener.onError("We are having trouble connecting to the Zenith network. Request Timeout. ");

                } else if (e instanceof IOException) {
                    responseListener.onError("You seem to have a bad network connection. Ensure you have an active connection.");

                } else {
//                    Toast.makeText(ZxploreService.this, "Zenith Server down", Toast.LENGTH_SHORT).show();

                    //view.onUnknownError(e.getMessage());
                }
            }
        });
    }


    public void getAccountsByRefId(String token, String referenceId, @NonNull ResponseListener<Data> responseListener) {

        networkInterface.getAccountByReferenceId(token, referenceId).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(new ResourceSingleObserver<Response<AccountResponse>>() {
            @Override
            public void onSuccess(Response<AccountResponse> accountResponse) {

                if (accountResponse.code() == 200) {
                    if (accountResponse.body() != null && accountResponse.body().getData() != null) {
                        responseListener.onSuccess(accountResponse.body().getData());
                    } else {
                        responseListener.onError("Couldn't connect to the server to get a response.");
                    }
                } else if (accountResponse.code() == 401) {
                    responseListener.onError("Your current session has expired, kindly login again.");
                } else if (accountResponse.code() == 404) {
                    responseListener.onError("Can't reach the Zenith network at the moment.");
                } else if (accountResponse.code() == 502) {
                    responseListener.onError("Can't reach the Zenith network at the moment. Contact support.");
                } else {
                    responseListener.onError("An error occurred, . Kindly contact support. ");
                }

            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    ResponseBody responseBody = ((HttpException) e).response().errorBody();
                    if (responseBody != null) {
                        responseListener.onError(responseBody.toString());
                    } else {
                        responseListener.onError("An error occurred, . Kindly contact support.");
                    }

                } else if (e instanceof SocketTimeoutException) {
                    responseListener.onError("We are having trouble connecting to the Zenith network. Request Timeout. ");

                } else if (e instanceof IOException) {
                    responseListener.onError("You seem to have a bad network connection. Ensure you have an active connection.");

                } else {
//                    Toast.makeText(ZxploreService.this, "Zenith Server down", Toast.LENGTH_SHORT).show();

                    //view.onUnknownError(e.getMessage());
                }
            }
        });
    }


}
