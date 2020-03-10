package com.example.zexplore.viewmodel;

import android.app.Application;

import com.example.zexplore.model.Bvn;
import com.example.zexplore.repository.BvnRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class BvnViewModel extends AndroidViewModel {

    private BvnRepository repository;
    private MutableLiveData<Bvn> bvnLiveData;
    private Disposable disposable;

    public BvnViewModel(@NonNull Application application) {
        super(application);
        repository = new BvnRepository();
        bvnLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<Bvn> verifyBvn(String token, Bvn bvn){
        disposable = repository.verifyBvn(token , bvn)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Bvn>() {
                    @Override
                    public void accept(Bvn bvn) throws Exception {
                        bvnLiveData.setValue(bvn);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        bvnLiveData.setValue(null);
                    }
                });
        return bvnLiveData;
    }
    
    @Override
    protected void onCleared() {
        if (disposable != null)disposable.dispose();
    }
}
