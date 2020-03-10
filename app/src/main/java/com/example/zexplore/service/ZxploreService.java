package com.example.zexplore.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.zexplore.R;
import com.example.zexplore.activity.MainActivity;
import com.example.zexplore.model.Attachment;
import com.example.zexplore.model.BaseResponse;
import com.example.zexplore.model.Form;
import com.example.zexplore.model.LoginResponse;
import com.example.zexplore.model.Notification;
import com.example.zexplore.model.SignatoryDetails;
import com.example.zexplore.model.SubmitForm;
import com.example.zexplore.network.NetworkClient;
import com.example.zexplore.network.NetworkInterface;
import com.example.zexplore.repository.FormRepository;
import com.example.zexplore.repository.NotificationRepository;
import com.example.zexplore.util.AppSettings;
import com.example.zexplore.util.AppUtility;
import com.example.zexplore.util.Constant;
import com.example.zexplore.util.CryptLib;
import com.example.zexplore.util.ZxploreApp;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.core.app.NotificationCompat;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.example.zexplore.util.Constant.ZENITH_BASE_URL;

public class ZxploreService extends Service {

    private NetworkInterface networkInterface = null;
    private Call<BaseResponse> responseCall = null;
    private CompositeDisposable disposable;
    private NotificationRepository notificationRepository;
    private AppSettings appSettings;
    private String token;

    public ZxploreService() {
    }

    private boolean cBusy;
    private int cSleepCount = 0, cSleepLimit = 10;
    private Timer cTimer;
    private TimerTask cTask = new TimerTask() {
        @Override
        public void run() {
            cSleepCount += 1;
            try {
                if (cSleepCount >= cSleepLimit) {
                    cSleepCount = 0;
                    if (!cBusy) {
                        if (AppUtility.isNetworkAvailable(ZxploreService.this)) {
                            final FormRepository repository = new FormRepository(getApplication());
                            List<Form> formList = repository.getUnSyncedForms();
                            if (formList != null && formList.size() > 0)
                                createAccount();
                        }
                    }
                }
            } catch (Throwable e) {
                e.printStackTrace();
                cBusy = false;
            }
        }
    };

    private boolean vBusy;
    private int vSleepCount = 0, vSleepLimit = 30;
    private Timer vTimer;
    private TimerTask vTask = new TimerTask() {
        @Override
        public void run() {
            Log.w("TAG", "Timer task for Checking Account Status " + vSleepCount + " " + vSleepLimit);
            vSleepCount += 1;
            try {
                if (vSleepCount >= vSleepLimit) {
                    vSleepCount = 0;
                    if (!vBusy) {
                        if (AppUtility.isNetworkAvailable(ZxploreService.this)) {
                            Log.w("TAG", "Checking Account Status Job has Executed");

                            final FormRepository repository = new FormRepository(getApplication());
                            List<Form> formList = repository.getSyncedForms();
                            if (formList != null && formList.size() > 0)
                                checkAccountStatus();
                        }
                    }
                }
            } catch (Throwable e) {

                if(e instanceof HttpException){
                    switch (((HttpException) e).response().code()){

                        case 400:
                            Log.d("TAG", e.getMessage());


                    }

                }
                HttpException error = (HttpException) e;
                String errorBody =  error.response().errorBody().toString();
                Log.d("ERROR", errorBody);
                vBusy = false;
                e.printStackTrace();
                vBusy = false;
            }
        }
    };
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        notificationRepository = new NotificationRepository(getApplication());

        cTimer = new Timer("CreateAccountTimer");
        cTimer.schedule(cTask, 1000L, 1000L);

        vTimer = new Timer("VerifyAccountTimer");
        vTimer.schedule(vTask, 1000L, 1000L);

        disposable = new CompositeDisposable();
    }

    @Override
    public void onDestroy() {


        cTimer.cancel();
        cTimer = null;

        vTimer.cancel();
        vTimer = null;

        if (networkInterface != null) networkInterface = null;
        if (responseCall != null) responseCall.cancel();
        if (disposable != null) disposable.dispose();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if(cTimer!= null) cTimer.cancel();
        cTimer = null;
        if(vTimer!= null)
            vTimer.cancel();
        vTimer = null;

        if (networkInterface != null) networkInterface = null;
        if (responseCall != null) responseCall.cancel();
        if (disposable != null) disposable.dispose();

        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        cTimer.cancel();
        cTimer = null;

        vTimer.cancel();
        vTimer = null;

        if (networkInterface != null) networkInterface = null;
        if (responseCall != null) responseCall.cancel();
        if (disposable != null) disposable.dispose();

        super.onLowMemory();
    }

    private void createAccount() {
        cBusy = true;
        if(networkInterface != null)
            NetworkClient.resetNetworkClient();
        networkInterface = NetworkClient.getNetworkInterface(ZENITH_BASE_URL);

        final FormRepository repository = new FormRepository(getApplication());
        List<Form> formList = repository.getUnSyncedForms();

        if (formList != null && formList.size() > 0) {
            for (final Form form : formList) {
                List<Attachment> attachments = repository.getAllAttachments(form.getId());
                List<Attachment> cleanAttachment = cleanAttachment(attachments);
                form.setAttachments(cleanAttachment);

                SubmitForm compileForm = compileForm(form);
                appSettings = AppSettings.getInstance(getApplicationContext());

                if(appSettings != null)
                    token = "Bearer " + appSettings.getUser().getToken();

                disposable.add(networkInterface.saveAccount(token, compileForm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<LoginResponse>>() {
                    @Override
                    public void onSuccess(Response<LoginResponse> response) {
                        if(response.code() == 400){
                            Gson gson = new Gson();

                            LoginResponse response1 = null;
                            try {
                                response1 = gson.fromJson(response.errorBody().string(), LoginResponse.class);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if(response1.getData()!= null)
                                formSentCompleted(repository, form, response1.getData().getBatchId());

                        } else if(response.code() == 200) {
                            if (response.body().getStatus() || response.body().getMessage().equals("Success")) {
                                if (response.body().getData().getBatchId() != null) {
                                    formSentCompleted(repository, form, response.body().getData().getBatchId());
                                    cBusy = false;
                                }
                            } else {
                                cBusy = false;
                            }
                        }else if(response.code() == 502) {
                            Toast.makeText(getApplicationContext(),"ZENITH SERVER CANNOT BE REACHED", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        cBusy = false;
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException)e).response().errorBody();
                            Toast.makeText(ZxploreService.this, responseBody.toString()+"", Toast.LENGTH_SHORT).show();

                        } else if (e instanceof SocketTimeoutException) {
                            Toast.makeText(ZxploreService.this, "Check your Network connection", Toast.LENGTH_SHORT).show();

                            //view.onTimeout();
                        } else if (e instanceof IOException) {
                            Toast.makeText(ZxploreService.this, "Check your Network connection", Toast.LENGTH_SHORT).show();

                            //view.onNetworkError();
                        } else {
                            Toast.makeText(ZxploreService.this, "Zenith Server down", Toast.LENGTH_SHORT).show();

                            //view.onUnknownError(e.getMessage());
                        }
                    }
                }));

            }
        } else {
            cBusy = false;
        }
    }

    private void checkAccountStatus() {
        vBusy = true;
        if(networkInterface != null)
            NetworkClient.resetNetworkClient();
        networkInterface = NetworkClient.getNetworkInterface(ZENITH_BASE_URL);

        final FormRepository repository = new FormRepository(getApplication());
        List<Form> formList = repository.getSyncedForms();

        appSettings = AppSettings.getInstance(getApplicationContext());

        if(appSettings != null) {
            token = "Bearer " + appSettings.getUser().getToken();
        }
        if (formList != null && formList.size() > 0) {
            for (final Form form : formList) {
                disposable.add(networkInterface.verifyReferenceId(token, form.getRefId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<Response<LoginResponse>>() {
                            @Override
                            public void onSuccess(Response<LoginResponse> response) {
                                vBusy = false;
                                if( response != null) {
                                    if (response.code() == 400) {
                                        Gson gson = new Gson();

                                        LoginResponse response1 = null;
                                        try {
                                            response1 = gson.fromJson(response.errorBody().string(), LoginResponse.class);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        if(response1 == null)
                                            return;
                                        if(response1.getData() == null)
                                            return;
                                        if (response1.getStatus()){



                                            if (response1.getData().getStatuss().equals("APPROVED")) {
                                                accountApproved(repository, form, response1.getData().getAccountNumber());
                                            } else if (response1.getData().getStatuss().equals("REJECTED")) {
                                                accountRejected(repository, form, "Account creation request was rejected.");
                                            }
                                        } else{
                                            if (response1.getData().getResponseMessage().equals("REJECTED")) {
                                                accountRejected(repository, form, "Account creation request was rejected.");
                                            }
                                        }
                                    } else if (response.code() == 200) {
                                        if ( response.body().getMessage().equals("Success")) {
                                            if (response.body().getStatus()) {

                                                if (response.body().getData().getStatuss().equals("APPROVED")) {
                                                    accountApproved(repository, form, response.body().getData().getAccountNumber());
                                                } else if (response.body().getData().getStatuss().equals("REJECTED")) {
                                                    accountRejected(repository, form, "Account creation request was rejected.");
                                                }
                                            } else {
                                                if (response.body().getData().getResponseMessage().equals("REJECTED")) {
                                                    accountRejected(repository, form, "Account creation request was rejected.");
                                                }
                                            }
                                        } else if(response.body().getData().getResponseCode().equals("1210") || response.body().getMessage().equals("Account Creation Pending")){
                                            if(response.body().getData().getStatuss().equals("REJECTED")){
                                                accountRejected(repository, form, "Account creation request was rejected.");
                                            }
                                            vBusy = false;
                                        }
                                    } else if (response.code() == 502) {
                                        Toast.makeText(getApplicationContext(), "ZENITH SERVER CANNOT BE REACHED", Toast.LENGTH_SHORT).show();
                                    }
                                }








                            }



                            @Override
                            public void onError(Throwable e) {

                                cBusy = false;
                                if (e instanceof HttpException) {
                                    ResponseBody responseBody = ((HttpException)e).response().errorBody();
                                    Toast.makeText(ZxploreService.this, responseBody.toString()+"", Toast.LENGTH_SHORT).show();

                                } else if (e instanceof SocketTimeoutException) {
                                    Toast.makeText(ZxploreService.this, "Check your Network connection", Toast.LENGTH_SHORT).show();

                                    //view.onTimeout();
                                } else if (e instanceof IOException) {
                                    Toast.makeText(ZxploreService.this, "Check your Network connection", Toast.LENGTH_SHORT).show();

                                    //view.onNetworkError();
                                } else {
                                    Toast.makeText(ZxploreService.this, "Zenith Server down", Toast.LENGTH_SHORT).show();

                                    //view.onUnknownError(e.getMessage());
                                }
                            }
                        })
                );

            }
        } else {
            vBusy = false;
        }
    }

    private void formSentCompleted(FormRepository repository, Form form, String batchId) {
        form.setSync(Constant.SENT_FORM);
        form.setStatus(Constant.PENDING_ACCOUNT);
        form.setBatchId(batchId);
        repository.insertForm(form);

        setNotification("Zxplore", form.getAccountTypeName().toUpperCase() + " FORM WAS SENT SUCCESSFULLY");

        addNotification(" Form Sent Successfully", "Form was sent and awaiting approval",
                Constant.SENT_FORM, form.getId());
    }

    private void accountApproved(FormRepository repository, Form form, String accountNumber) {
        form.setStatus(Constant.APPROVED_ACCOUNT);
        form.setAccountNumber(accountNumber);
        repository.insertForm(form);
        String accountName = "";

        try {
            CryptLib _crypt = new CryptLib();
            String key = CryptLib.SHA256(Constant.ENCRYPT_KEY, 32); //32 bytes = 256 bit
            String iv = Constant.INNITIALISING_VECTOR_KEY; //16 bytes = 128 bit
            accountName = _crypt.decrypt(form.getAccountName(), key, iv);

        } catch (Exception e) {
            e.printStackTrace();
        }
        setNotification("Zxplore", accountName + " WAS CREATED SUCCESSFULLY");

        addNotification("Account was created Successfully", "Account Number : " + form.getAccountNumber(),
                Constant.APPROVED_ACCOUNT, form.getId());
    }

    private void accountRejected(FormRepository repository, Form form, String message) {
        form.setSync(Constant.REJECTED_FORM);
        form.setStatus(Constant.REJECTED_ACCOUNT);
        repository.insertForm(form);

        setNotification("Zxplore", message);

        addNotification(message, "Kindly review the form & resend it.",
                Constant.REJECTED_ACCOUNT, form.getId());
    }


    NotificationCompat.Builder builder = null;

    public void setNotification(String title, String message) {
        //Define sound URI
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(soundUri)
                .setAutoCancel(true);
        int NOTIFICATION_ID = (int)System.currentTimeMillis();

        Intent targetIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());
    }

    private SubmitForm compileForm(Form form) {
        SignatoryDetails signatoryDetails = new SignatoryDetails(form);
        List<SignatoryDetails> detailsList = new ArrayList<>();
        detailsList.add(signatoryDetails);
        return new SubmitForm(form, detailsList);
    }

    private List<Attachment> cleanAttachment(List<Attachment> attachmentList) {
        List<Attachment> temp = new ArrayList<>();
        for (Attachment attachment : attachmentList) {
            String cleanAttachment = attachment.getEncodedImage().replace("\n", "");
            attachment.setEncodedImage(cleanAttachment);
            temp.add(attachment);
        }
        return temp;
    }

    public void addNotification(String title, String message, int type,
                                long formId) {
        Notification notification = new Notification();
        notification.setTitle(title);
        notification.setMessage(message);
        notification.setType(type);
        notification.setFormId(formId);
        notification.setStatus(1);
        notification.setCreatedOn(AppUtility.getDate());
        notificationRepository.insertNotification(notification);
    }
}
