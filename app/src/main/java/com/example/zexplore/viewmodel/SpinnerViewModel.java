package com.example.zexplore.viewmodel;

import android.app.Application;

import com.example.zexplore.model.AccountClass;
import com.example.zexplore.model.AccountType;
import com.example.zexplore.model.City;
import com.example.zexplore.model.Country;
import com.example.zexplore.model.Identification;
import com.example.zexplore.model.Occupation;
import com.example.zexplore.model.State;
import com.example.zexplore.model.Title;
import com.example.zexplore.model.ZenithBaseResponse;
import com.example.zexplore.network.NetworkClient;
import com.example.zexplore.repository.SpinnerRepository;
import com.example.zexplore.util.Constant;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class SpinnerViewModel extends AndroidViewModel {

    private SpinnerRepository repository;
    private MutableLiveData<List<Title>> titleLiveData;
    private MutableLiveData<List<State>> stateLiveData;
    private MutableLiveData<List<Occupation>> occupationLiveData;
    private MutableLiveData<List<City>> cityLiveData;
    private MutableLiveData<List<Country>> countryLiveData;
    private MutableLiveData<List<AccountClass>> accountClassLiveData;
    private MutableLiveData<List<AccountType>> accountTypeLiveData;
    private MutableLiveData<List<Identification>> identificationLiveData;
    private CompositeDisposable disposable;

    public SpinnerViewModel(Application application) {
        super(application);
        disposable = new CompositeDisposable();
        repository = new SpinnerRepository(application);
        titleLiveData = new MutableLiveData<>();
        stateLiveData = new MutableLiveData<>();
        occupationLiveData = new MutableLiveData<>();
        cityLiveData = new MutableLiveData<>();
        countryLiveData = new MutableLiveData<>();
        accountClassLiveData = new MutableLiveData<>();
        accountTypeLiveData = new MutableLiveData<>();
        identificationLiveData = new MutableLiveData<>();
        fetchLocalTitles();
        fetchLocalStates();
        fetchLocalOccupation();
        fetchLocalCountries();
        fetchLocalCities();
        fetchLocalAccountClass();
        fetchLocalAccountTypes();
        fetchLocalIdentification();
        getLocalAccountCategory();
    }

    public LiveData<List<AccountClass>> getAccountClass(){
        return accountClassLiveData;
    }

    public LiveData<List<AccountClass>> getLocalAccountCategory(){
        final MutableLiveData<List<AccountClass>> liveData = new MutableLiveData<>();

        disposable.add(repository.getLocalAccountClass()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<AccountClass>>() {
                    @Override
                    public void onSuccess(List<AccountClass> accountClassList) {
                        if (accountClassList != null && accountClassList.size() > 0){
                            liveData.setValue(accountClassList);
                            accountClassLiveData = liveData;
                        } else {
                            fetchRemoteAccountClass();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                })
        );
        return accountClassLiveData;
    }
    private void fetchLocalAccountClass(){
        disposable.add(repository.getLocalAccountClass()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<AccountClass>>() {
                    @Override
                    public void onSuccess(List<AccountClass> accountClassList) {
                        if (accountClassList != null && accountClassList.size() > 0){
                            accountClassLiveData.setValue(accountClassList);
                        } else {
                            fetchRemoteAccountClass();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                })
        );
    }

    private void fetchRemoteAccountClass(){
        disposable.add(repository.getRemoteAccountClass()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<ZenithBaseResponse<List<AccountClass>>>() {
                    @Override
                    public void onSuccess(ZenithBaseResponse<List<AccountClass>> response) {
                        if (response.getStatus()){
                            if (response.getResult().size() > 0){
                                repository.insertLocalAccountClass(response.getResult());
                                accountClassLiveData.setValue(response.getResult());
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) { }
                }));
    }

    public LiveData<List<AccountType>> getAccountTypes(){
        return accountTypeLiveData;
    }

    private void fetchLocalAccountTypes(){
        disposable.add(repository.getLocalAccountType()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<AccountType>>() {
                    @Override
                    public void onSuccess(List<AccountType> accountTypeList) {
                        if (accountTypeList != null && accountTypeList.size() > 0){
                            accountTypeLiveData.setValue(accountTypeList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        accountTypeLiveData.setValue(null);
                    }
                })
        );
    }

    public LiveData<List<Identification>> getIdentifications(){
        return identificationLiveData;
    }

    private void fetchLocalIdentification(){
        disposable.add(repository.getLocalIdentification()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Identification>>() {
                    @Override
                    public void onSuccess(List<Identification> identifications) {
                        if (identifications != null && identifications.size() > 0){
                            identificationLiveData.setValue(identifications);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        identificationLiveData.setValue(null);
                    }
                })
        );
    }

    public LiveData<List<Country>> getCountries(){
        return countryLiveData;
    }

    private void fetchLocalCountries(){
        disposable.add(repository.getLocalCountries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<List<Country>>() {
                    @Override
                    public void onSuccess(List<Country> countryList) {
                        if (countryList != null && countryList.size() > 0){
                            countryLiveData.setValue(countryList);
                            //fetchRemoteCountries();
                        } else {
                            fetchRemoteCountries();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        countryLiveData.setValue(null);
                    }
                }));
    }

    private void fetchRemoteCountries(){
        final List<Country> temp = new ArrayList<>();
        disposable.add(repository.getRemoteCountries()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<Country>() {
                    @Override
                    public void onSuccess(Country country) {
                        if (country.getStatus()){
                            for (String menu: country.getMenu()){
                                temp.add(new Country(menu));
                            }
                            if (temp.size() > 0){
                                repository.insertLocalCountries(temp);
                                countryLiveData.setValue(temp);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) { }
                }));
    }

    public LiveData<List<City>> getCities(){
        return cityLiveData;
    }

    private void fetchLocalCities(){
        disposable.add(repository.getLocalCities()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<List<City>>() {
                    @Override
                    public void onSuccess(List<City> cityList) {
                        if (cityList != null && cityList.size() > 0){
                            cityLiveData.setValue(cityList);
                            //fetchRemoteCities();
                        } else {
                            fetchRemoteCities();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        cityLiveData.setValue(null);
                    }
                }));
    }

    private void fetchRemoteCities(){
        final List<City> temp = new ArrayList<>();
        disposable.add(repository.getRemoteCities()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<City>() {
                    @Override
                    public void onSuccess(City city) {
                        if (city.getStatus()){
                            for (String menu: city.getMenu()){
                                temp.add(new City(menu));
                            }
                            if (temp.size() > 0){
                                repository.insertLocalCities(temp);
                                cityLiveData.setValue(temp);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {}
                }));
    }

    public LiveData<List<State>> getStates(){
        return stateLiveData;
    }

    private void fetchLocalStates(){
        disposable.add(repository.getLocalStates()
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribeOn(Schedulers.io())
                  .subscribeWith(new DisposableSingleObserver<List<State>>() {
                      @Override
                      public void onSuccess(List<State> stateList) {
                          if (stateList != null && stateList.size() > 0){
                              stateLiveData.setValue(stateList);
                              //fetchRemoteStates();
                          } else {
                              fetchRemoteStates();
                          }
                      }

                      @Override
                      public void onError(Throwable e) { }
                  }));
    }

    private void fetchRemoteStates(){
        final List<State> temp = new ArrayList<>();
        disposable.add(repository.getRemoteStates()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<State>() {
                    @Override
                    public void onSuccess(State state) {
                        if (state.getStatus()){
                            for (String menu: state.getMenu()){
                                temp.add(new State(menu));
                            }
                            if (temp.size() > 0){
                                repository.insertLocalStates(temp);
                                stateLiveData.setValue(temp);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) { }
                }));
    }

    public MutableLiveData<List<Title>> getTitles(){
        return titleLiveData;
    }

    private void fetchLocalTitles(){
        disposable.add(repository.getLocalTitles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<List<Title>>() {
                    @Override
                    public void onSuccess(List<Title> titleList) {
                        if (titleList != null && titleList.size() > 0){
                            titleLiveData.setValue(titleList);
                            //fetchRemoteTitles();
                        } else {
                            fetchRemoteTitles();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        titleLiveData.setValue(null);
                    }
                }));
    }

    private void fetchRemoteTitles(){
        final List<Title> temp = new ArrayList<>();
        disposable.add(repository.getRemoteTitles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<Title>() {
                    @Override
                    public void onSuccess(Title title) {
                        if (title.getStatus()){
                            for (String menu: title.getMenu()){
                                temp.add(new Title(menu));
                            }

                            if (temp.size() > 0){
                                repository.insertLocalTitles(temp);
                                titleLiveData.setValue(temp);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) { }
                }));
    }

    public MutableLiveData<List<Occupation>> getOccupations(){
        return occupationLiveData;
    }

    private void fetchLocalOccupation(){
        disposable.add(repository.getLocalOccupation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<List<Occupation>>() {
                    @Override
                    public void onSuccess(List<Occupation> occupationList) {
                        if (occupationList != null && occupationList.size() > 0){
                            occupationLiveData.setValue(occupationList);
                            //fetchRemoteOccupation();
                        } else {
                            fetchRemoteOccupation();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        occupationLiveData.setValue(null);
                    }
                }));
    }

    private void fetchRemoteOccupation(){
        final List<Occupation> temp = new ArrayList<>();
        disposable.add(repository.getRemoteOccupation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(new DisposableSingleObserver<Occupation>() {
                    @Override
                    public void onSuccess(Occupation occupation) {
                        if (occupation.getStatus()){
                            for (String menu: occupation.getMenu()){
                                temp.add(new Occupation(menu));
                            }
                            if (temp.size() > 0){
                                repository.insertLocalOccupation(temp);
                                occupationLiveData.setValue(temp);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) { }
                }));
    }

    public void refreshSpinner(){
        fetchRemoteAccountClass();
        fetchRemoteCities();
        fetchRemoteStates();
        fetchRemoteOccupation();
        fetchRemoteTitles();
        fetchRemoteCountries();
//        getLocalAccountCategory();
    }

}
