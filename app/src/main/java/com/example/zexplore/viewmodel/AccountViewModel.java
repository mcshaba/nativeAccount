package com.example.zexplore.viewmodel;

import android.app.Application;

import com.example.zexplore.model.AccountBaseResponse;
import com.example.zexplore.model.AccountEdit;
import com.example.zexplore.model.AccountModel;
import com.example.zexplore.model.LoginResponse;
import com.example.zexplore.model.SaveAccountResponse;
import com.example.zexplore.repository.AccountRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class AccountViewModel extends AndroidViewModel {

    private AccountRepository repository;
    private MutableLiveData<Response<SaveAccountResponse>> responseLiveData;
    private MutableLiveData<Response<AccountBaseResponse<List<AccountModel>>>> liveData;
    private Disposable disposable;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        repository = new AccountRepository();
        responseLiveData = new MutableLiveData<>();
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<Response<SaveAccountResponse>> getAccountViewModel(String token, String refId){
        disposable = repository.getAccountsByRefId(token, refId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Response<SaveAccountResponse>>() {
                    @Override
                    public void accept(Response<SaveAccountResponse> loginResponseResponse) throws Exception {
                        responseLiveData.setValue(loginResponseResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        responseLiveData.setValue(null);
                    }
                });
        return responseLiveData;
    }

    public MutableLiveData<Response<AccountBaseResponse<List<AccountModel>>>> getAccountsByRsmID(String token, String statusRSM, String status){
        disposable = repository.getAccountsByRsmID(token, statusRSM, status)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Response<AccountBaseResponse<List<AccountModel>>>>() {
                    @Override
                    public void accept(Response<AccountBaseResponse<List<AccountModel>>> loginResponseResponse) throws Exception {
                        liveData.setValue(loginResponseResponse);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        liveData.setValue(null);
                    }
                });
                return liveData;
    }

    @Override
    protected void onCleared() {
        if (disposable != null)disposable.dispose();
    }

}
