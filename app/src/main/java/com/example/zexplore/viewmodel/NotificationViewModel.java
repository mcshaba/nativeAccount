package com.example.zexplore.viewmodel;

import android.app.Application;

import com.example.zexplore.model.Notification;
import com.example.zexplore.repository.NotificationRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.SingleSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class NotificationViewModel extends AndroidViewModel {

    private MutableLiveData<Long> countLiveData;
    private MutableLiveData<List<Notification>> notificationsLiveData;
    private NotificationRepository notificationRepository;
    private CompositeDisposable disposable;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        notificationRepository = new NotificationRepository(application);
        disposable = new CompositeDisposable();
        countLiveData = new MutableLiveData<>();
        notificationsLiveData = new MutableLiveData<>();
        fetchNotificationCount();
    }

    public LiveData<Long> getNotificationCount(){
        fetchNotificationCount();
        return countLiveData;
    }

    private void fetchNotificationCount(){
        disposable.add(notificationRepository.getNotificationCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Long>() {
                    @Override
                    public void onSuccess(Long aLong) {
                        countLiveData.setValue(aLong);
                    }

                    @Override
                    public void onError(Throwable e) {}
                }));
    }

    public LiveData<List<Notification>> getNotifications(){
        fetchNotifications();
        return notificationsLiveData;
    }

    private void fetchNotifications(){
        disposable.add(notificationRepository.getNotifications()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<Notification>, SingleSource<List<Notification>>>() {
                    @Override
                    public SingleSource<List<Notification>> apply(List<Notification> notifications) throws Exception {
                        return notificationRepository.getForm(notifications);
                    }
                })
                .subscribeWith(new DisposableSingleObserver<List<Notification>>() {
                    @Override
                    public void onSuccess(List<Notification> notifications) {
                       notificationsLiveData.postValue(notifications);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                })
        );

        /*disposable.add(notificationRepository.getNotifications()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<List<Notification>, Observable<List<Notification>>>() {
                    @Override
                    public Observable<List<Notification>> apply(List<Notification> notifications) throws Exception {
                        return notificationRepository.getForm(notifications);
                    }
                })
                .subscribeWith(new DisposableObserver<List<Notification>>() {
                    @Override
                    public void onNext(List<Notification> notifications) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );*/
    }

    public void updateStatus(Notification notification){
        if (notification != null){
            notification.setStatus(0);
            notificationRepository.updateStatus(notification);
        }
    }

    public void insertNotification(Notification notification){
        notificationRepository.insertNotification(notification);
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
    }
}
