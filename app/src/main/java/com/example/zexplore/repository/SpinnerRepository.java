package com.example.zexplore.repository;

import android.app.Application;

import com.example.zexplore.database.ZenormsDatabase;
import com.example.zexplore.model.AccountClass;
import com.example.zexplore.model.AccountType;
import com.example.zexplore.model.City;
import com.example.zexplore.model.Country;
import com.example.zexplore.model.Identification;
import com.example.zexplore.model.Occupation;
import com.example.zexplore.model.State;
import com.example.zexplore.model.Title;
import com.example.zexplore.model.ZenithBaseResponse;
import com.example.zexplore.model.dao.SpinnerDao;
import com.example.zexplore.network.NetworkClient;
import com.example.zexplore.network.NetworkInterface;
import com.example.zexplore.util.AppSettings;
import com.example.zexplore.util.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class SpinnerRepository {

    private AppSettings appSettings;
    private String token = "";
    private NetworkInterface networkInterface;
    private SpinnerDao spinnerDao;
    private Application application;

    public SpinnerRepository(Application application) {
        networkInterface = NetworkClient.getNetworkInterface(Constant.ZENITH_BASE_URL);
        ZenormsDatabase database = ZenormsDatabase.getDatabase(application);
        this.application = application;
        spinnerDao = database.spinnerDao();
        appSettings = AppSettings.getInstance(application);

        if(appSettings != null)
            token = "Bearer " + appSettings.getUser().getToken();

    }

    public Single<List<Country>> getLocalCountries(){
        return spinnerDao.getCountries();
    }

    public Single<Country> getRemoteCountries(){
        return networkInterface.getCountries(token);
    }

    public void insertLocalCountries(final List<Country> countryList){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                deleteLocalCountries();
                spinnerDao.insertCountries(countryList);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteLocalCountries(){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                spinnerDao.deleteCountries();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public Single<List<City>> getLocalCities(){
        return spinnerDao.getCities();
    }

    public Single<City> getRemoteCities(){
        return networkInterface.getCities(token);
    }

    public void insertLocalCities(final List<City> cityList){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                deleteLocalCities();
                spinnerDao.insertCities(cityList);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteLocalCities(){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                spinnerDao.deleteCities();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public Single<List<State>> getLocalStates(){
        return spinnerDao.getStates();
    }

    public Single<State> getRemoteStates(){
        return networkInterface.getStates(token);
    }

    public void insertLocalStates(final List<State> stateList){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                deleteLocalStates();
                spinnerDao.insertStates(stateList);
            }
        }).observeOn(AndroidSchedulers.mainThread())
          .subscribeOn(Schedulers.io())
          .subscribe();
    }

    public void deleteLocalStates(){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                spinnerDao.deleteStates();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public Single<Title> getRemoteTitles(){
        return networkInterface.getTitles(token);
    }

    public Single<List<Title>> getLocalTitles(){
        return spinnerDao.getTitles();
    }

    public void insertLocalTitles(final List<Title> titleList){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                deleteLocalTitles();
                spinnerDao.insertTitles(titleList);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteLocalTitles(){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                spinnerDao.deleteTitles();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public Single<List<Occupation>> getLocalOccupation(){
        return spinnerDao.getOccupations();
    }

    public Single<Occupation> getRemoteOccupation(){
        return networkInterface.getOccupations(token);
    }

    public void insertLocalOccupation(final List<Occupation> occupationList){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                deleteLocalOccupation();
                spinnerDao.insertOccupations(occupationList);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteLocalOccupation(){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                spinnerDao.deleteOccupations();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public Single<List<AccountClass>> getLocalAccountClass(){
        return spinnerDao.getAccountClass();
//        return Single.fromCallable(new Callable<List<AccountClass>>() {
//            @Override
//            public List<AccountClass> call() throws Exception {
//                List<AccountClass> accountClassList = null;
//                try {
//                    InputStream inputStream = application.getAssets().open("account-class.json");
//                    int size = inputStream.available();
//                    byte[] buffer = new byte[size];
//                    inputStream.read(buffer);
//                    inputStream.close();
//
//                    String bufferString = new String(buffer);
//                    Gson gson = new Gson();
//                    Type accountListType = new TypeToken<ArrayList<AccountClass>>(){}.getClass();
//                    accountClassList = gson.fromJson(bufferString, accountListType);
//                } catch (IOException io) {
//                    io.printStackTrace();
//                }catch (Exception io) {
//                    io.printStackTrace();
//                }
//                return accountClassList;
//            }
//        }).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<ZenithBaseResponse<List<AccountClass>>> getRemoteAccountClass(){
        return networkInterface.getAccountClass(token);
    }

    public void insertLocalAccountClass(final List<AccountClass> accountClasses){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                deleteLocalAccountClass();
                spinnerDao.insertAccountClass(accountClasses);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void deleteLocalAccountClass(){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                spinnerDao.deleteAccountClass();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public Single<List<AccountType>> getLocalAccountType(){
        return Single.fromCallable(new Callable<List<AccountType>>() {
            @Override
            public List<AccountType> call() throws Exception {
                List<AccountType> accountTypeList = null;
                try {
                    InputStream inputStream = application.getAssets().open("account-type.json");
                    int size = inputStream.available();
                    byte[] buffer = new byte[size];
                    inputStream.read(buffer);
                    inputStream.close();

                    String bufferString = new String(buffer);
                    Gson gson = new Gson();
                    Type accountListType = new TypeToken<ArrayList<AccountType>>(){}.getType();
                    accountTypeList = gson.fromJson(bufferString, accountListType);
                } catch (IOException io) {
                    io.printStackTrace();
                }catch (Exception io) {
                    io.printStackTrace();
                }
                return accountTypeList;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Identification>> getLocalIdentification(){
        return Single.fromCallable(new Callable<List<Identification>>() {
            @Override
            public List<Identification> call() throws Exception {
                List<Identification> identificationList = null;
                try {
                    InputStream inputStream = application.getAssets().open("means-of-id.json");
                    int size = inputStream.available();
                    byte[] buffer = new byte[size];
                    inputStream.read(buffer);
                    inputStream.close();

                    String bufferString = new String(buffer);
                    Gson gson = new Gson();
                    Type identificationType = new TypeToken<ArrayList<Identification>>(){}.getType();
                    identificationList = gson.fromJson(bufferString, identificationType);
                } catch (IOException io){
                    io.printStackTrace();
                } catch (Exception e){
                    e.printStackTrace();
                }
                return identificationList;
            }
        });
    }
}
