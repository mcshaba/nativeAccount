package com.example.zexplore.viewmodel;

import android.app.Application;

import com.example.zexplore.ZexploreApp;
import com.example.zexplore.model.Attachment;
import com.example.zexplore.model.Form;
import com.example.zexplore.model.SubmitForm;
import com.example.zexplore.repository.FormRepository;
import com.example.zexplore.util.AppUtility;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by shaba.michael on 3/22/2019.
 */

public class FormViewModel extends ViewModel {

    private FormRepository repository;
    private MutableLiveData<Form> formLiveData;

    private MutableLiveData<Boolean> iseditMode;
    private SubmitForm submitForm;

    private CompositeDisposable disposable;

//    public FormViewModel(Application application) {
//        super(application);
//        disposable = new CompositeDisposable();
//        repository = new FormRepository(application);
//        formLiveData = new MutableLiveData<>(new Form());
//    }

    public FormViewModel() {
        disposable = new CompositeDisposable();
        repository = new FormRepository(ZexploreApp.getInstance());
        formLiveData = new MutableLiveData<>(new Form());
        iseditMode = new MutableLiveData<>(false);
    }

    public MutableLiveData<Form> getForm() {

        if (formLiveData == null) {
            formLiveData = new MutableLiveData<>(new Form());
        }

        return formLiveData;
    }



    public MutableLiveData<Boolean> getIseditMode(){
        if(iseditMode == null){
            iseditMode = new MutableLiveData<>(false);
        }
        return  iseditMode;
    }

    public void setIseditMode(Boolean iseditMode){
        this.iseditMode.postValue(iseditMode);
    }

    public void setForm(Form form) {
        this.formLiveData.postValue(form);
    }

    private void loadForm() {
        Form form = new Form();
        setForm(form);
    }

    public LiveData<Form> getForm(long id) {
        return repository.getForm(id);
    }

    public LiveData<List<Form>> getSavedForms() {
        return repository.getSavedForms();
    }

    public LiveData<List<Form>> getCompletedForms() {
        return repository.getCompletedForms();
    }

    public LiveData<List<Form>> getRejectedForms() {
        return repository.getRejectedForms();
    }

    public LiveData<List<Form>> getSentForms() {
        return repository.getSentForms();
    }

    public LiveData<List<Form>> getForms() {
        return repository.getForms();
    }

    public void insertForm(Form form) {
        repository.insertForm(form);
    }

    public void deleteForm(Form form) {
        List<Attachment> attachments = form.getAttachments();
        if (attachments != null && attachments.size() > 0) {
            for (Attachment attachment : attachments) {
                AppUtility.deleteFile(attachment.getImage());
            }
        }
        repository.deleteForm(form);
    }

    public LiveData<List<Attachment>> getAttachments(long formId) {
        return repository.getAttachments(formId);
    }

    public void deleteAttachment(long id) {
        repository.deleteAttachment(id);
    }
}
