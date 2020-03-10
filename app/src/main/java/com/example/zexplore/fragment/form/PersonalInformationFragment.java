package com.example.zexplore.fragment.form;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.zexplore.R;
import com.example.zexplore.activity.MainActivity;
import com.example.zexplore.fragment.CreateAccountFragment;
import com.example.zexplore.listener.FragmentNavigationListener;
import com.example.zexplore.model.Attachment;
import com.example.zexplore.model.Bvn;
import com.example.zexplore.model.Form;
import com.example.zexplore.model.formModels.PersonalInformationModel;
import com.example.zexplore.util.AppSettings;
import com.example.zexplore.util.AppUtility;
import com.example.zexplore.util.Constant;
import com.example.zexplore.util.CryptLib;
import com.example.zexplore.viewmodel.BvnViewModel;
import com.example.zexplore.viewmodel.FormViewModel;
import com.example.zexplore.viewmodel.SpinnerViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static com.example.zexplore.fragment.CreateAccountFragment.IS_EDIT_MODE;

import static com.example.zexplore.fragment.form.ContactDetailsFragment.address1;
import static com.example.zexplore.fragment.form.ContactDetailsFragment.address2;
import static com.example.zexplore.fragment.form.ContactDetailsFragment.email;
import static com.example.zexplore.fragment.form.ContactDetailsFragment.maritalStatus;
import static com.example.zexplore.fragment.form.ContactDetailsFragment.mobile;
import static com.example.zexplore.fragment.form.ContactDetailsFragment.sex;
import static com.example.zexplore.fragment.form.ContactDetailsFragment.stateOfResident;
import static com.example.zexplore.util.Constant.SUCCESS_CODE;

/**
 * Created by Ehigiator David on 20/03/2019.
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */


/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalInformationFragment extends Fragment implements MainActivity.onPersonalInformationReceived, CreateAccountFragment.FragmentLifecycle {

    public  TextInputEditText title;
    public  TextInputEditText state;
    public  TextInputEditText country;
    public  TextInputEditText bvn;
    public  TextInputEditText surname;
    public  TextInputEditText firstName;
    public  TextInputEditText otherNames;
    public  TextInputEditText dateOfBirth;
    public  TextInputEditText maidenName;
    public  ImageButton verifyBvnButton;
    public static boolean completed;

    private BvnViewModel bvnViewModel;

    private String bvnValue, titleValue, countryValue,
            stateValue, surnameValue, firstNameValue,
            otherNamesValue, maidenNameValue,
            dobValue;
    private AppSettings appSettings;

    private String token = " ";
    private FormViewModel formViewModel;
    private SpinnerViewModel spinnerViewModel;
    private boolean editable;

    Form form;
    FragmentNavigationListener fragmentNavigationListener;


    public PersonalInformationFragment() {
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

        return inflater.inflate(R.layout.fragment_personal_information, container, false);
    }


    @Override
    public void onStop() {


        title.removeTextChangedListener(watcher);
        state.removeTextChangedListener(watcher);
        country.removeTextChangedListener(watcher);

        bvn.removeTextChangedListener(watcher);
        surname.removeTextChangedListener(watcher);
        firstName.removeTextChangedListener(watcher);
        otherNames.removeTextChangedListener(watcher);
        maidenName.removeTextChangedListener(watcher);
        dateOfBirth.removeTextChangedListener(watcher);

        super.onStop();

    }

    @Override
    public void onStart() {
        super.onStart();

        title.addTextChangedListener(watcher);
        state.addTextChangedListener(watcher);
        country.addTextChangedListener(watcher);

        bvn.addTextChangedListener(watcher);
        surname.addTextChangedListener(watcher);
        firstName.addTextChangedListener(watcher);
        otherNames.addTextChangedListener(watcher);
        maidenName.addTextChangedListener(watcher);
        dateOfBirth.addTextChangedListener(watcher);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        appSettings = AppSettings.getInstance(requireActivity());

        if (appSettings != null) {
            token = "Bearer " + appSettings.getUser().getToken();
        }
        spinnerViewModel = ViewModelProviders.of(requireActivity()).get(SpinnerViewModel.class);


        title = view.findViewById(R.id.titles);
        state = view.findViewById(R.id.state);
        country = view.findViewById(R.id.country_of_origin);

        bvn = view.findViewById(R.id.bvn);
        surname = view.findViewById(R.id.surname);
        firstName = view.findViewById(R.id.first_name);
        otherNames = view.findViewById(R.id.other_names);
        maidenName = view.findViewById(R.id.maiden_name);
        dateOfBirth = view.findViewById(R.id.date_of_birth);
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment newFragment = new DatePickerFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("Form", formViewModel.getForm().getValue());
                bundle.putString("Type", "DateOfBirth");
                newFragment.setArguments(bundle);
                newFragment.show(getFragmentManager(), "datePicker");

            }
        });
        verifyBvnButton = view.findViewById(R.id.verify_bvn);

        completed = false;


        verifyBvnButton = view.findViewById(R.id.verify_bvn);

        bvnValue = bvn.getText().toString().trim();

        verifyBvnButton.setOnClickListener(view1 -> {
            bvnValue = bvn.getText().toString().trim();
            if (bvnValue.length() < 10) {
                verifyBvnButton.setEnabled(false);
//                Toast.makeText(requireActivity(), "BVN is more than "+ bvn.getText().toString().length()+" Characters", Toast.LENGTH_SHORT ).show();
                Snackbar snackbar = Snackbar.make(requireActivity().findViewById(R.id.container), "BVN is more than " + bvn.getText().toString().length() + " Characters", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else {
                verifyBvnButton.setEnabled(true);
            }


            form.setBvn(bvnValue);
            Bvn bvnNew = new Bvn();
            String encrytedBVN = "";

            try {
                CryptLib _crypt = new CryptLib();
                String key = CryptLib.SHA256(Constant.ENCRYPT_KEY, 32); //32 bytes = 256 bit
                String iv = Constant.INNITIALISING_VECTOR_KEY; //16 bytes = 128 bit
                encrytedBVN = _crypt.encrypt(bvnValue, key, iv);

                bvnNew.setBvnNew(encrytedBVN);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(requireActivity(), "ENCRYPTION KEY IS INCORRECT", Toast.LENGTH_SHORT).show();
            }
            //fragmentNavigationListener.verifyBvn(bvnNew);
            verifyBvn(bvnNew);

        });

        title.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showTitlePopupMenu(view1, android.R.style.Widget_Material_PopupMenu, getContext());
            } else {
                showTitlePopupMenu(view1, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
            }
        });

        state.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showStatePopupMenu(view1, android.R.style.Widget_Material_PopupMenu, getContext());
            } else {
                showStatePopupMenu(view1, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
            }
        });

        country.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showCountryPopupMenu(view1, android.R.style.Widget_Material_PopupMenu, getContext());
            } else {
                showCountryPopupMenu(view1, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
            }
        });

            formViewModel.getForm().observe(getViewLifecycleOwner(), form1 -> {
                form = form1;

                if (form1 != null) {

                    if (TextUtils.isEmpty(bvn.getText()))
                        bvn.setText(form1.getBvn());

                    if (TextUtils.isEmpty(title.getText()))
                        title.setText(form1.getTitle());

                    if (TextUtils.isEmpty(surname.getText()))
                        surname.setText(form1.getLastName());

                    if (TextUtils.isEmpty(firstName.getText()))
                        firstName.setText(form1.getFirstName());

                    if (TextUtils.isEmpty(otherNames.getText()))
                        otherNames.setText(form1.getMiddleName());

                    if (TextUtils.isEmpty(maidenName.getText()))
                        maidenName.setText(form1.getMotherMaidenName());

                    if (TextUtils.isEmpty(dateOfBirth.getText()))
                        dateOfBirth.setText(form1.getDateOfBirth());

                    if (TextUtils.isEmpty(state.getText()))
                        state.setText(form1.getStateOfOrigin());

                    if (TextUtils.isEmpty(country.getText()))
                        country.setText(form1.getCountryOfOrigin());


                }

            });




    }

    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            if (charSequence.hashCode() == bvn.getText().hashCode()) {
                form.setBvn(charSequence.toString());
            } else if (charSequence.hashCode() == title.getText().hashCode()) {
                form.setTitle(charSequence.toString());
            } else if (charSequence.hashCode() == state.getText().hashCode()) {
                form.setStateOfOrigin(charSequence.toString());
            } else if (charSequence.hashCode() == surname.getText().hashCode()) {
                form.setLastName(charSequence.toString());
            } else if (charSequence.hashCode() == firstName.getText().hashCode()) {
                form.setFirstName(charSequence.toString());
            } else if (charSequence.hashCode() == otherNames.getText().hashCode()) {
                form.setMiddleName(charSequence.toString());
            } else if (charSequence.hashCode() == maidenName.getText().hashCode()) {
                form.setMotherMaidenName(charSequence.toString());
            } else if (charSequence.hashCode() == dateOfBirth.getText().hashCode()) {
                form.setDateOfBirth(charSequence.toString());
            } else if (charSequence.hashCode() == country.getText().hashCode()) {
                form.setCountryOfOrigin(charSequence.toString());
            }


            if (title.getText().toString().length() == 0 || state.getText().toString().length() == 0 ||
                    country.getText().toString().length() == 0 || bvn.getText().toString().length() == 0 ||
                    surname.toString().trim().length() == 0 || firstName.getText().toString().length() == 0 || maidenName.getText().toString().length() == 0 ||
                    dateOfBirth.getText().toString().length() == 0) {
                completed = false;
            } else {
                completed = true;
                countryValue = country.getText().toString();
                dobValue = dateOfBirth.getText().toString();
                maidenNameValue = maidenName.getText().toString();
                otherNamesValue = otherNames.getText().toString();
                firstNameValue = firstName.getText().toString();
                surnameValue = surname.getText().toString();
                stateValue = state.getText().toString();

            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void verifyBvn(Bvn bvn) {

        bvnViewModel = ViewModelProviders.of(this).get(BvnViewModel.class);
        AppUtility.hideKeyboard(requireActivity());

        final ProgressDialog progressDialog = new ProgressDialog(requireContext());
        progressDialog.setMessage("Verifying Bvn");
        progressDialog.setCancelable(true);
        progressDialog.show();

        bvnViewModel.verifyBvn(token, bvn)
                .observe(this, new Observer<Bvn>() {
                    private String dobValue;

                    @Override
                    public void onChanged(Bvn bvn) {
                        if (bvn != null) {
                            if (bvn.getResponseCode() != null && bvn.getResponseCode().equals(SUCCESS_CODE)) {
                                try {

                                    CryptLib _crypt = new CryptLib();
                                    String key = CryptLib.SHA256(Constant.ENCRYPT_KEY, 32); //32 bytes = 256 bit
                                    String iv = Constant.INNITIALISING_VECTOR_KEY; //16 bytes = 128 bit

                                    String MiddleName = _crypt.decrypt(bvn.getMiddleName(), key, iv);
                                    String FirstName = _crypt.decrypt(bvn.getFirstName(), key, iv);
                                    String LastName = _crypt.decrypt(bvn.getLastName(), key, iv);
                                    String Email = _crypt.decrypt(bvn.getEmail(), key, iv);
                                    String DateOfBirth = _crypt.decrypt(bvn.getDateOfBirth(), key, iv);
                                    String PhoneNumber = _crypt.decrypt(bvn.getPhoneNumber(), key, iv);
                                    char c = PhoneNumber.charAt(0);
                                    if (c == '0') {
                                        PhoneNumber = "234" + PhoneNumber.substring(1);
                                    }
                                    String ResidentialAddress = _crypt.decrypt(bvn.getResidentialAddress(), key, iv);

                                    form.setMiddleName(MiddleName.toUpperCase());
                                    form.setFirstName(FirstName.toUpperCase());
                                    form.setLastName(LastName.toUpperCase());
                                    form.setEmailAddress(Email);
                                    form.setAddress1(ResidentialAddress.toUpperCase());
                                    dobValue = DateOfBirth;
                                    String[] dob = dobValue.split("-");

                                    String day = dob[0];
                                    String month = dob[1];
                                    String years = dob[2];
                                    int count = years.length();
                                    if (count == 2) {
                                        if (Integer.valueOf(years) >= 0 && Integer.valueOf(years) <= 2) {
                                            years = "20" + years;
                                        } else
                                            years = "19" + years;
                                        dobValue = day + "-" + month + "-" + years;
                                    } else if (count == 4) {
                                        dobValue = DateOfBirth;
                                    }
                                    form.setPhoneNumber(PhoneNumber);
                                    form.setDateOfBirth(dobValue);
                                    form.setCountryOfOrigin(bvn.getNationality().toUpperCase());

                                    String gender = AppUtility.charInitials(bvn.getGender());
                                    form.setSex(gender);
                                    form.setMaritalStatus(bvn.getMaritalStatus().toUpperCase());
                                    form.setStateOfOrigin(bvn.getStateOfOrigin().toUpperCase());
                                    form.setStateOfResidence(bvn.getStateOfResidential().toUpperCase());
                                    formViewModel.setForm(form);

                                    //Fill the fields with the values from BVN
                                    populateSection();


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else if (bvn.getBvn() == null && bvn.getFirstName() == null) {
                                progressDialog.dismiss();
                                Snackbar snackbar = Snackbar.make(requireActivity().findViewById(R.id.container), "BVN cannot retrieve data at the moment", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            } else {
                                Toast.makeText(requireActivity(), "Failed to validate BVN", Toast.LENGTH_LONG).show();
                                AppUtility.okDialog(requireActivity(), "Failed", "Failed to validate bvn.");
                            }
                        }
                        progressDialog.dismiss();
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
                    state.setText(R.string.state_abia);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_adamawa:
                    state.setText(R.string.state_adamawa);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_akwa_ibom:
                    state.setText(R.string.state_akwa_ibom);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_anambra:
                    state.setText(R.string.state_anambra);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_bauchi:
                    state.setText(R.string.state_bauchi);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_benue:
                    state.setText(R.string.state_benue);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_borno:
                    state.setText(R.string.state_borno);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_bayelsa:
                    state.setText(R.string.state_bayelsa);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_cross_river:
                    state.setText(R.string.state_cross_river);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_delta:
                    state.setText(R.string.state_delta);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_ebonyi:
                    state.setText(R.string.state_ebonyi);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_edo:
                    state.setText(R.string.state_edo);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_ekiti:
                    state.setText(R.string.state_ekiti);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_enugu:
                    state.setText(R.string.state_enugu);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_fct:
                    state.setText(R.string.state_fct);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_gombe:
                    state.setText(R.string.state_gombe);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_imo:
                    state.setText(R.string.state_imo);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_jigawa:
                    state.setText(R.string.state_jigawa);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_kebbi:
                    state.setText(R.string.state_kebbi);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_kaduna:
                    state.setText(R.string.state_kaduna);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_kogi:
                    state.setText(R.string.state_kogi);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_kano:
                    state.setText(R.string.state_kano);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_katsina:
                    state.setText(R.string.state_katsina);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_kwara:
                    state.setText(R.string.state_kwara);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_lagos:
                    state.setText(R.string.state_lagos);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_niger:
                    state.setText(R.string.state_niger);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_nassarawa:
                    state.setText(R.string.state_nassarawa);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_ogun:
                    state.setText(R.string.state_ogun);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_ondo:
                    state.setText(R.string.state_ondo);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_osun:
                    state.setText(R.string.state_osun);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_oyo:
                    state.setText(R.string.state_oyo);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_plateau:
                    state.setText(R.string.state_plateau);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_rivers:
                    state.setText(R.string.state_rivers);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_sokoto:
                    state.setText(R.string.state_sokoto);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_taraba:
                    state.setText(R.string.state_taraba);
                    stateValue = state.getText().toString();
                    break;

                case R.id.action_state_yobe:
                    state.setText(R.string.state_yobe);
                    stateValue = state.getText().toString();
                    break;
                case R.id.action_state_zamfara:
                    state.setText(R.string.state_zamfara);
                    stateValue = state.getText().toString();
                    break;

            }
            return true;
        });

        popup.show();
    }


    private void showCountryPopupMenu(View anchor, int style, Context context) {
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(context, style);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, anchor);


        //inflate menu
        popup.getMenuInflater().inflate(R.menu.country_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_country:
                    country.setText(R.string.country_ng);
                    countryValue = country.getText().toString();
                    break;
            }
            return true;
        });

        popup.show();
    }


    private void showTitlePopupMenu(View anchor, int style, Context context) {
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(context, style);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, anchor);


        //inflate menu
        popup.getMenuInflater().inflate(R.menu.title_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_title_miss:
                    title.setText(R.string.title_miss);
                    titleValue = title.getText().toString();
                    break;
                case R.id.action_title_dr:
                    title.setText(R.string.title_dr);
                    titleValue = title.getText().toString();
                    break;

                case R.id.action_title_prof:
                    title.setText(R.string.title_prof);
                    titleValue = title.getText().toString();
                    break;
                case R.id.action_title_rev:
                    title.setText(R.string.title_rev);

                    titleValue = title.getText().toString();
                    break;

                case R.id.action_title_chief:
                    title.setText(R.string.title_chief);
                    titleValue = title.getText().toString();
                    break;
                case R.id.action_title_mr:
                    title.setText(R.string.title_mr);
                    titleValue = title.getText().toString();
                    break;
                case R.id.action_title_barrister:
                    title.setText(R.string.title_barrister);
                    titleValue = title.getText().toString();
                    break;
                case R.id.action_title_pastor:
                    title.setText(R.string.title_pastor);
                    titleValue = title.getText().toString();
                    break;

                case R.id.action_title_otunba:
                    title.setText(R.string.title_otunba);
                    titleValue = title.getText().toString();
                    break;
                case R.id.action_title_mrs:
                    title.setText(R.string.title_mrs);
                    titleValue = title.getText().toString();
                    break;

                case R.id.action_title_engr:
                    title.setText(R.string.title_engr);
                    titleValue = title.getText().toString();
                    break;

                case R.id.action_title_alhaji:
                    title.setText(R.string.title_alhaji);
                    titleValue = title.getText().toString();
                    break;

                case R.id.action_title_alhaja:
                    title.setText(R.string.title_alhaja);
                    titleValue = title.getText().toString();
                    break;

                case R.id.action_title_mr_mrs:
                    title.setText(R.string.title_mr_mrs);
                    titleValue = title.getText().toString();
                    break;

                case R.id.action_title_hon_just:
                    title.setText(R.string.title_hon_just);
                    titleValue = title.getText().toString();
                    break;

            }
            return true;
        });

        popup.show();
    }

    private void populateSection() {
        formViewModel.getForm().observe(requireActivity(), form1 -> {
            form = form1;
            if (form1 != null) {
                bvn.setText(form.getBvn());
                firstName.setText(form.getFirstName());
                surname.setText(form.getLastName());
                otherNames.setText(form.getMiddleName());
                address1.setText(form.getAddress1());
                email.setText(form.getEmailAddress());
                dateOfBirth.setText(form.getDateOfBirth());
                if (form1.getPhoneNumber() != null && form.getPhoneNumber().charAt(0) == '2') {
                    mobile.setText(form.getPhoneNumber().substring(3));
                } else {
                    mobile.setText(form.getPhoneNumber());
                }

                country.setText(form.getCountryOfOrigin());
                if (form1.getSex() != null && form.getSex().equals("M")) {
                    sex.setText("MALE");
                } else if (form1.getSex() != null && form.getSex().equals("F")) {
                    sex.setText("FEMALE");
                }

                state.setText(form.getStateOfOrigin());
                maritalStatus.setText(form.getMaritalStatus());
                stateOfResident.setText(form.getStateOfResidence());
            }
        });
    }

    @Override
    public void onPersonalInfoReceived(PersonalInformationModel model) {

    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }
}
