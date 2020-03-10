package com.example.zexplore.repository;

import android.app.Application;
//import android.arch.lifecycle.LiveData;
//
//import com.zenith.zxplore.database.ZenormsDatabase;
//import com.zenith.zxplore.model.Attachment;
//import com.zenith.zxplore.model.Form;
//import com.zenith.zxplore.model.dao.AttachmentDao;
//import com.zenith.zxplore.model.dao.FormDao;
//import com.zenith.zxplore.util.AppUtility;

import com.example.zexplore.database.ZenormsDatabase;
import com.example.zexplore.model.Attachment;
import com.example.zexplore.model.Form;
import com.example.zexplore.model.dao.AttachmentDAO;
import com.example.zexplore.model.dao.FormDao;
import com.example.zexplore.util.AppUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shaba.michael on 3/21/2019.
 */

public class FormRepository {

    private FormDao formDao;
    private AttachmentDAO attachmentDao;

    public FormRepository(Application application){
        ZenormsDatabase database = ZenormsDatabase.getDatabase(application);
        formDao = database.formDao();
        attachmentDao = database.attachmentDao();
    }

    public LiveData<List<Form>> getForms(){
        return formDao.getForms();
    }

    public LiveData<List<Form>> getSavedForms(){
        return formDao.getSavedForms();
    }

    public LiveData<List<Form>> getCompletedForms(){
        return formDao.getCompletedForms();
    }

    public LiveData<List<Form>> getSentForms(){
        return formDao.getSentForms();
    }

    public LiveData<List<Form>> getRejectedForms(){
        return formDao.getRejectedForms();
    }

    public List<Form> getSyncedForms(){
        return formDao.getSyncedForms();
    }

    public List<Form> getUnSyncedForms(){
        return formDao.getUnSyncedForms();
    }

    public void insertForm(final Form form){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                long id = formDao.insertForm(form);
                if (form.getAttachments() != null && form.getAttachments().size() > 0){
                    List<Attachment> attachments = getAttachment(form.getAttachments(), id);
                    attachmentDao.insertAttachments(attachments);
                }
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public LiveData<Form> getForm(long id){
        return formDao.getFormById(id);
    }

    public void deleteForm(final Form form){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                formDao.deleteForm(form);
                attachmentDao.deleteFormAttachment(form.getId());
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public List<Attachment> getAttachment(List<Attachment> list, long id){
        List<Attachment> temp = new ArrayList<>();
        try {
            for (Attachment attachment : list){
                attachment.setFormId(id);
                if (attachment.getImage() != null){
                    String base64 = AppUtility.encodeToBase64(attachment.getImage());
                    attachment.setEncodedImage(base64);
                }
                temp.add(attachment);
            }
        } catch (IOException io){
            io.printStackTrace();
        }

        return temp;
    }

    public LiveData<List<Attachment>> getAttachments(long formId){
        return attachmentDao.getAttachments(formId);
    }

    public List<Attachment> getAllAttachments(long formId){
        return attachmentDao.getAllAttachments(formId);
    }

    public void deleteAttachment(final long id){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                attachmentDao.deleteAttachment(id);
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

}
