package com.example.zexplore.fragment.form;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;


import com.example.zexplore.model.Form;
import com.example.zexplore.util.AppUtility;
import com.example.zexplore.viewmodel.FormViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import io.reactivex.annotations.Nullable;


public  class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private Form form;
    private String type;
    private DateSelectedListener dateSelectedListener;
    private String currentDate;
    private int claseCode;
    private FormViewModel formViewModel;


    public interface DateSelectedListener{
        void onDateSelected(Form form);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formViewModel = ViewModelProviders.of(requireActivity()).get(FormViewModel.class);
        form = formViewModel.getForm().getValue();
        Bundle bundle = getArguments();
        if (bundle != null) {
//            form = (Form)bundle.getSerializable("Form");
            type = bundle.getString("Type");
            claseCode = form.getClassCode();
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        currentDate = getCurrentDate(year, month, day);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(requireActivity(), this, year, month, day);
    }

    private String getCurrentDate(int year, int month, int day){
        int currentMonth = month + 1;
        return year + "-" + currentMonth + "-" + day;
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Do something with the date chosen by the user
        try {
            month = month + 1;
            String selectedDate = year + "-" + month + "-" + day;

//            Log.d("TAG_SELECTED_DATE", selectedDate);

            //SimpleDateFormat cdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat cdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            Date current = cdf.parse(currentDate);
            Date selected = cdf.parse(selectedDate);

            switch (type){
                case "IssueDate":
                    if (validatePastDate(selected,current)){
                        if (compareDate(form.getDateOfBirth(), selectedDate)){
                            form.setIdIssueDate(
                                    dateOfBirthFormat(day, month, year)
                            );
                            formViewModel.setForm(form);

                            dateSelectedListener.onDateSelected(form);
                        } else {
                            errorDialog("Issue Date must be after Date of Birth.");
                        }
                    } else {
                        errorDialog("Issue Date must be in the past.");
                    }
                    break;
                case "ExpiryDate":
//                    Log.d("TAG", year + "-" + month + "-" + day);
                    if (validateFutureDate(selected, current)){
                        form.setIdExpiryDate(
                                dateOfBirthFormat(day, month, year)
                        );
                        formViewModel.setForm(form);

                        dateSelectedListener.onDateSelected(form);
                    } else {
                        errorDialog("Expiry Date must be in the future.");
                    }
                    break;
                case "DateOfBirth":
                    if(claseCode==228 || claseCode==388){
                        if (validateZecaAge(day, month, year)) {
                            form.setDateOfBirth(
                                    dateOfBirthFormat(day, month, year)
                            );
                            formViewModel.setForm(form);

                            dateSelectedListener.onDateSelected(form);
                        } else {
                            errorDialog("Age range for Zeca is between 1 and 17 years.");
                        }

                    }else if (claseCode==357 || claseCode==389){

                        if(validateAspireAge(day, month, year)) {
                            form.setDateOfBirth(
                                    dateOfBirthFormat(day, month, year)
                            );
                            formViewModel.setForm(form);

                        }else{
                            errorDialog("Age range for Aspire is less than 13 years.");
                        }
                    }
                    else
                    if (validateAge(day, month, year)){
                        form.setDateOfBirth(
                                dateOfBirthFormat(day, month, year)
                        );
                        formViewModel.setForm(form);

                        dateSelectedListener.onDateSelected(form);
                    } else {
                        errorDialog("Minimum date of birth is 18 years.");
                    }
                    break;

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        currentDate = null;
    }

    private boolean compareDate(String dob, String selectedDate){
        boolean valid = false;
        if(dob != null){
            try{
                String newDob = convertDob(dob);
                SimpleDateFormat selectedFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                SimpleDateFormat dobFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());

                Date d1 = selectedFormat.parse(selectedDate);
                Date d2 = dobFormat.parse(newDob);
                if (d1.compareTo(d2) > 0){
                    valid = true;
                } else if (d1.compareTo(d2) < 0){
                    valid = false;
                } else if (d1.compareTo(d2) == 0){
                    valid = false;
                }
            } catch (ParseException pe){
                pe.printStackTrace();
            }
        } else {
            valid = false;
//            Log.d("TAG_DOB", "Kindly select dob");
        }
        return valid;
    }

    private boolean validateZecaAge(int day, int month, int year){
        int age = AppUtility.getAge(day, month - 1, year);
//        Log.d("TAG", age + "");
        if (age >= 5 && age< 17){
            return true;
        } else {
            return false;
        }
    }

    private boolean validateAspireAge(int day, int month, int year){
        int age = AppUtility.getAge(day, month - 1, year);
//        Log.d("TAG", age + "");
        if (age >= 13 ){
            return true;
        } else {
            return false;
        }
    }

    private boolean validateAge(int day, int month, int year){
        int age = AppUtility.getAge(day, month - 1, year);
//        Log.d("TAG", age + "");
        if (age >= 18){
            return true;
        } else {
            return false;
        }
    }
    private boolean validatePastDate(Date selected, Date current){
        if (selected.before(current)){
            return true;
        } else {
            return  false;
        }
    }

    private boolean validateFutureDate(Date selected, Date current){
        if (selected.after(current)){
            return true;
        } else {
            return false;
        }
    }

    private String dateOfBirthFormat(int day, int month, int year){
        return day + "-" + monthInitial(month - 1) + "-" + year;
    }

    private String meansOfIdFormat(int day, int month, int year){
        return year + "-" + month + "-" + day;
    }

    public static String monthInitial(int month){
        String[] monthNames = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
        return monthNames[month];
    }

    private int monthDigit(String month){
        switch (month){
            case "JAN":
                return 1;
            case "FEB":
                return 2;
            case "MAR":
                return 3;
            case "APR":
                return 4;
            case "MAY":
                return 5;
            case "JUN":
                return 6;
            case "JUL":
                return 7;
            case "AUG":
                return 8;
            case "SEP":
                return 9;
            case "OCT":
                return 10;
            case "NOV":
                return 11;
            case "DEC":
                return 12;
        }
        return 0;
    }

    private String convertDob(String dob){
        String extract = dob.substring(dob.indexOf("-") + 1, dob.lastIndexOf("-"));
        int month = monthDigit(extract);
        return dob.replace(extract, String.valueOf(month));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DateSelectedListener) {
            dateSelectedListener = (DateSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DateSelectedListener");
        }
    }

    private void errorDialog(String message){
        new AlertDialog.Builder(getActivity())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Oops")
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}