package com.example.zexplore.repository;

import android.app.Application;


import com.example.zexplore.database.ZenormsDatabase;
import com.example.zexplore.model.Attachment;
import com.example.zexplore.model.Form;
import com.example.zexplore.model.Notification;
import com.example.zexplore.model.dao.AttachmentDAO;
import com.example.zexplore.model.dao.FormDao;
import com.example.zexplore.model.dao.NotificationDao;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class NotificationRepository {

   private NotificationDao notificationDao;
   private FormDao formDao;
   private AttachmentDAO attachmentDao;

    public NotificationRepository(Application application) {
        ZenormsDatabase database = ZenormsDatabase.getDatabase(application);
        notificationDao = database.notificationDao();
        formDao = database.formDao();
        attachmentDao = database.attachmentDao();
    }

    public Single<Long> getNotificationCount(){
        return notificationDao.getNotificationCount();
    }

    public Single<List<Notification>> getNotifications(){
        return notificationDao.getNotifications();
    }

    public Single<List<Notification>> getForm(final List<Notification> notifications){
        return Single.create(new SingleOnSubscribe<List<Notification>>() {
            @Override
            public void subscribe(SingleEmitter<List<Notification>> emitter) throws Exception {
                List<Notification> temp = new ArrayList<>();
                if (!emitter.isDisposed()){
                    if (notifications.size() > 0){
                        for (Notification notification: notifications){
                            Form form = formDao.getNotifiedForm(notification.getFormId());
                            if (form != null){
                                List<Attachment> attachments = attachmentDao.getAttachment(notification.getFormId());
                                form.setAttachments(attachments);
                                notification.setForm(form);
                                temp.add(notification);
                            } else {
                                temp.add(notification);
                            }
                        }
                    }
                    emitter.onSuccess(temp);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    public void updateStatus(final Notification notification){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                notificationDao.insertNotification(notification);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void insertNotification(final Notification notification){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                notificationDao.insertNotification(notification);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

}
