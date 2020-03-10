package com.example.zexplore.fragment.form;


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

import com.example.zexplore.R;
import com.example.zexplore.fragment.CreateAccountFragment;
import com.example.zexplore.listener.FragmentNavigationListener;
import com.example.zexplore.model.Form;
import com.example.zexplore.util.AppUtility;
import com.example.zexplore.viewmodel.FormViewModel;
import com.example.zexplore.viewmodel.SpinnerViewModel;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import static com.example.zexplore.fragment.CreateAccountFragment.IS_EDIT_MODE;

/**
 * Created by Ehigiator David on 20/03/2019.
 * Modified by Shaba Michael
 * Cyberspace Limited
 * davidehigiator@cyberspace.net.ng
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactDetailsFragment extends Fragment implements CreateAccountFragment.FragmentLifecycle{

    public static TextInputEditText country, stateOfResident, city, sex, occupation, maritalStatus;
    public static TextInputEditText address1, address2, email, mobile, nextOfKin, occupationText;
    public static boolean completed;

    private String countryValue, stateValue, cityValue, address1Value, address2Value, emailValue,
            mobileValue, sexValue, occupationValue, nextOfKinValue, maritalStatusValue;

    private FormViewModel formViewModel;
    private SpinnerViewModel spinnerViewModel;
    private boolean editable;

    FragmentNavigationListener fragmentNavigationListener;

    Form form;
    public ContactDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_details, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formViewModel = ViewModelProviders.of(requireActivity()).get(FormViewModel.class);
        form = formViewModel.getForm().getValue();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        address1 = view.findViewById(R.id.address1);
        address2 = view.findViewById(R.id.address2);
        email = view.findViewById(R.id.email);
        mobile = view.findViewById(R.id.mobile);
        nextOfKin = view.findViewById(R.id.next_of_kin);

        country = view.findViewById(R.id.country_of_residence);
        stateOfResident = view.findViewById(R.id.state_of_residence);
        city = view.findViewById(R.id.city_of_residence);
        sex = view.findViewById(R.id.sex);
        occupation = view.findViewById(R.id.occupation);
        maritalStatus = view.findViewById(R.id.marital_status);


        address1.addTextChangedListener(watcher);
        address2.addTextChangedListener(watcher);
        email.addTextChangedListener(watcher);
        mobile.addTextChangedListener(watcher);
        nextOfKin.addTextChangedListener(watcher);
        country.addTextChangedListener(watcher);
        stateOfResident.addTextChangedListener(watcher);
        city.addTextChangedListener(watcher);
        sex.addTextChangedListener(watcher);
        occupation.addTextChangedListener(watcher);
        maritalStatus.addTextChangedListener(watcher);

        //occupationLayout = view.findViewById(R.id.occupation_layout);

        country.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showCountryPopupMenu(view1, android.R.style.Widget_Material_PopupMenu, getContext());
            } else {
                showCountryPopupMenu(view1, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
            }
        });

        maritalStatus.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showMaritalStatusPopupMenu(view1, android.R.style.Widget_Material_PopupMenu, getContext());
            } else {
                showMaritalStatusPopupMenu(view1, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
            }
        });

        occupation.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showOccupationPopupMenu(view1, android.R.style.Widget_Material_PopupMenu, getContext());
            } else {
                showOccupationPopupMenu(view1, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
            }
        });

        sex.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showGenderPopupMenu(view1, android.R.style.Widget_Material_PopupMenu, getContext());
            } else {
                showGenderPopupMenu(view1, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
            }
        });


        city.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showCityPopupMenu(view1, android.R.style.Widget_Material_PopupMenu, getContext());
            } else {
                showCityPopupMenu(view1, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
            }
        });

        stateOfResident.setOnClickListener(view1 -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                showStatePopupMenu(view1, android.R.style.Widget_Material_PopupMenu, getContext());
            } else {
                showStatePopupMenu(view1, android.R.style.Widget_DeviceDefault_Light_PopupMenu, getContext());
            }
        });

        if (IS_EDIT_MODE) {

            formViewModel.getForm().observe(getViewLifecycleOwner(), new Observer<Form>() {
                @Override
                public void onChanged(Form form1) {
                    form = form1;

                    if (form1 != null) {

                        if (TextUtils.isEmpty(address1.getText())) {
                            address1.setText(form1.getAddress1());
                        }

                        if (TextUtils.isEmpty(address2.getText())) {
                            address2.setText(form1.getAddress2());
                        }


                        if (TextUtils.isEmpty(email.getText())) {
                            email.setText(form1.getEmailAddress());
                        }

                        if (TextUtils.isEmpty(mobile.getText())) {
                            mobile.setText(form1.getPhoneNumber());
                        }
                        if (TextUtils.isEmpty(nextOfKin.getText())) {
                            nextOfKin.setText(form1.getNextOfKin());
                        }
                        if (TextUtils.isEmpty(country.getText())) {
                            country.setText(form1.getCountryOfOrigin());
                        }
                        if (TextUtils.isEmpty(stateOfResident.getText())) {
                            stateOfResident.setText(form1.getStateOfResidence());
                        }
                        if (TextUtils.isEmpty(city.getText())) {
                            city.setText(form1.getCityOfResidence());
                        }
                        if (TextUtils.isEmpty(sex.getText())) {
                            sex.setText(form1.getSex());
                        }

                        if (TextUtils.isEmpty(occupation.getText())) {
                            occupation.setText(form1.getOccupation());
                        }


                        if (TextUtils.isEmpty(maritalStatus.getText())) {
                            maritalStatus.setText(form1.getMaritalStatus());
                        }


                    }
                }
            });


        }


    }

    private void showOccupationPopupMenu(View anchor, int style, Context context) {

        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(context, style);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, anchor);


        //inflate menu
        popup.getMenuInflater().inflate(R.menu.occupation_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_occupation_accountant:
                    occupation.setText(R.string.occupation_accountant);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_administrator:
                    occupation.setText(R.string.occupation_administrator);
                    occupationValue = occupation.getText().toString();
                    break;
                case R.id.action_occupation_advertiser:
                    occupation.setText(R.string.occupation_advertiser);
                    occupationValue = occupation.getText().toString();
                    break;
                case R.id.action_occupation_architect:
                    occupation.setText(R.string.occupation_architect);
                    occupationValue = occupation.getText().toString();
                    break;
                case R.id.action_occupation_banker:
                    occupation.setText(R.string.occupation_banker);
                    occupationValue = occupation.getText().toString();
                    break;
                case R.id.action_occupation_beautician:
                    occupation.setText(R.string.occupation_beautician);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_business_trader:
                    occupation.setText(R.string.occupation_business_trader);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_caterer:
                    occupation.setText(R.string.occupation_caterer);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_civil_servant:
                    occupation.setText(R.string.occupation_civil_servant);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_cleric:
                    occupation.setText(R.string.occupation_cleric);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_communication_technologist:
                    occupation.setText(R.string.occupation_communication_technologist);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_educationist:
                    occupation.setText(R.string.occupation_educationist);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_engineer:
                    occupation.setText(R.string.occupation_engineer);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_farmer:
                    occupation.setText(R.string.occupation_farmer);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_fashion_designer:
                    occupation.setText(R.string.occupation_fashion_designer);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_financial_service_consultant:
                    occupation.setText(R.string.occupation_financial_service_consultant);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_horologist:
                    occupation.setText(R.string.occupation_horologist);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_horticulturist:
                    occupation.setText(R.string.occupation_horticulturist);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_hotel:
                    occupation.setText(R.string.occupation_hotel);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_import:
                    occupation.setText(R.string.occupation_import);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_info_tech:
                    occupation.setText(R.string.occupation_info_tech);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_insurance:
                    occupation.setText(R.string.occupation_insurance);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_journalist:
                    occupation.setText(R.string.occupation_journalist);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_legal_practitioner:
                    occupation.setText(R.string.occupation_legal_practitioner);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_management_consultant:
                    occupation.setText(R.string.occupation_management_consultant);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_medical_practioner:
                    occupation.setText(R.string.occupation_medical_practioner);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_merchant:
                    occupation.setText(R.string.occupation_merchant);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_military:
                    occupation.setText(R.string.occupation_military);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_musician:
                    occupation.setText(R.string.occupation_musician);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_others:
                    occupation.setText(R.string.occupation_others);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_phamacist:
                    occupation.setText(R.string.occupation_phamacist);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_photographer:
                    occupation.setText(R.string.occupation_photographer);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_pilot:
                    occupation.setText(R.string.occupation_pilot);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_public_office:
                    occupation.setText(R.string.occupation_public_office);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_sailor:
                    occupation.setText(R.string.occupation_sailor);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_scientist:
                    occupation.setText(R.string.occupation_scientist);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_secretary:
                    occupation.setText(R.string.occupation_secretary);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_student:
                    occupation.setText(R.string.occupation_student);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_surveyor:
                    occupation.setText(R.string.occupation_surveyor);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_system_analyst:
                    occupation.setText(R.string.occupation_system_analyst);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_teacher_lecturer:
                    occupation.setText(R.string.occupation_teacher_lecturer);
                    occupationValue = occupation.getText().toString();
                    break;

                case R.id.action_occupation_transporter:
                    occupation.setText(R.string.occupation_transporter);
                    occupationValue = occupation.getText().toString();
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

            if(form == null)
                return;

            if (charSequence.hashCode() == email.getText().hashCode()) {
                form.setEmailAddress(charSequence.toString());
            } else if (charSequence.hashCode() == mobile.getText().hashCode()) {
                form.setPhoneNumber(charSequence.toString());
            } else if (charSequence.hashCode() == nextOfKin.getText().hashCode()) {
                form.setNextOfKin(charSequence.toString());
            } else if (charSequence.hashCode() == address1.getText().hashCode()) {
                form.setAddress1(charSequence.toString());
            } else if (charSequence.hashCode() == address2.getText().hashCode()) {
                form.setAddress2(charSequence.toString());
            } else if (charSequence.hashCode() == country.getText().hashCode()) {
                form.setCountryOfResidence(charSequence.toString());
            } else if (charSequence.hashCode() == stateOfResident.getText().hashCode()) {
                form.setStateOfResidence(charSequence.toString());
            } else if (charSequence.hashCode() == city.getText().hashCode()) {
                form.setCityOfResidence(charSequence.toString());
            } else if (charSequence.hashCode() == sex.getText().hashCode()) {
                if (!charSequence.toString().isEmpty())
                    form.setSex(AppUtility.charInitials(charSequence.toString()));
            } else if (charSequence.hashCode() == occupation.getText().hashCode()) {
                form.setOccupation(charSequence.toString());
            } else if (charSequence.hashCode() == maritalStatus.getText().hashCode()) {
                form.setMaritalStatus(charSequence.toString());
            }
        }

        @Override
        public void afterTextChanged(Editable s) {


            if (address1.getText().toString().length() == 0
                    || mobile.getText().toString().length() == 0
                    || nextOfKin.getText().toString().length() == 0
                    || country.getText().toString().length() == 0
                    || stateOfResident.getText().toString().length() == 0
                    || city.getText().toString().length() == 0
                    || sex.getText().toString().length() == 0
                    || occupation.getText().toString().length() == 0
                    || maritalStatus.getText().toString().length() == 0) {
                completed = false;
            } else {
                completed = true;
            }
        }
    };

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

    private void showGenderPopupMenu(View anchor, int style, Context context) {
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(context, style);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, anchor);


        //inflate menu
        popup.getMenuInflater().inflate(R.menu.gender_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_gender_male:
                    sex.setText(R.string.gender_male);
                    sex.setTag(R.string.gender_male);
                    sexValue = sex.getText().toString();
                    break;

                case R.id.action_gender_female:
                    sex.setText(R.string.gender_female);
                    sexValue = sex.getText().toString();
                    break;
            }
            return true;
        });

        popup.show();
    }

    private void showCityPopupMenu(View anchor, int style, Context context) {
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(context, style);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, anchor);


        //inflate menu
        popup.getMenuInflater().inflate(R.menu.city_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_city_aba:
                    city.setText(R.string.city_aba);
                    cityValue = city.getText().toString();
                    break;
                case R.id.action_city_abakaliki:
                    city.setText(R.string.city_abakaliki);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_abeokuta:
                    city.setText(R.string.city_abeokuta);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_abuja:
                    city.setText(R.string.city_abuja);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_ado_ekiti:
                    city.setText(R.string.city_ado_ekiti);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_agbor:
                    city.setText(R.string.city_agbor);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_akure:
                    city.setText(R.string.city_akure);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_alaba:
                    city.setText(R.string.city_alaba);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_anthony:
                    city.setText(R.string.city_anthony);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_asaba:
                    city.setText(R.string.city_asaba);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_auchi:
                    city.setText(R.string.city_auchi);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_awka:
                    city.setText(R.string.city_awka);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_bauchi:
                    city.setText(R.string.city_bauchi);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_benin:
                    city.setText(R.string.city_benin);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_benin_kebbi:
                    city.setText(R.string.city_benin_kebbi);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_calabar:
                    city.setText(R.string.city_calabar);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_coker:
                    city.setText(R.string.city_coker);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_damaturu:
                    city.setText(R.string.city_damaturu);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_dutse:
                    city.setText(R.string.city_dutse);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_enugu:
                    city.setText(R.string.city_enugu);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_festac:
                    city.setText(R.string.city_festac);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_gombe:
                    city.setText(R.string.city_gombe);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_gusau:
                    city.setText(R.string.city_gusau);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_ibadan:
                    city.setText(R.string.city_ibadan);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_ikeja:
                    city.setText(R.string.city_ikeja);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_ikoyi:
                    city.setText(R.string.city_ikoyi);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_ilorin:
                    city.setText(R.string.city_ilorin);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_ilupeju:
                    city.setText(R.string.city_ilupeju);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_isolo:
                    city.setText(R.string.city_isolo);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_jalingo:
                    city.setText(R.string.city_jalingo);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_jos:
                    city.setText(R.string.city_jos);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_kaduna:
                    city.setText(R.string.city_kaduna);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_kano:
                    city.setText(R.string.city_kano);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_katsina:
                    city.setText(R.string.city_katsina);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_lafia:
                    city.setText(R.string.city_lafia);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_lagos:
                    city.setText(R.string.city_lagos);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_lokoja:
                    city.setText(R.string.city_lokoja);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_maidugiri:
                    city.setText(R.string.city_maidugiri);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_mararaba:
                    city.setText(R.string.city_mararaba);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_markurdi:
                    city.setText(R.string.city_markurdi);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_minna:
                    city.setText(R.string.city_minna);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_nnewi:
                    city.setText(R.string.city_nnewi);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_nsukka:
                    city.setText(R.string.city_nsukka);
                    cityValue = city.getText().toString();
                    break;


                case R.id.action_city_ogba:
                    city.setText(R.string.city_ogba);
                    cityValue = city.getText().toString();
                    break;


                case R.id.action_city_ojo:
                    city.setText(R.string.city_ojo);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_onitsha:
                    city.setText(R.string.city_onitsha);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_oshodi:
                    city.setText(R.string.city_oshodi);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_oshogbo:
                    city.setText(R.string.city_oshogbo);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_owerri:
                    city.setText(R.string.city_owerri);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_oyingbo:
                    city.setText(R.string.city_oyingbo);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_p_h:
                    city.setText(R.string.city_p_h);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_sapele:
                    city.setText(R.string.city_sapele);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_sokoto:
                    city.setText(R.string.city_sokoto);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_surulere:
                    city.setText(R.string.city_surulere);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_ughelli:
                    city.setText(R.string.city_ughelli);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_umuahia:
                    city.setText(R.string.city_umuahia);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_uyo:
                    city.setText(R.string.city_uyo);
                    cityValue = city.getText().toString();
                    break;
                case R.id.action_city_vi:
                    city.setText(R.string.city_vi);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_warri:
                    city.setText(R.string.city_warri);
                    cityValue = city.getText().toString();
                    break;
                case R.id.action_city_yaba:
                    city.setText(R.string.city_yaba);
                    cityValue = city.getText().toString();
                    break;

                case R.id.action_city_yenagoa:
                    city.setText(R.string.city_yenagoa);
                    cityValue = city.getText().toString();
                    break;
                case R.id.action_city_yola:
                    city.setText(R.string.city_yola);
                    cityValue = city.getText().toString();
                    break;
            }
            return true;
        });

        popup.show();
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
                    stateOfResident.setText(R.string.state_abia);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_adamawa:
                    stateOfResident.setText(R.string.state_adamawa);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_akwa_ibom:
                    stateOfResident.setText(R.string.state_akwa_ibom);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_anambra:
                    stateOfResident.setText(R.string.state_anambra);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_bauchi:
                    stateOfResident.setText(R.string.state_bauchi);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_benue:
                    stateOfResident.setText(R.string.state_benue);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_borno:
                    stateOfResident.setText(R.string.state_borno);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_bayelsa:
                    stateOfResident.setText(R.string.state_bayelsa);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_cross_river:
                    stateOfResident.setText(R.string.state_cross_river);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_delta:
                    stateOfResident.setText(R.string.state_delta);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_ebonyi:
                    stateOfResident.setText(R.string.state_ebonyi);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_edo:
                    stateOfResident.setText(R.string.state_edo);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_ekiti:
                    stateOfResident.setText(R.string.state_ekiti);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_enugu:
                    stateOfResident.setText(R.string.state_enugu);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_fct:
                    stateOfResident.setText(R.string.state_fct);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_gombe:
                    stateOfResident.setText(R.string.state_gombe);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_imo:
                    stateOfResident.setText(R.string.state_imo);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_jigawa:
                    stateOfResident.setText(R.string.state_jigawa);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_kebbi:
                    stateOfResident.setText(R.string.state_kebbi);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_kaduna:
                    stateOfResident.setText(R.string.state_kaduna);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_kogi:
                    stateOfResident.setText(R.string.state_kogi);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_kano:
                    stateOfResident.setText(R.string.state_kano);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_katsina:
                    stateOfResident.setText(R.string.state_katsina);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_kwara:
                    stateOfResident.setText(R.string.state_kwara);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_lagos:
                    stateOfResident.setText(R.string.state_lagos);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_niger:
                    stateOfResident.setText(R.string.state_niger);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_nassarawa:
                    stateOfResident.setText(R.string.state_nassarawa);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_ogun:
                    stateOfResident.setText(R.string.state_ogun);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_ondo:
                    stateOfResident.setText(R.string.state_ondo);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_osun:
                    stateOfResident.setText(R.string.state_osun);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_oyo:
                    stateOfResident.setText(R.string.state_oyo);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_plateau:
                    stateOfResident.setText(R.string.state_plateau);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_rivers:
                    stateOfResident.setText(R.string.state_rivers);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_sokoto:
                    stateOfResident.setText(R.string.state_sokoto);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_taraba:
                    stateOfResident.setText(R.string.state_taraba);
                    stateValue = stateOfResident.getText().toString();
                    break;

                case R.id.action_state_yobe:
                    stateOfResident.setText(R.string.state_yobe);
                    stateValue = stateOfResident.getText().toString();
                    break;
                case R.id.action_state_zamfara:
                    stateOfResident.setText(R.string.state_zamfara);
                    stateValue = stateOfResident.getText().toString();
                    break;

            }
            return true;
        });

        popup.show();
    }

    private void showMaritalStatusPopupMenu(View anchor, int style, Context context) {
        //init the wrapper with style
        Context wrapper = new ContextThemeWrapper(context, style);

        //init the popup
        PopupMenu popup = new PopupMenu(wrapper, anchor);


        //inflate menu
        popup.getMenuInflater().inflate(R.menu.marital_status_menu, popup.getMenu());

        //implement click events
        popup.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_single:
                    maritalStatus.setText(R.string.status_single);
                    maritalStatusValue = maritalStatus.getText().toString();
                    break;

                case R.id.action_married:
                    maritalStatus.setText(R.string.status_married);
                    maritalStatusValue = maritalStatus.getText().toString();
                    break;

                case R.id.action_widow:
                    maritalStatus.setText(R.string.status_widow);
                    maritalStatusValue = maritalStatus.getText().toString();
                    break;

                case R.id.action_separated:
                    maritalStatus.setText(R.string.status_separated);
                    maritalStatusValue = maritalStatus.getText().toString();
                    break;
                case R.id.action_divorced:
                    maritalStatus.setText(R.string.status_divorced);
                    maritalStatusValue = maritalStatus.getText().toString();
                    break;


            }
            return true;
        });

        popup.show();
    }

    @Override
    public void onPauseFragment() {

    }

    @Override
    public void onResumeFragment() {

    }
}
