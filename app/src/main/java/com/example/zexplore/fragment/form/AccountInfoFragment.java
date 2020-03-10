package com.example.zexplore.fragment.form;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zexplore.R;
import com.example.zexplore.activity.MainActivity;
import com.example.zexplore.adapter.SpinnerLAdapter;
import com.example.zexplore.adapter.SpinnerZAdapter;
import com.example.zexplore.fragment.CreateAccountFragment;
import com.example.zexplore.listener.FragmentNavigationListener;
import com.example.zexplore.model.AccountClass;
import com.example.zexplore.model.AccountType;
import com.example.zexplore.model.Attachment;
import com.example.zexplore.model.BaseResponse;
import com.example.zexplore.model.Form;
import com.example.zexplore.model.ZenithBaseResponse;
import com.example.zexplore.util.AppSettings;
import com.example.zexplore.util.AppUtility;
import com.example.zexplore.util.Constant;
import com.example.zexplore.util.CryptLib;
import com.example.zexplore.viewmodel.AccountViewModel;
import com.example.zexplore.viewmodel.FormViewModel;
import com.example.zexplore.viewmodel.SpinnerViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;

import static com.example.zexplore.fragment.CreateAccountFragment.IS_EDIT_MODE;

/**
 * Created by Ehigiator David on 20/03/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountInfoFragment extends Fragment implements CreateAccountFragment.FragmentLifecycle {

    TextInputEditText accountTypeEditText, accountHolderTypeEditText, accountRiskEditText, accountCategoryEditText;

    private String accountTypeValue = " ", accountHolderTypeValue,
            riskRankValue, accountClassValue;
    private int accountClassCode;

    FragmentNavigationListener fragmentNavigationListener;

    private FormViewModel formViewModel;
    private SpinnerViewModel spinnerViewModel;

    private SpinnerLAdapter accountTypeAdapter;
    private SpinnerZAdapter accountClassAdapter;
    public  Form form;
    public static boolean completed;

    private boolean editable;

    private String TAG = AccountInfoFragment.class.getSimpleName();

    public AccountInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        formViewModel = ViewModelProviders.of(requireActivity()).get(FormViewModel.class);
        form =  formViewModel.getForm().getValue();
        return inflater.inflate(R.layout.fragment_account_info_framgent, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if(!IS_EDIT_MODE){
            form =  formViewModel.getForm().getValue();

            if(form != null){
                form.setSync(Constant.COMPLETED_FORM);
                form.setStatus(Constant.PENDING_ACCOUNT);
                form.setImageIdentifier(System.currentTimeMillis());
            }

        }

        if (form == null) {
            form = new Form();
            form.setSync(Constant.COMPLETED_FORM);
            form.setStatus(Constant.PENDING_ACCOUNT);
            form.setImageIdentifier(System.currentTimeMillis());

        } else {
            formViewModel.getAttachments(form.getId()).observe(this, new Observer<List<Attachment>>() {
                @Override
                public void onChanged(@Nullable List<Attachment> attachmentList) {
                    if (attachmentList != null && attachmentList.size() > 0) {
                        form.setAttachments(attachmentList);
                    }
                    formViewModel.setForm(form);
                }
            });
        }
        formViewModel.setForm(form);

        spinnerViewModel = ViewModelProviders.of(requireActivity()).get(SpinnerViewModel.class);

        accountCategoryEditText = view.findViewById(R.id.accountCategoryEditText);
        accountTypeEditText = view.findViewById(R.id.accountTypeEditText);
        accountHolderTypeEditText = view.findViewById(R.id.accountHolderTypeEditText);
        accountRiskEditText = view.findViewById(R.id.accountRiskEditText);

        accountCategoryEditText.addTextChangedListener(watcher);
        //accountCategoryEditText.setOnTouchListener(dropDownTouch);

        accountTypeEditText.addTextChangedListener(watcher);
        accountHolderTypeEditText.addTextChangedListener(watcher);
        accountRiskEditText.addTextChangedListener(watcher);


        accountTypeEditText.setOnClickListener(v -> {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showAccountTypePopupMenu(v, android.R.style.Widget_Material_PopupMenu, getContext());
            } else {
                showAccountTypePopupMenu(v, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
            }

        });

        accountHolderTypeEditText.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showAccountHolderPopupMenu(view1, android.R.style.Widget_Material_PopupMenu, getContext());
            } else {
                showAccountHolderPopupMenu(view1, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
            }
        });

        accountRiskEditText.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showAccountRiskPopupMenu(view1, android.R.style.Widget_Material_PopupMenu, getContext());
            } else {
                showAccountRiskPopupMenu(view1, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
            }
        });

        accountCategoryEditText.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showAccountClassPopupMenu(view1, android.R.style.Widget_Material_PopupMenu, getContext());
            } else {
                showAccountClassPopupMenu(view1, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
            }
        });


        if (IS_EDIT_MODE && isAdded()) {


            formViewModel.getForm().observe(getViewLifecycleOwner(), new Observer<Form>() {
                @Override
                public void onChanged(Form form1) {
                    form = form1;

                    if (form1 != null) {

                        if (TextUtils.isEmpty(accountTypeEditText.getText())) {

                            if (form1.getAccountType() != null && form1.getAccountType().equals("SA")) {
                                accountTypeEditText.setText(getString(R.string.savings));
                            } else if (form1.getAccountType() != null && form1.getAccountType().equals("CA")) {
                                accountTypeEditText.setText(getString(R.string.current));
                            }
                        }


                        if (TextUtils.isEmpty(accountHolderTypeEditText.getText()))
                            accountHolderTypeEditText.setText(form1.getAccountHolderType());

                        if (TextUtils.isEmpty(accountRiskEditText.getText()))
                            accountRiskEditText.setText(form1.getRiskRank());

                        if (TextUtils.isEmpty(accountCategoryEditText.getText()))
                            getAccountClassCode(form1.getClassCode());


                    }
                }
            });
        }
        else {
            accountTypeEditText.setText("");
            accountHolderTypeEditText.setText("");
            accountRiskEditText.setText("");
            accountCategoryEditText.setText("");

        }


    }


    public void getAccountClassCode(int code) {

        switch (code) {
            case 164:
                accountCategoryEditText.setText(getString(R.string.current_diaspora));
                break;
            case 156:
                accountCategoryEditText.setText(getString(R.string.current_gold));

                break;
            case 100:
                accountCategoryEditText.setText(getString(R.string.current_individual));

                break;
            case 157:
                accountCategoryEditText.setText(getString(R.string.current_platinum));

                break;
            case 217:
                accountCategoryEditText.setText(getString(R.string.current_salary));

                break;
            case 357:
                accountCategoryEditText.setText(getString(R.string.saving_aspire));

                break;
            case 163:
                accountCategoryEditText.setText(getString(R.string.saving_diaspora));

                break;
            case 389:
                accountCategoryEditText.setText(getString(R.string.saving_diaspora_aspire));

                break;
            case 344:
                accountCategoryEditText.setText(getString(R.string.saving_eazysave_classic));

                break;
            case 346:
                accountCategoryEditText.setText(getString(R.string.saving_eazysave_plus));

                break;
            case 345:
                accountCategoryEditText.setText(getString(R.string.saving_eazysave_premium));

                break;
            case 216:
                accountCategoryEditText.setText(getString(R.string.saving_salary));

                break;
            case 200:
                accountCategoryEditText.setText(getString(R.string.saving_acct));

                break;
            default:
                break;
        }

    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            if (charSequence.hashCode() == accountTypeEditText.getText().hashCode()) {
                if (charSequence.length() != 0) {
                    form.setAccountType(AppUtility.charInitials(charSequence.toString()));
                    form.setAccountTypeName(accountTypeEditText.getText().toString());
                }
            } else if (charSequence.hashCode() == accountRiskEditText.getText().hashCode()) {
                form.setRiskRank(charSequence.toString().toUpperCase());
            } else if (charSequence.hashCode() == accountHolderTypeEditText.getText().hashCode()) {
                form.setAccountHolderType(charSequence.toString().toUpperCase());
            } else if (charSequence.hashCode() == accountCategoryEditText.getText().hashCode()) {
                form.setAccountClass(charSequence.toString().toUpperCase());

                if (charSequence.toString().equals(getResources().getString(R.string.current_diaspora))) {
                    accountClassCode = 164;
                } else if (charSequence.toString().equals(getResources().getString(R.string.current_gold))) {
                    accountClassCode = 156;
                } else if (charSequence.toString().equals(getResources().getString(R.string.current_individual))) {
                    accountClassCode = 100;
                } else if (charSequence.toString().equals(getResources().getString(R.string.current_platinum))) {
                    accountClassCode = 157;
                } else if (charSequence.toString().equals(getResources().getString(R.string.current_salary))) {
                    accountClassCode = 217;
                } else if (charSequence.toString().equals(getResources().getString(R.string.saving_aspire))) {
                    accountClassCode = 357;
                } else if (charSequence.toString().equals(getResources().getString(R.string.saving_diaspora))) {
                    accountClassCode = 163;
                } else if (charSequence.toString().equals(getResources().getString(R.string.saving_diaspora_aspire))) {
                    accountClassCode = 389;
                } else if (charSequence.toString().equals(getResources().getString(R.string.saving_eazysave_classic))) {
                    accountClassCode = 344;
                } else if (charSequence.toString().equals(getResources().getString(R.string.saving_eazysave_plus))) {
                    accountClassCode = 346;
                } else if (charSequence.toString().equals(getResources().getString(R.string.saving_eazysave_premium))) {
                    accountClassCode = 345;
                } else if (charSequence.toString().equals(getResources().getString(R.string.saving_salary))) {
                    accountClassCode = 216;
                } else if (charSequence.toString().equals(getResources().getString(R.string.saving_acct))) {
                    accountClassCode = 200;
                }

                form.setClassCode(accountClassCode);

            }

            if (accountCategoryEditText.getText().toString().length() == 0 || accountTypeEditText.getText().toString().length() == 0 ||
                    accountHolderTypeEditText.getText().toString().length() == 0 || accountRiskEditText.getText().toString().length() == 0) {
                completed = false;
            } else {
                completed = true;
//                form.setAccountType(AppUtility.charInitials(accountTypeValue));
//                form.setAccountTypeName(accountTypeEditText.getText().toString());
//                form.setAccountHolderType(accountHolderTypeValue.toUpperCase());
//                form.setRiskRank(riskRankValue.toUpperCase());
//                form.setAccountClass(accountClassValue);
//                form.setClassCode(accountClassCode);

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (s == accountTypeEditText.getEditableText()) {
                if (s.length() != 0) {
                    form.setAccountType(AppUtility.charInitials(s.toString()));
                    form.setAccountTypeName(accountTypeEditText.getText().toString());
                }
            } else if (s == accountCategoryEditText.getEditableText()) {
                form.setAccountClass(s.toString());
                form.setClassCode(accountClassCode);

            } else if (s == accountRiskEditText.getEditableText()) {
                form.setRiskRank(s.toString());
            } else if (s == accountHolderTypeEditText.getEditableText()) {
                form.setAccountHolderType(s.toString());

            }
            formViewModel.setForm(form);

        }
    };

    private List<AccountClass> fliterAccountClass(String type, List<AccountClass> accountClasses) {
        List<AccountClass> temp = new ArrayList<>();
        if (accountClasses != null && accountClasses.size() > 0) {
            temp.clear();
            for (AccountClass accountClass : accountClasses) {
                if (accountClass.getClassType().trim().equals(type)) {
                    temp.add(accountClass);
                }
            }
        }
        return temp;
    }

//    private void populateAccountType(){
//        accountTypeAdapter = new SpinnerLAdapter(requireActivity());
//        spinnerAccountType.setAdapter(accountTypeAdapter);
//        spinnerViewModel.getAccountTypes().observe(this, new Observer<List<AccountType>>() {
//            @Override
//            public void onChanged(@Nullable List<AccountType> accountTypeList) {
//                List<BaseResponse> temp = new ArrayList<>();
//                if (accountTypeList != null && accountTypeList.size() > 0){
//                    temp.add(new AccountType(0, "SELECT ACCOUNT TYPE", ""));
//                    temp.addAll(accountTypeList);
//                    accountTypeAdapter.updateData(temp);
//                    populateSection();
//                } else {
//                    temp.add(new AccountType(0, "SELECT ACCOUNT TYPE", ""));
//                    accountTypeAdapter.updateData(temp);
//                }
////                disableControls();
//            }
//        });
//    }

    private void showAccountTypePopupMenu(View anchor, int style, Context context) {
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(context, style);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, anchor);


        //inflate menu
        popup.getMenuInflater().inflate(R.menu.account_type_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_savings:
                    accountTypeEditText.setText(R.string.savings);
                    accountTypeValue = accountTypeEditText.getText().toString();
                    break;
                case R.id.action_current:
                    accountTypeEditText.setText(R.string.current);
                    accountTypeValue = accountTypeEditText.getText().toString();
                    break;

            }
            return true;
        });

        popup.show();

    }

    private void showAccountHolderPopupMenu(View anchor, int style, Context context) {
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(context, style);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, anchor);


        //inflate menu
        popup.getMenuInflater().inflate(R.menu.account_holder_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_type:
                    accountHolderTypeEditText.setText(R.string.holder_type);
                    accountHolderTypeValue = accountHolderTypeEditText.getText().toString();
                    break;

            }
            return true;
        });

        popup.show();

    }

    private void showAccountRiskPopupMenu(View anchor, int style, Context context) {
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(context, style);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, anchor);


        //inflate menu
        popup.getMenuInflater().inflate(R.menu.risk_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_low_risk:
                    accountRiskEditText.setText(R.string.low_risk);
                    riskRankValue = accountRiskEditText.getText().toString();
                    break;
                case R.id.action_medium_risk:
                    accountRiskEditText.setText(R.string.medium_risk);
                    riskRankValue = accountRiskEditText.getText().toString();
                    break;
                case R.id.action_high_risk:
                    accountRiskEditText.setText(R.string.high_risk);
                    riskRankValue = accountRiskEditText.getText().toString();
                    break;

            }
            return true;
        });

        popup.show();

    }


    private void showAccountClassPopupMenu(View anchor, int style, Context context) {
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(context, style);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, anchor);


        //inflate menu
        if (accountTypeValue.equals(getString(R.string.current))) {
            popup.getMenuInflater().inflate(R.menu.current_account_category_menu, popup.getMenu());

            //implement click events
            popup.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.action_current_diaspora:
                        accountCategoryEditText.setText(R.string.current_diaspora);
                        accountClassValue = accountCategoryEditText.getText().toString();
                        accountClassCode = 164;
                        break;
                    case R.id.action_current_gold:
                        accountCategoryEditText.setText(R.string.current_gold);
                        accountClassValue = accountCategoryEditText.getText().toString();
                        accountClassCode = 156;
                        break;
                    case R.id.action_current_individual:
                        accountCategoryEditText.setText(R.string.current_individual);
                        accountClassValue = accountCategoryEditText.getText().toString();
                        accountClassCode = 100;
                        break;

                    case R.id.action_current_platinum:
                        accountCategoryEditText.setText(R.string.current_platinum);
                        accountClassValue = accountCategoryEditText.getText().toString();
                        accountClassCode = 157;
                        break;
                    case R.id.action_current_salary:
                        accountCategoryEditText.setText(R.string.current_salary);
                        accountClassValue = accountCategoryEditText.getText().toString();
                        accountClassCode = 217;
                        break;

                }
                return true;
            });
        } else {
            popup.getMenuInflater().inflate(R.menu.savings_account_category_menu, popup.getMenu());

            //implement click events
            popup.setOnMenuItemClickListener(menuItem -> {
                switch (menuItem.getItemId()) {
                    case R.id.action_saving_aspire:
                        accountCategoryEditText.setText(R.string.saving_aspire);
                        accountClassValue = accountCategoryEditText.getText().toString();
                        accountClassCode = 357;
                        break;
                    case R.id.action_saving_diaspora:
                        accountCategoryEditText.setText(R.string.saving_diaspora);
                        accountClassValue = accountCategoryEditText.getText().toString();
                        accountClassCode = 163;
                        break;
                    case R.id.action_saving_diaspora_aspire:
                        accountCategoryEditText.setText(R.string.saving_diaspora_aspire);
                        accountClassValue = accountCategoryEditText.getText().toString();
                        accountClassCode = 389;
                        break;

                    case R.id.action_saving_eazysave_classic:
                        accountCategoryEditText.setText(R.string.saving_eazysave_classic);
                        accountClassValue = accountCategoryEditText.getText().toString();
                        accountClassCode = 344;
                        break;
                    case R.id.action_saving_eazysave_plus:
                        accountCategoryEditText.setText(R.string.saving_eazysave_plus);
                        accountClassValue = accountCategoryEditText.getText().toString();
                        accountClassCode = 346;
                        break;

                    case R.id.action_saving_eazysave_premium:
                        accountCategoryEditText.setText(R.string.saving_eazysave_premium);
                        accountClassValue = accountCategoryEditText.getText().toString();
                        accountClassCode = 345;
                        break;
                    case R.id.action_saving_salary:
                        accountCategoryEditText.setText(R.string.saving_salary);
                        accountClassValue = accountCategoryEditText.getText().toString();
                        accountClassCode = 216;
                        break;

                    case R.id.action_saving_acct:
                        accountCategoryEditText.setText(R.string.saving_acct);
                        accountClassValue = accountCategoryEditText.getText().toString();
                        accountClassCode = 200;
                        break;

                }
                return true;
            });
        }

        popup.show();

    }

    private View.OnTouchListener dropDownTouch = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            if (view instanceof TextInputEditText) {
                switch (view.getId()) {
                    case R.id.accountCategoryEditText:
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            showAccountClassPopupMenu(view, android.R.style.Widget_Material_PopupMenu, getContext());
                        } else {
                            showAccountClassPopupMenu(view, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
                        }
                        break;

                }
            }

            return true;
        }
    };

    @Override
    public void onPauseFragment() {
        Log.i(TAG, "onPauseFragment()");
//        Toast.makeText(getActivity(), "onPauseFragment():" + TAG, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResumeFragment() {
        Log.i(TAG, "onResumeFragment()");
//        Toast.makeText(getActivity(), "onResumeFragment():" + TAG, Toast.LENGTH_SHORT).show();
    }

//    private void populateSection(){
//        formViewModel.getForm().observe(requireActivity(), new Observer<Form>() {
//            @Override
//            public void onChanged(@Nullable Form form) {
//                if (form != null){
//                    if (form.getAccountNumber() != null) {
////                        accountNumber.setVisibility(View.VISIBLE);
////                        accountNumber.setText(form.getAccountNumber());
//                    }
//
//                    if (form.getAccountName() != null) {
////                        accountName.setVisibility(View.VISIBLE);
//
//                        String accountNameString = "";
//                        try {
//                            CryptLib _crypt = new CryptLib();
//                            String key = CryptLib.SHA256(Constant.ENCRYPT_KEY, 32); //32 bytes = 256 bit
//                            String iv = Constant.INNITIALISING_VECTOR_KEY; //16 bytes = 128 bit
//                            accountNameString = _crypt.decrypt(form.getAccountName(), key, iv);
////                            accountName.setText(accountNameString);
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Toast.makeText(requireActivity(), "ENCRYPTION KEY IS INCORRECT", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    //if (form.getAccountClassPosition() != 0) spinnerAccountClass.setSelection(form.getAccountClassPosition());
//                    if (form.getAccountTypePosition() != 0) spinnerAccountType.setSelection(form.getAccountTypePosition());
//                    if (form.getAccountHolderPosition() != 0) accountHolderType.setSelection(form.getAccountHolderPosition());
//                    if (form.getRiskRankPosition() != 0) riskRank.setSelection(form.getRiskRankPosition());
//                }
//            }
//        });
//    }

}
