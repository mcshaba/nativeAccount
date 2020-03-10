package com.example.zexplore.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.example.zexplore.fragment.CreateAccountFragment.IS_EDIT_MODE;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zexplore.R;
import com.example.zexplore.fragment.BottomNavigationDrawerFragment;
import com.example.zexplore.fragment.form.DatePickerFragment;
import com.example.zexplore.fragment.form.IdCardUploadFragment;
import com.example.zexplore.fragment.form.PersonalInformationFragment;
import com.example.zexplore.fragment.form.SignatoryFragment;
import com.example.zexplore.fragment.form.UploadPassportFragment;
import com.example.zexplore.fragment.form.UploadUtilityFragment;
import com.example.zexplore.listener.FragmentNavigationListener;
import com.example.zexplore.model.Attachment;
import com.example.zexplore.model.Bvn;
import com.example.zexplore.model.Form;
import com.example.zexplore.model.LoginResponse;
import com.example.zexplore.model.SignatoryDetails;
import com.example.zexplore.model.SubmitForm;
import com.example.zexplore.model.formModels.PersonalInformationModel;
import com.example.zexplore.network.NetworkClient;
import com.example.zexplore.network.NetworkInterface;
import com.example.zexplore.repository.FormRepository;
import com.example.zexplore.service.ConnectivityReceiver;
import com.example.zexplore.service.ZxploreService;
import com.example.zexplore.util.AppSettings;
import com.example.zexplore.util.AppUtility;
import com.example.zexplore.util.Constant;
import com.example.zexplore.util.CryptLib;
import com.example.zexplore.viewmodel.BvnViewModel;
import com.example.zexplore.viewmodel.FormViewModel;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.soundcloud.android.crop.Crop;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import static com.example.zexplore.fragment.form.UploadPassportFragment.TYPE;
import static com.example.zexplore.util.Constant.ZENITH_BASE_URL;

public class MainActivity extends AppCompatActivity implements FragmentNavigationListener, UploadPassportFragment.ItemClickListener,
        UploadUtilityFragment.ItemClickListener, IdCardUploadFragment.ItemClickListener, DatePickerFragment.DateSelectedListener {

    FloatingActionButton floatingActionButton;
    private CompositeDisposable disposable;
    private NetworkInterface networkInterface = null;
    BottomAppBar bottomAppBar;

    private AppSettings appSettings;
    private String token = " ";
    private static final int CAMERA = 1, GALLERY = 2;

    private String type;
    private BroadcastReceiver broadcastReceiver;
    private FormViewModel formViewModel;

    Snackbar snackbar;

    Boolean currentMode;

    NavController navController;
    Form form;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        disposable = new CompositeDisposable();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        appSettings = AppSettings.getInstance(MainActivity.this);

        checkNetworkState();
        floatingActionButton = findViewById(R.id.fab);

        bottomAppBar = findViewById(R.id.bar);

        navController = Navigation.findNavController(MainActivity.this, R.id.nav_host_fragment);

        Navigation.setViewNavController(floatingActionButton, navController);


        floatingActionButton.setOnClickListener(v -> {
                    IS_EDIT_MODE = false;

                    form = formViewModel.getForm().getValue();

                    if (form != null) {
                        form.setSync(Constant.COMPLETED_FORM);
                        form.setStatus(Constant.PENDING_ACCOUNT);
                        form.setImageIdentifier(System.currentTimeMillis());
                    }
                    form = new Form();
                    formViewModel.getForm().setValue(form);
                    NavOptions.Builder navBuilder = new NavOptions.Builder();
                    NavOptions navOptions = navBuilder.setPopUpTo(R.id.mainFragment, true).build();
                    navController.navigate(R.id.action_global_createAccountFragment);

                }


        );

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {

            if (destination.getId() == R.id.createAccountFragment) {

                floatingActionButton.setVisibility(View.GONE);
                bottomAppBar.setNavigationIcon(null);
                bottomAppBar.setVisibility(View.GONE);

            } else if (destination.getId() == R.id.loginFragment) {
                floatingActionButton.setVisibility(View.GONE);
                bottomAppBar.setVisibility(View.GONE);
            } else {
                bottomAppBar.setVisibility(View.VISIBLE);
                bottomAppBar.setNavigationIcon(R.drawable.zexplore_filter);
                floatingActionButton.setVisibility(View.VISIBLE);

            }

        });

        bottomAppBar.setNavigationOnClickListener(v -> {
            // Handle the navigation click by showing a BottomDrawer etc.
            BottomSheetDialogFragment bottomSheetDialogFragment = new BottomNavigationDrawerFragment();
            bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (IS_EDIT_MODE) {
            formViewModel = ViewModelProviders.of(this).get("edit", FormViewModel.class);

            form = formViewModel.getForm().getValue();

        } else {
            formViewModel = ViewModelProviders.of(this).get("create", FormViewModel.class);
        }
    }

    public void checkNetworkState() {
        IntentFilter intentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int[] type = {ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE};
                if (ConnectivityReceiver.isNetworkAvailable(context, type)) {
                    checkNetworkConnection(1);
                } else {
                    checkNetworkConnection(2);
                }
            }
        };

        //register the receiver
        registerReceiver(broadcastReceiver, intentFilter);
    }

    private void checkNetworkConnection(int status) {


        int marginSide = 0;
        int marginBottom = 550;

        String message;

        if (status == 1)
            message = "You are currently online.";
        else
            message = "You seem to have a bad network connection.";

        Snackbar snackbar = Snackbar.make(findViewById(R.id.container), message, Snackbar.LENGTH_LONG);
//        snackbar.setAction("Cancel", v -> snackbar.dismiss());
        // Changing message text color
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.amberWarning));

        View snackbarView = snackbar.getView();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();


        params.setMargins(
                params.leftMargin + marginSide,
                params.topMargin,
                params.rightMargin + marginSide,
                params.bottomMargin + marginBottom
        );

        snackbarView.setLayoutParams(params);
        snackbar.show();


    }


    @Override
    public void onSubmitFormClicked(Form form1) {
        this.form = formViewModel.getForm().getValue();


        //workaround for edit mode
        if (this.form != null && this.form.getFirstName() == null) {
            this.form = form1;
            formViewModel.getForm().setValue(form1);
            this.form = formViewModel.getForm().getValue();
        }

        //workaround for edit mode
        if (this.form == null) {
            this.form = form1;
            formViewModel.getForm().setValue(form1);
            this.form = formViewModel.getForm().getValue();
        }
        AppSettings appSettings = AppSettings.getInstance(this);

        String accountName = "", dateofbirth = "", refId = "";
        String firstName = "", middleName = "", lastName = "", addressLine1 = "", addressLine2 = "",
                emailAddress = "", phoneNumber = "", nextOfKin = "", maidenName = "", bvn = "";
        try {

            CryptLib _crypt = new CryptLib();
            String key = CryptLib.SHA256(Constant.ENCRYPT_KEY, 32); //32 bytes = 256 bit
            String iv = Constant.INNITIALISING_VECTOR_KEY; //16 bytes = 128 bit
            firstName = _crypt.encrypt(this.form.getFirstName(), key, iv);
            middleName = _crypt.encrypt(this.form.getMiddleName(), key, iv);
            lastName = _crypt.encrypt(this.form.getLastName(), key, iv);
            String fullName = this.form.getFirstName() + " " + this.form.getMiddleName() + " " + this.form.getLastName();

            if (fullName.length() > 35) {
                char mid = 0;
                if (!TextUtils.isEmpty(middleName))
                    mid = this.form.getMiddleName().charAt(0);

                fullName = this.form.getFirstName() + " " + mid + ". " + this.form.getLastName();
                accountName = _crypt.encrypt(fullName, key, iv);

            } else {
                accountName = _crypt.encrypt(fullName, key, iv);
            }
            Date date = new Date();

            long time = date.getTime();

            // Timestamp ts = new Timestamp(date.getTime());
//            System.out.println("Current Time Stamp: " + ts);

            if (TextUtils.isEmpty(form.getRefId()) || form.getRefId() == null) {
                refId = appSettings.getUser().getEmployeeId() + String.valueOf(time).substring(7);

            } else {
                refId = form.getRefId();
            }
            System.out.println("Time in Milliseconds: refid" + refId);

            addressLine1 = _crypt.encrypt(form.getAddress1(), key, iv);

            if (!TextUtils.isEmpty(addressLine2))
                addressLine2 = _crypt.encrypt(form.getAddress2(), key, iv);
            emailAddress = _crypt.encrypt(form.getEmailAddress(), key, iv);
            String datee = form.getDateOfBirth();
            Log.d("DATE", datee);
            dateofbirth = _crypt.encrypt(form.getDateOfBirth(), key, iv);
            bvn = _crypt.encrypt(form.getBvn(), key, iv);
            phoneNumber = _crypt.encrypt(form.getPhoneNumber(), key, iv);
            nextOfKin = _crypt.encrypt(form.getNextOfKin(), key, iv);
            maidenName = _crypt.encrypt(form.getMotherMaidenName(), key, iv);
            _crypt.encrypt(form.getAttachments().toString(), key, iv);
        } catch (Exception e) {
            e.printStackTrace();
        }

        form.setCreatedOn(AppUtility.getDate());
        form.setFirstName(firstName);
        form.setMiddleName(middleName);
        form.setLastName(lastName);
        form.setAccountName(accountName);
        form.setDateOfBirth(dateofbirth);
        form.setAddress1(addressLine1);
        form.setAddress2(addressLine2);
        form.setEmailAddress(emailAddress);
        form.setPhoneNumber(phoneNumber);
        form.setNextOfKin(nextOfKin);
        form.setBvn(bvn);
        form.setMotherMaidenName(maidenName);
        form.setSex(AppUtility.charInitials(form.getSex()));
        form.setRefId(refId);
        form.setRsmId(appSettings.getUser().getEmployeeId());
        form.setBranchNumber(appSettings.getUser().getBranchNumber());
        form.setAmlCustType(10);
        form.setAmlCustNatureBusiness(4);
        form.setAmlCustNature(14);
        form.setSync(Constant.COMPLETED_FORM);
        form.setStatus(Constant.PENDING_ACCOUNT);

        formViewModel.setForm(form);


        if (isNetworkAvailable(this)) {
            createAccount();
        } else {
            formViewModel.insertForm(form);
            Snackbar.make(floatingActionButton, "Account form has been saved offline, and will be synced when back online.", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navController.navigateUp();
                }
            }).show();
        }
        if (isLoggedIn()) {
            startZenormService();
        }

    }


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return (activeNetwork != null && activeNetwork.isConnectedOrConnecting());
        } else {
            return false;
        }
    }


    private void createAccount() {
        this.form = formViewModel.getForm().getValue();

        int marginSide = 0;
        int marginBottom = 550;


        snackbar = Snackbar.make(floatingActionButton, "Submitting form for approval...", Snackbar.LENGTH_INDEFINITE);

        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.amberWarning));

        View snackbarView = snackbar.getView();
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) snackbarView.getLayoutParams();


        params.setMargins(
                params.leftMargin + marginSide,
                params.topMargin,
                params.rightMargin + marginSide,
                params.bottomMargin + marginBottom
        );

        snackbarView.setLayoutParams(params);
        snackbar.show();


        if (networkInterface != null)
            NetworkClient.resetNetworkClient();
        networkInterface = NetworkClient.getNetworkInterface(ZENITH_BASE_URL);

//        final FormRepository repository = new FormRepository(getApplication());

//
//        List<Attachment> attachments = repository.getAllAttachments(form.getId());
//        List<Attachment> cleanAttachment = cleanAttachment(attachments);
//        form.setAttachments(cleanAttachment);

        SubmitForm compileForm = compileForm(form);
        appSettings = AppSettings.getInstance(getApplicationContext());

        if (appSettings != null)
            token = "Bearer " + appSettings.getUser().getToken();

        disposable.add(networkInterface.saveAccount(token, compileForm)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Response<LoginResponse>>() {
                    @Override
                    public void onSuccess(Response<LoginResponse> response) {

                        formViewModel.getForm().postValue(null);

                        if (snackbar != null && snackbar.isShown())
                            snackbar.dismiss();


                        if (response.code() == 400) {

                            String errorMessage = "";
                            assert response.body() != null;

                            try {
                                assert response.errorBody() != null;
                                JSONObject jObjError = new JSONObject(response.errorBody().string());
                                errorMessage = jObjError.getString("message");

                            } catch (Exception e) {
                                errorMessage = e.getLocalizedMessage();
                            }

                            Snackbar.make(floatingActionButton, errorMessage, Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    createAccount();
                                }
                            }).show();


                        } else if (response.code() == 200) {
                            assert response.body() != null;
                            if (response.body().getStatus() || response.body().getMessage().equals("Success")) {
                                if (response.body().getData().getBatchId() != null) {
                                    Snackbar.make(floatingActionButton, "Account form was sent successfully", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            navController.navigateUp();
                                        }
                                    }).show();
                                } else {

                                    Snackbar.make(floatingActionButton, "Account form was sent successfully, awaiting approval.", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            navController.navigateUp();
                                        }
                                    }).show();

                                }
                            } else {

                                // TODO: 09/05/2019 display options.
                            }
                        } else if (response.code() == 502) {

                            Snackbar.make(floatingActionButton, "We are having trouble connecting to the Zenith network.", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    createAccount();
                                }
                            }).show();
                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                        formViewModel.getForm().postValue(null);

                        if (snackbar != null && snackbar.isShown())
                            snackbar.dismiss();

                        String errorMessage;
                        if (e instanceof HttpException) {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            if (responseBody != null) {
                                errorMessage = responseBody.toString();
                            } else {
                                errorMessage = "An error occurred, . Kindly contact support.";
                            }

                        } else if (e instanceof SocketTimeoutException) {
                            errorMessage = "We are having trouble connecting to the Zenith network. Request Timeout. ";

                        } else if (e instanceof IOException) {
                            errorMessage = "You seem to have a bad network connection. Ensure you have an active connection.";

                        } else {
                            errorMessage = e.getLocalizedMessage();
                        }

                        Snackbar.make(floatingActionButton, errorMessage, Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                createAccount();
                            }
                        }).show();

                    }
                }));


    }


    @Override
    public void onSaveFormClicked(Form form) {
        AppSettings appSettings = AppSettings.getInstance(this);
        form.setCreatedOn(AppUtility.getDate());
        form.setRsmId(appSettings.getUser().getEmployeeId());
        form.setBranchNumber(appSettings.getUser().getBranchNumber());
        form.setSync(Constant.SAVED_FORM);
        form.setStatus(Constant.PENDING_ACCOUNT);
        formViewModel.setForm(form);
        formViewModel.insertForm(form);

//        finish();

    }


    @Override
    public void onDeleteFormClicked(Form form) {

        formViewModel.deleteForm(form);
    }

    @Override
    public void onCloseFormClicked() {

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

    private void formSentCompleted(FormRepository repository, Form form, String batchId) {
        form.setSync(Constant.SENT_FORM);
        form.setStatus(Constant.PENDING_ACCOUNT);
        form.setBatchId(batchId);
        repository.insertForm(form);

        Snackbar.make(floatingActionButton, "Account form was sent successfully", Snackbar.LENGTH_LONG).setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigateUp();
            }
        }).show();

//        setNotification("Zxplore", form.getAccountTypeName().toUpperCase() + " FORM WAS SENT SUCCESSFULLY");
//
//        addNotification(" Form Sent Successfully", "Form was sent and awaiting approval",
//                Const.SENT_FORM, form.getId());
    }

    private void askToSave(final Form form) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Save Form")
                .setMessage("Save Form before quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onSaveFormClicked(form);
                    }

                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    public void onCameraClicked(String type) {
        this.type = type;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA);
        } else {
            openCamera();
        }

    }

    @Override
    public void onGalleryClicked(String type) {
        this.type = type;
        Crop.pickImage(this);

    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case CAMERA:
                    openCamera();
                    break;
                case GALLERY:
                    openGallery();
                    break;
            }
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }


    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Crop.REQUEST_PICK:
                if (data != null)
                    beginCrop(data.getData());
                break;
            case Crop.REQUEST_CROP:
                if (resultCode == RESULT_OK) {
                    try {
                        Single.fromCallable(new Callable<Form>() {
                            @Override
                            public Form call() throws Exception {
                                Uri uri = Crop.getOutput(data);
                                InputStream inputStream = getContentResolver().openInputStream(uri);
                                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                                if (bitmap != null) {
                                    List<Attachment> attachments = form.getAttachments();
                                    if (attachments == null) attachments = new ArrayList<>();

                                    switch (type) {
                                        case TYPE:
                                            Attachment attachment = AppUtility.getAttachedImage(attachments, TYPE);
                                            String singlePath = AppUtility.saveImage(MainActivity.this, bitmap, type, form.getImageIdentifier());
                                            if (attachment != null) {
                                                attachment.setImage(singlePath);
                                                attachments.add(attachment);
                                            } else {
                                                attachments.add(new Attachment(singlePath, type));
                                            }
                                            form.setAttachments(attachments);
                                            break;
                                        default:
                                            String multiPath = AppUtility.saveImage(MainActivity.this, bitmap, type, 0);
                                            attachments.add(new Attachment(multiPath, type));
                                            form.setAttachments(attachments);
                                            break;
                                    }
                                }
                                return form;
                            }
                        }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new SingleObserver<Form>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onSuccess(Form form) {
                                        formViewModel.setForm(form);
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CAMERA:
                if (resultCode == RESULT_OK) {
                    beginCrop(data.getData());
                    Single.fromCallable(new Callable<Form>() {
                        @Override
                        public Form call() throws Exception {
                            final Bitmap bitmap = (Bitmap) data.getExtras().get("Data");
                            if (bitmap != null) {
                                List<Attachment> attachments = form.getAttachments();
                                if (attachments == null) attachments = new ArrayList<>();

                                switch (type) {
                                    case TYPE:
                                        Attachment attachment = AppUtility.getAttachedImage(attachments, TYPE);
                                        String singlePath = AppUtility.saveImage(MainActivity.this, bitmap, type, form.getImageIdentifier());
                                        if (attachment != null) {
                                            attachment.setImage(singlePath);
                                            attachments.add(attachment);
                                        } else {
                                            attachments.add(new Attachment(singlePath, type));
                                        }
                                        form.setAttachments(attachments);
                                        break;
                                    default:
                                        String multiPath = AppUtility.saveImage(MainActivity.this, bitmap, type, 0);
                                        attachments.add(new Attachment(multiPath, type));
                                        form.setAttachments(attachments);
                                        break;
                                }
                            }
                            return form;
                        }
                    }).subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new SingleObserver<Form>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onSuccess(Form form) {
                                    formViewModel.setForm(form);
                                }

                                @Override
                                public void onError(Throwable e) {

                                }
                            });
                }
                break;
            case GALLERY:
                if (resultCode == RESULT_OK) {
                    try {
                        Single.fromCallable(new Callable<Form>() {
                            @Override
                            public Form call() throws Exception {
                                Uri uri = data.getData();
                                InputStream inputStream = getContentResolver().openInputStream(uri);
                                final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                                if (bitmap != null) {
                                    List<Attachment> attachments = form.getAttachments();
                                    if (attachments == null) attachments = new ArrayList<>();

                                    switch (type) {
                                        case TYPE:
                                            Attachment attachment = AppUtility.getAttachedImage(attachments, TYPE);
                                            String singlePath = AppUtility.saveImage(MainActivity.this, bitmap, type, form.getImageIdentifier());
                                            if (attachment != null) {
                                                attachment.setImage(singlePath);
                                                attachments.add(attachment);
                                            } else {
                                                attachments.add(new Attachment(singlePath, type));
                                            }
                                            form.setAttachments(attachments);
                                            break;
                                        default:
                                            String multiPath = AppUtility.saveImage(MainActivity.this, bitmap, type, 0);
                                            attachments.add(new Attachment(multiPath, type));
                                            form.setAttachments(attachments);
                                            break;
                                    }
                                }
                                return form;
                            }
                        }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new SingleObserver<Form>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onSuccess(Form form) {
                                        formViewModel.setForm(form);
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private boolean isLoggedIn() {
        AppSettings appSettings = AppSettings.getInstance(getApplicationContext());
        if (appSettings.getLogin() != null)
            return true;
        else return false;
    }

    private void startZenormService() {
        Intent intent = new Intent(this, ZxploreService.class);
        startService(intent);
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();

//        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        if (count == 1) {
            askToSave(form);
//            if (editable){
//            } else {
//                finish();
//            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
        stopService(getIntent());
    }

    @Override
    public void onDateSelected(Form form) {

        this.form = formViewModel.getForm().getValue();

        this.form.setDateOfBirth(form.getDateOfBirth());
        this.form.setIdExpiryDate(form.getIdExpiryDate());
        this.form.setIdIssueDate(form.getIdIssueDate());

        formViewModel.setForm(form);

//        PersonalInformationFragment.dateOfBirth.setText(form.getDateOfBirth());

    }


    public interface onPersonalInformationReceived {

        void onPersonalInfoReceived(PersonalInformationModel model);
    }
}
