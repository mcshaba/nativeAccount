package com.example.zexplore.fragment.form;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.zexplore.R;
import com.example.zexplore.fragment.CreateAccountFragment;
import com.example.zexplore.listener.FragmentNavigationListener;
import com.example.zexplore.model.Form;
import com.example.zexplore.viewmodel.FormViewModel;
import com.example.zexplore.viewmodel.SpinnerViewModel;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static com.example.zexplore.fragment.CreateAccountFragment.IS_EDIT_MODE;

/**
 * Created by Ehigiator David on 20/03/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */


/**
 * A simple {@link Fragment} subclass.
 */
public class IdentificationFragment extends Fragment implements CreateAccountFragment.FragmentLifecycle {

    private TextInputEditText idIssueDate, idExpiryDate;
    private TextInputEditText meansOfId, idPlaceOfIssue;
    private TextInputEditText idNumber, idIssuer;

    private CheckBox emailStatement, smsAlert, requestToken, requestIbank;

    private String meansOfIdValue, emailStatementValue, smsAlertValue, requestMasterCardValue,
            requestVisaCardValue, requestVerveCardValue, requestTokenValue, requestIbankValue;

    private FormViewModel formViewModel;
    private SpinnerViewModel spinnerViewModel;
    private boolean editable;
    private boolean completed;

    FragmentNavigationListener fragmentNavigationListener;

    Form form;

    public IdentificationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        formViewModel = ViewModelProviders.of(requireActivity()).get(FormViewModel.class);
        form = formViewModel.getForm().getValue();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_identification, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        meansOfId.addTextChangedListener(watcher);
        idPlaceOfIssue.addTextChangedListener(watcher);
        idIssueDate.addTextChangedListener(watcher);
        idExpiryDate.addTextChangedListener(watcher);
        idNumber.addTextChangedListener(watcher);
        idIssuer.addTextChangedListener(watcher);

    }

    @Override
    public void onStop() {
        super.onStop();

        meansOfId.removeTextChangedListener(watcher);
        idPlaceOfIssue.removeTextChangedListener(watcher);
        idIssueDate.removeTextChangedListener(watcher);
        idExpiryDate.removeTextChangedListener(watcher);
        idNumber.removeTextChangedListener(watcher);
        idIssuer.removeTextChangedListener(watcher);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        spinnerViewModel = ViewModelProviders.of(this).get(SpinnerViewModel.class);

        meansOfId = view.findViewById(R.id.means_of_id);
        idPlaceOfIssue = view.findViewById(R.id.id_place_of_issue);
        idIssueDate = view.findViewById(R.id.id_issue_date);
        idExpiryDate = view.findViewById(R.id.id_expiry_date);
        idNumber = view.findViewById(R.id.id_number);
        idIssuer = view.findViewById(R.id.id_issuer);

        idIssueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateForm();
                DialogFragment fragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Form", form);
                bundle.putString("Type", "IssueDate");
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "datePicker");
            }
        });

        idExpiryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateForm();
                DialogFragment fragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Form", form);
                bundle.putString("Type", "ExpiryDate");
                fragment.setArguments(bundle);
                fragment.show(getFragmentManager(), "datePicker");
            }
        });

        emailStatement = view.findViewById(R.id.use_email_statement);
        smsAlert = view.findViewById(R.id.alert_request);
        requestToken = view.findViewById(R.id.request_token);
        requestIbank = view.findViewById(R.id.request_ibank);


        emailStatementValue = getCheckedValue(emailStatement.isChecked());
        smsAlertValue = getCheckedValue(smsAlert.isChecked());
        requestTokenValue = getCheckedValue(requestToken.isChecked());
        requestIbankValue = getCheckedValue(requestIbank.isChecked());

        requestMasterCardValue = "N";
        requestVisaCardValue = "N";
        requestVerveCardValue = "N";


        requestToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {
                    requestTokenValue = getCheckedValue(requestToken.isChecked());

                } else {
                    requestTokenValue = getCheckedValue(!requestToken.isChecked());
                }
            }
        });

        requestIbank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CompoundButton) view).isChecked()) {
                    requestIbankValue = getCheckedValue(requestIbank.isChecked());

                } else {
                    requestIbankValue = getCheckedValue(!requestIbank.isChecked());
                }
            }
        });

        idPlaceOfIssue.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showStatePopupMenu(view1, android.R.style.Widget_Material_PopupMenu, getContext());
            } else {
                showStatePopupMenu(view1, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
            }
        });

        meansOfId.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showIdentityPopupMenu(view1, android.R.style.Widget_Material_PopupMenu, getContext());
            } else {
                showIdentityPopupMenu(view1, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
            }
        });



            formViewModel.getForm().observe(getViewLifecycleOwner(), form1 -> {

                if (form1 != null) {

                    if (TextUtils.isEmpty(meansOfId.getText()))
                        meansOfId.setText(form1.getMeansOfId());

                    if (TextUtils.isEmpty(idPlaceOfIssue.getText()))
                        idPlaceOfIssue.setText(form1.getIdPlaceOfIssue());

                    if (TextUtils.isEmpty(idIssueDate.getText()))
                        idIssueDate.setText(form1.getIdIssueDate());

                    if (TextUtils.isEmpty(idExpiryDate.getText()))
                        idExpiryDate.setText(form1.getIdExpiryDate());

                    if (TextUtils.isEmpty(idNumber.getText()))
                        idNumber.setText(form1.getIdNumber());

                    if (TextUtils.isEmpty(idIssuer.getText())) {
                        idIssuer.setText(form1.getIdIssuer());

                        smsAlert.setChecked(form1.getAlertZRequest().equals("Y"));
                        requestToken.setChecked(form1.getTokenRequest().equals("Y"));
                        requestIbank.setChecked(form1.getIbankRequest().equals("Y"));
                    }


                }

            });



    }

    private void showStatePopupMenu(View anchor, int style, Context context) {
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(context, style);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, anchor);

        //inflate menu
        popup.getMenuInflater().inflate(R.menu.state_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_state_abia:
                    idPlaceOfIssue.setText(R.string.state_abia);
                    break;

                case R.id.action_state_adamawa:
                    idPlaceOfIssue.setText(R.string.state_adamawa);
                    break;

                case R.id.action_state_akwa_ibom:
                    idPlaceOfIssue.setText(R.string.state_akwa_ibom);
                    break;

                case R.id.action_state_anambra:
                    idPlaceOfIssue.setText(R.string.state_anambra);
                    break;
                case R.id.action_state_bauchi:
                    idPlaceOfIssue.setText(R.string.state_bauchi);
                    break;

                case R.id.action_state_benue:
                    idPlaceOfIssue.setText(R.string.state_benue);
                    break;
                case R.id.action_state_borno:
                    idPlaceOfIssue.setText(R.string.state_borno);
                    break;

                case R.id.action_state_bayelsa:
                    idPlaceOfIssue.setText(R.string.state_bayelsa);
                    break;
                case R.id.action_state_cross_river:
                    idPlaceOfIssue.setText(R.string.state_cross_river);
                    break;

                case R.id.action_state_delta:
                    idPlaceOfIssue.setText(R.string.state_delta);
                    break;
                case R.id.action_state_ebonyi:
                    idPlaceOfIssue.setText(R.string.state_ebonyi);
                    break;

                case R.id.action_state_edo:
                    idPlaceOfIssue.setText(R.string.state_edo);
                    break;
                case R.id.action_state_ekiti:
                    idPlaceOfIssue.setText(R.string.state_ekiti);
                    break;

                case R.id.action_state_enugu:
                    idPlaceOfIssue.setText(R.string.state_enugu);
                    break;
                case R.id.action_state_fct:
                    idPlaceOfIssue.setText(R.string.state_fct);
                    break;

                case R.id.action_state_gombe:
                    idPlaceOfIssue.setText(R.string.state_gombe);
                    break;
                case R.id.action_state_imo:
                    idPlaceOfIssue.setText(R.string.state_imo);
                    break;

                case R.id.action_state_jigawa:
                    idPlaceOfIssue.setText(R.string.state_jigawa);
                    break;
                case R.id.action_state_kebbi:
                    idPlaceOfIssue.setText(R.string.state_kebbi);
                    break;

                case R.id.action_state_kaduna:
                    idPlaceOfIssue.setText(R.string.state_kaduna);
                    break;
                case R.id.action_state_kogi:
                    idPlaceOfIssue.setText(R.string.state_kogi);
                    break;

                case R.id.action_state_kano:
                    idPlaceOfIssue.setText(R.string.state_kano);
                    break;
                case R.id.action_state_katsina:
                    idPlaceOfIssue.setText(R.string.state_katsina);
                    break;

                case R.id.action_state_kwara:
                    idPlaceOfIssue.setText(R.string.state_kwara);
                    break;
                case R.id.action_state_lagos:
                    idPlaceOfIssue.setText(R.string.state_lagos);
                    break;

                case R.id.action_state_niger:
                    idPlaceOfIssue.setText(R.string.state_niger);
                    break;
                case R.id.action_state_nassarawa:
                    idPlaceOfIssue.setText(R.string.state_nassarawa);
                    break;

                case R.id.action_state_ogun:
                    idPlaceOfIssue.setText(R.string.state_ogun);
                    break;
                case R.id.action_state_ondo:
                    idPlaceOfIssue.setText(R.string.state_ondo);
                    break;

                case R.id.action_state_osun:
                    idPlaceOfIssue.setText(R.string.state_osun);
                    break;
                case R.id.action_state_oyo:
                    idPlaceOfIssue.setText(R.string.state_oyo);
                    break;

                case R.id.action_state_plateau:
                    idPlaceOfIssue.setText(R.string.state_plateau);
                    break;
                case R.id.action_state_rivers:
                    idPlaceOfIssue.setText(R.string.state_rivers);
                    break;

                case R.id.action_state_sokoto:
                    idPlaceOfIssue.setText(R.string.state_sokoto);
                    break;
                case R.id.action_state_taraba:
                    idPlaceOfIssue.setText(R.string.state_taraba);
                    break;

                case R.id.action_state_yobe:
                    idPlaceOfIssue.setText(R.string.state_yobe);
                    break;
                case R.id.action_state_zamfara:
                    idPlaceOfIssue.setText(R.string.state_zamfara);
                    break;

            }
            return true;
        });

        popup.show();
    }


    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            if (charSequence.hashCode() == meansOfId.getText().hashCode()) {
                form.setMeansOfId(charSequence.toString());
            } else if (charSequence.hashCode() == idPlaceOfIssue.getText().hashCode()) {
                form.setIdPlaceOfIssue(charSequence.toString());
            } else if (charSequence.hashCode() == idIssueDate.getText().hashCode()) {
                form.setIdIssueDate(charSequence.toString());
            } else if (charSequence.hashCode() == idExpiryDate.getText().hashCode()) {
                form.setIdExpiryDate(charSequence.toString());
            } else if (charSequence.hashCode() == idNumber.getText().hashCode()) {
                form.setIdNumber(charSequence.toString());
            } else if (charSequence.hashCode() == idIssuer.getText().hashCode()) {
                form.setIdIssuer(charSequence.toString());
            }

            if (meansOfId.getText().toString().length() == 0 ||
                    idPlaceOfIssue.getText().toString().length() == 0 ||
                    idIssueDate.getText().toString().length() == 0 || idIssuer.getText().toString().length() == 0 ||
                    idExpiryDate.toString().trim().length() == 0 ||
                    idNumber.getText().toString().length() == 0) {
                completed = false;
            } else {
                completed = true;
                updateForm();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void showIdentityPopupMenu(View anchor, int style, Context context) {
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(context, style);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, anchor);


        //inflate menu
        popup.getMenuInflater().inflate(R.menu.identity_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_id_drivers:
                    meansOfId.setText(R.string.id_drivers);
                    meansOfIdValue = meansOfId.getText().toString();
                    break;

                case R.id.action_id_int_passport:
                    meansOfId.setText(R.string.id_int_passport);
                    meansOfIdValue = meansOfId.getText().toString();
                    break;

                case R.id.action_id_national:
                    meansOfId.setText(R.string.id_national);
                    meansOfIdValue = meansOfId.getText().toString();
                    break;

                case R.id.action_id_others:
                    meansOfId.setText(R.string.id_others);
                    meansOfIdValue = meansOfId.getText().toString();
                    break;
                case R.id.action_id_voters:
                    meansOfId.setText(R.string.id_voters);
                    meansOfIdValue = meansOfId.getText().toString();
                    break;

                case R.id.action_id_student:
                    meansOfId.setText(R.string.id_student);
                    meansOfIdValue = meansOfId.getText().toString();
                    break;
            }
            return true;
        });

        popup.show();
    }

    private String getCheckedValue(boolean checked) {
        return checked ? "Y" : "N";
    }

    private boolean setCheckedValue(String value) {
        if (value.equals("Y")) {
            return true;
        } else {
            return false;
        }
    }

    private void populateSection() {
        formViewModel.getForm().observe(getViewLifecycleOwner(), new Observer<Form>() {
            @Override
            public void onChanged(@Nullable Form form1) {

                form = form1;
                if (form1 != null) {
                    meansOfId.setSelection(form1.getMeansOfIdPosition());
                    idNumber.setText(form1.getIdNumber());
                    idIssuer.setText(form1.getIdIssuer());
                    idPlaceOfIssue.setSelection(form1.getIdPlaceOfIssuePosition());
                    idIssueDate.setText(form1.getIdIssueDate());
                    idExpiryDate.setText(form1.getIdExpiryDate());

                    if (form1.getUseEmailForStatement() != null) {
                        boolean value = setCheckedValue(form1.getUseEmailForStatement());
                        emailStatement.setChecked(value);
                    }


                    if (form1.getAlertZRequest() != null) {
                        boolean value = setCheckedValue(form1.getAlertZRequest());
                        smsAlert.setChecked(value);
                    }

                    if (form1.getTokenRequest() != null) {
                        boolean value = setCheckedValue(form1.getTokenRequest());
                        requestToken.setChecked(value);
                    }

                    if (form1.getIbankRequest() != null) {
                        boolean value = setCheckedValue(form.getIbankRequest());
                        requestIbank.setChecked(value);
                    }
                }
            }
        });
    }

    private void updateForm() {
        form.setMeansOfId(meansOfIdValue);
        form.setUseEmailForStatement(emailStatementValue);
        form.setAlertZRequest(smsAlertValue);
        form.setMasterCardRequest(requestMasterCardValue);
        form.setVisaCardRequest(requestVisaCardValue);
        form.setVerveCardRequest(requestVerveCardValue);
        form.setTokenRequest(requestTokenValue);
        form.setIbankRequest(requestIbankValue);

    }

    @Override
    public void onResume() {
        super.onResume();
//        populateSection();
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }
}
