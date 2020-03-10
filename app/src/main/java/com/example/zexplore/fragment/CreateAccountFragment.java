package com.example.zexplore.fragment;


import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.example.zexplore.R;
import com.example.zexplore.fragment.form.AccountInfoFragment;
import com.example.zexplore.fragment.form.ContactDetailsFragment;
import com.example.zexplore.fragment.form.PersonalInformationFragment;
import com.example.zexplore.helper.Animation;
import com.example.zexplore.helper.CustomViewPager;
import com.example.zexplore.helper.NavigationIconClickListener;
import com.example.zexplore.helper.ViewPagerAdapter;
import com.example.zexplore.listener.ResponseListener;
import com.example.zexplore.model.AccountEdit;
import com.example.zexplore.model.Attachment;
import com.example.zexplore.model.Form;
import com.example.zexplore.model.LoginResponse;
import com.example.zexplore.model.SaveAccountResponse;
import com.example.zexplore.model.formresponse.Data;
import com.example.zexplore.repository.AccountRepository;
import com.example.zexplore.util.AppSettings;
import com.example.zexplore.util.AppUtility;
import com.example.zexplore.util.Constant;
import com.example.zexplore.viewmodel.AccountViewModel;
import com.example.zexplore.viewmodel.BvnViewModel;
import com.example.zexplore.viewmodel.FormViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.Objects;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;


/**
 * Created by Ehigiator David on 20/03/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */


/**
 * A simple {@link Fragment} subclass.
 */


public class CreateAccountFragment extends Fragment {

    CustomViewPager viewPager;
    private AccountViewModel accountViewModel;


    private MaterialButton accountInformationButton;
    private MaterialButton contactDetailsMenuButton;
    private MaterialButton identificationMenuButton;
    private MaterialButton idUploadMenuButton;
    private MaterialButton passportUploadMenuButton;
    private MaterialButton utilityUploadMenuButton;
    private MaterialButton signatoryMenuButton;
    private MaterialButton personalInformationMenuButton;

    private int currentPage;

    Toolbar toolbar;


    String referenceId;
    Snackbar snackbar;

    private FormViewModel formViewModel;

    ViewPagerAdapter pagerAdapter;

    public static Boolean IS_EDIT_MODE = false;

    Form form;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        formViewModel = ViewModelProviders.of(requireActivity()).get(FormViewModel.class);
        form = formViewModel.getForm().getValue();

    }

    public CreateAccountFragment() {
        // Required empty public constructor
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // TODO: 26/04/2019  get the view model.

        requireActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        // Set up the tool bar
        setUpToolbar(view);

        // Set cut corner background for API 23+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.findViewById(R.id.form_grid).setBackground(Objects.requireNonNull(getContext()).getDrawable(R.drawable.cut_background_shape));
        }

        viewPager = view.findViewById(R.id.view_pager);
        pagerAdapter = new ViewPagerAdapter(Objects.requireNonNull(getChildFragmentManager()));
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                FragmentLifecycle fragmentToShow = (FragmentLifecycle)pagerAdapter.getItem(position);
                fragmentToShow.onResumeFragment();

                FragmentLifecycle fragmentToHide = (FragmentLifecycle)pagerAdapter.getItem(position);
                fragmentToHide.onPauseFragment();


                currentPage = position;



                updatePageView();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        accountInformationButton = view.findViewById(R.id.accountInformationButton);
        personalInformationMenuButton = view.findViewById(R.id.personalInformationMenuButton);
        contactDetailsMenuButton = view.findViewById(R.id.contactDetailsMenuButton);
        identificationMenuButton = view.findViewById(R.id.identificationMenuButton);
        idUploadMenuButton = view.findViewById(R.id.idUploadMenuButton);
        passportUploadMenuButton = view.findViewById(R.id.passportUploadMenuButton);
        utilityUploadMenuButton = view.findViewById(R.id.utilityUploadMenuButton);
        signatoryMenuButton = view.findViewById(R.id.signatoryMenuButton);

        accountInformationButton.setOnClickListener(v -> viewPager.setCurrentItem(0, true));
        personalInformationMenuButton.setOnClickListener(v -> viewPager.setCurrentItem(1, true));
        contactDetailsMenuButton.setOnClickListener(v -> viewPager.setCurrentItem(2, true));
        identificationMenuButton.setOnClickListener(v -> viewPager.setCurrentItem(3, true));
        idUploadMenuButton.setOnClickListener(v -> viewPager.setCurrentItem(4, true));
        passportUploadMenuButton.setOnClickListener(v -> viewPager.setCurrentItem(5, true));
        utilityUploadMenuButton.setOnClickListener(v -> viewPager.setCurrentItem(6, true));
        signatoryMenuButton.setOnClickListener(v -> viewPager.setCurrentItem(7, true));


        if(!IS_EDIT_MODE){
            formViewModel.getForm().postValue(null);
        }
        else{
            if (getArguments() != null) {
                referenceId = getArguments().getString("referenceId", "0");
                getAccountByReferenceId(referenceId);
            }
        }





    }



    private void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof TextInputEditText) {
                ((TextInputEditText)view).setText("");
            }

            if(view instanceof ViewGroup && (((ViewGroup)view).getChildCount() > 0))
                clearForm((ViewGroup)view);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_create_account, container, false);

    }

    private void checkFormData(int pageNumber) {

        // TODO: 26/04/2019 get the viewmodel values, and perform the check
        // TODO: 26/04/2019 If the pageNumber is this, check certain fields
    }

    private void updatePageView() {
        switch (currentPage) {
            case 0:
                checkFormData(0);
                toolbar.setTitle("Step 1 of 8");
                viewPager.setPagingEnabled(true);

                break;
            case 1:
                viewPager.setPagingEnabled(true);

                toolbar.setTitle("Step 2 of 8");
                break;
            case 2:
                viewPager.setPagingEnabled(true);

                toolbar.setTitle("Step 3 of 8");
                break;
            case 3:
                viewPager.setPagingEnabled(true);

                toolbar.setTitle("Step 4 of 8");
                break;
            case 4:
                viewPager.setPagingEnabled(true);

                toolbar.setTitle("Step 5 of 8");
                break;
            case 5:
                viewPager.setPagingEnabled(true);

                toolbar.setTitle("Step 6 of 8");
                break;
            case 6:
                viewPager.setPagingEnabled(true);

                toolbar.setTitle("Step 7 of 8");
                break;
            case 7:
                viewPager.setPagingEnabled(false);
                toolbar.setTitle("Step 8 of 8");
                break;
            default:
                break;
        }

        // TODO: 20/03/2019 Check if form is completely filled before strike-through
        if (PersonalInformationFragment.completed) {
            Animation.animateStrikeThrough1(personalInformationMenuButton);
        } else {
            personalInformationMenuButton.setPaintFlags(personalInformationMenuButton.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if (AccountInfoFragment.completed) {
            Animation.animateStrikeThrough1(accountInformationButton);
        } else {
            personalInformationMenuButton.setPaintFlags(personalInformationMenuButton.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
        if (ContactDetailsFragment.completed) {
            Animation.animateStrikeThrough1(contactDetailsMenuButton);
        } else {
            personalInformationMenuButton.setPaintFlags(personalInformationMenuButton.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void setUpToolbar(View view) {
        toolbar = view.findViewById(R.id.app_bar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
        }

        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(
                getContext(),
                view.findViewById(R.id.form_grid),
                new AccelerateDecelerateInterpolator(),
                Objects.requireNonNull(getContext()).getResources().getDrawable(R.drawable.zexplore_branded_menu), // Menu open icon
                getContext().getResources().getDrawable(R.drawable.close_menu))); // Menu close icon
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.form_menu, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_form:
                saveForm();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveForm() {

        if(getContext() == null)
            return;

        AppSettings appSettings = AppSettings.getInstance(getContext());
        form.setCreatedOn(AppUtility.getDate());
        form.setRsmId(appSettings.getUser().getEmployeeId());
        form.setBranchNumber(appSettings.getUser().getBranchNumber());
        form.setSync(Constant.SAVED_FORM);
        form.setStatus(Constant.PENDING_ACCOUNT);
        formViewModel.setForm(form);
        formViewModel.insertForm(form);


        Snackbar.make(toolbar, "Account details have been saved offline, and can be completed before submission.", Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Navigation.findNavController(v).popBackStack();

            }
        }).show();

    }


    private void getAccountByReferenceId(String referenceId) {

        int marginSide = 0;
        int marginBottom = 550;


        snackbar = Snackbar.make(Objects.requireNonNull(getView()), "Loading....", Snackbar.LENGTH_INDEFINITE);
//        snackbar.setAction("Cancel", v -> snackbar.dismiss());
        // Changing message text color
        snackbar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.amberWarning));

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

        String token = "";
        AppSettings appSettings;
        appSettings = AppSettings.getInstance(requireContext());

        if (appSettings != null)
            token = "Bearer " + appSettings.getUser().getToken();


        AccountRepository.getInstance().getAccountsByRefId(token, referenceId, new ResponseListener<Data>() {
            @Override
            public void onSuccess(Data data) {


                formViewModel.getForm().observe(requireActivity(), form1 -> {

                    form = form1;

                    if (form1 != null) {
                        try {
                            form1.setBvn(data.getSignatoryDetails().get(0).getBvn());
//
//                            if(form.getFirstName() == null){
                            form1.setRsmId(data.getRSMId());
                            form1.setRefId(data.getRefId());

                            //account information
                            form1.setAccountType(data.getAccountType());
                            form1.setAccountHolderType(data.getAccountHolderType());
                            form1.setRiskRank(data.getRiskRank());
                            form1.setClassCode(Integer.parseInt(data.getClassCode()));

                            //personal information
                            form1.setBvn(data.getSignatoryDetails().get(0).getBvn());
                            form1.setTitle(data.getSignatoryDetails().get(0).getTitle());
                            form1.setLastName(data.getSignatoryDetails().get(0).getLastName());
                            form1.setFirstName(data.getSignatoryDetails().get(0).getFirstName());
                            form1.setMiddleName(data.getSignatoryDetails().get(0).getMiddleName());
                            form1.setMotherMaidenName(data.getSignatoryDetails().get(0).getMotherMaidenName());
                            form1.setDateOfBirth(data.getSignatoryDetails().get(0).getDateOfBirth());
                            form1.setStateOfOrigin(data.getSignatoryDetails().get(0).getStateOfOrigin());
                            form1.setCountryOfOrigin(data.getSignatoryDetails().get(0).getCountryOfOrigin());
                            form1.setAddress1(data.getSignatoryDetails().get(0).getAddressLine1());

                            //contact details
                            form1.setEmailAddress(data.getSignatoryDetails().get(0).getEmailAddress());
                            form1.setPhoneNumber(data.getSignatoryDetails().get(0).getPhoneNumber());
                            form1.setNextOfKin(data.getSignatoryDetails().get(0).getNextOfKin());
                            form1.setAddress1(data.getSignatoryDetails().get(0).getAddressLine1());
                            form1.setAddress2(data.getSignatoryDetails().get(0).getAddressLine2());
                            form1.setCountryOfResidence(data.getSignatoryDetails().get(0).getCountryOfOrigin());
                            form1.setStateOfResidence(data.getSignatoryDetails().get(0).getState());
                            form1.setCityOfResidence(data.getSignatoryDetails().get(0).getCity());
                            form1.setSex(data.getSignatoryDetails().get(0).getSex());
                            form1.setOccupation(data.getSignatoryDetails().get(0).getOccupation());
                            form1.setMaritalStatus(data.getSignatoryDetails().get(0).getMaritalStatus());

                            //means of identification
                            form1.setMeansOfId(data.getSignatoryDetails().get(0).getMeansOfId());
                            form1.setIdIssuer(data.getSignatoryDetails().get(0).getIdIssuer());
                            form1.setIdNumber(data.getSignatoryDetails().get(0).getIdNumber());
                            form1.setIdPlaceOfIssue(data.getSignatoryDetails().get(0).getIdPlaceOfIssue());
                            form1.setIdIssueDate(data.getSignatoryDetails().get(0).getIdIssueDate());
                            form1.setIdExpiryDate(data.getSignatoryDetails().get(0).getIdExpiryDate());
                            form1.setIbankRequest(data.getIbankRequest());
                            form1.setAlertZRequest(data.getAlertZRequest());
                            form1.setTokenRequest(data.getTokenRequest());

//                            }

                        } catch (IllegalBlockSizeException e) {
                            e.printStackTrace();
                        } catch (BadPaddingException e) {
                            e.printStackTrace();
                        } catch (InvalidAlgorithmParameterException e) {
                            e.printStackTrace();
                        } catch (InvalidKeyException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        if (snackbar != null && snackbar.isShown())
                            snackbar.dismiss();

                    }

                });


            }

            @Override
            public void onError(String message) {
                snackbar.dismiss();

            }
        });


    }

    @Deprecated
    private void getAccountByRefId(String refId) {

        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        String token = "";
        AppSettings appSettings;
        appSettings = AppSettings.getInstance(requireActivity());

        if (appSettings != null)
            token = "Bearer " + appSettings.getUser().getToken();

        accountViewModel.getAccountViewModel(token, refId).observe(requireActivity(), data ->
                {

                    snackbar.dismiss();
                    Gson gson = new Gson();

                    AccountEdit response1 = data.body().getData();
                    try {
                        response1 = gson.fromJson(data.body().getData().toString(), AccountEdit.class);
                        Log.d("Test", response1.toString());

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (response1 != null) {

                    }
                }
        );


    }


    public interface FragmentLifecycle {

        public void onPauseFragment();
        public void onResumeFragment();

    }

}
